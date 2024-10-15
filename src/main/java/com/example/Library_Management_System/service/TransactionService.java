package com.example.Library_Management_System.service;

import com.example.Library_Management_System.dto.InitiateTransactionRequest;
import com.example.Library_Management_System.model.*;
import com.example.Library_Management_System.repository.TransactionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    StudentService studentService;

    @Autowired
    AdminService adminService;

    @Autowired
    BookService bookService;

    @Autowired
    TransactionDao transactionDao;

    //These values are defined in application.properties to make it convenient to configure them. Change value at one place, will be
    //reflected everywhere else
    @Value("${student.allowed.duration}")
    Integer allowedDuration;

    @Value("${student.allowed.max-books}")
    Integer maxBooksAllowed;

    @Value("${student.allowed.fine-per-day}")
    Integer finePerDay;

    public String initiateTxn(InitiateTransactionRequest initiateTransactionRequest) throws Exception {
        return initiateTransactionRequest.getTransactionType() == TransactionType.ISSUE ?
                issueBook(initiateTransactionRequest) : returnBook(initiateTransactionRequest);
    }

    /**
     * Issue a book (Issuance) -> studentRollNumber, bookId, adminId
     * 1. Validate the request -> student, book and admin is valid or not
     * 2. Validate if book is available or not -> If book is already issued on someone's name
     * 3. Validate if the book can be issued to a person or not => Check whether issue limit is available or not in student account
     * 4. Entry in transaction table -> PENDING STATUS
     * 5. Assign book to a student -> Update student column in book table
     * 6. Update the status -> SUCCESS STATUS
     **/

    private String issueBook(InitiateTransactionRequest initiateTransactionRequest) throws Exception {
        //1. Validate the request

        Student student = validateStudent(initiateTransactionRequest.getStudentRollNumber());
        if(student == null) {
            throw new Exception("Invalid student roll number");
        }

        Admin admin = validateAdmin(initiateTransactionRequest.getAdminId());
        if(admin == null) {
            throw new Exception("Invalid admin");
        }

        Book book = validateBook(initiateTransactionRequest.getBookId());
        if(book == null) {
            throw new Exception("Invalid Book");
        }

        // 2. Check book availability

        if(book.getBook_student() != null) {
            throw new Exception("This book has already been issued to " + book.getBook_student().getName());
        }

        // 3. Check if book can be issued
        if(student.getBookList().size() >= maxBooksAllowed) {
            throw new Exception("Issue limit reached for this student");
        }

        Transaction transaction = null;
        try {
            // 4. Entry in transaction table

             transaction = Transaction.builder()
                    .transactionId(UUID.randomUUID().toString())
                    .student(student)
                    .book(book)
                    .admin(admin)
                    .transactionType(TransactionType.ISSUE)
                    .transactionStatus(TransactionStatus.PENDING)
                    .build();

            // 4. Make entry in the transaction table
            transactionDao.save(transaction);


            // 5. Assign book to student
            book.setBook_student(student);
            bookService.addBookOrUpdate(book);

            // 6. Update the transaction status
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);

        } catch (Exception e) {
            assert transaction != null;
            transaction.setTransactionStatus(TransactionStatus.FAILURE);

        } finally {
            assert transaction != null;
            transactionDao.save(transaction);
        }

        return transaction.getTransactionId();
    }

    /**
     * Return a book
     *
     * 1. Validate the request
     * 2. Get the corresponding issue transaction
     * 3. Entry in transaction table -> PENDING STATUS
     * 4. Due date of book, currentDate - issueDate > allowedDuration => fine calculation
     * 5. If there is fine => get the fine
     * 6. Deallocate the book from student's name
     * 7. Update transaction status -> SUCCESS
     */
    private String returnBook(InitiateTransactionRequest initiateTransactionRequest) throws Exception {
        //1. Validate the request

        Student student = validateStudent(initiateTransactionRequest.getStudentRollNumber());
        if(student == null) {
            throw new Exception("Invalid student roll number");
        }

        Admin admin = validateAdmin(initiateTransactionRequest.getAdminId());
        if(admin == null) {
            throw new Exception("Invalid admin");
        }

        Book book = validateBook(initiateTransactionRequest.getBookId());
        if(book == null) {
            throw new Exception("Invalid Book");
        }

        // 2. Get the corresponding issue transaction
        Transaction issuanceTransaction = transactionDao.findTransactionByStudentAndBookAndTransactionTypeOrderByIdDesc(student, book, TransactionType.ISSUE);

        Transaction transaction = null;

        try{
            Integer fine = calculateFine(issuanceTransaction.getCreatedOn());

            transaction = Transaction.builder()
                    .transactionId(UUID.randomUUID().toString())
                    .student(student)
                    .book(book)
                    .admin(admin)
                    .transactionType(TransactionType.RETURN)
                    .transactionStatus(TransactionStatus.PENDING)
                    .build();

            // 4. Make entry in the transaction table
            transactionDao.save(transaction);


            // 5. Assign book to student
            book.setBook_student(null);
            bookService.addBookOrUpdate(book);

            // 6. Update the transaction status
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        } catch (Exception e) {
            assert transaction != null;
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
        } finally {
            assert transaction != null;
            transactionDao.save(transaction);
        }

        return transaction.getTransactionId();

    }

    private Integer calculateFine(Date issuanceDateTime) {
        long issuanceTimeInMillis = issuanceDateTime.getTime();
        long currentTime = System.currentTimeMillis();

        long diff = currentTime - issuanceTimeInMillis;

        long daysPassed = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        if(daysPassed > allowedDuration) {
            return (int) ((daysPassed - allowedDuration)*finePerDay);
        }
        return 0;
    }

    private Student validateStudent(String studentRollNumber) {
        try {
            return studentService.searchStudent("rollNumber", studentRollNumber).getFirst();
        } catch (Exception e) {
            return null;
        }
    }

    private Admin validateAdmin(Integer adminId) {
        try {
            return adminService.findAdmin(adminId);
        } catch (Exception e) {
            return null;
        }
    }

    private Book validateBook(Integer bookId) {
        try {
            return bookService.searchBook("id", String.valueOf(bookId)).getFirst();
        } catch (Exception e) {
            return null;
        }
    }
}
