package com.example.Library_Management_System.service;

import com.example.Library_Management_System.dto.InitiateTransactionRequest;
import com.example.Library_Management_System.model.*;
import com.example.Library_Management_System.repository.TransactionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
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



    private String issueBook(InitiateTransactionRequest initiateTransactionRequest) throws Exception {

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

        if(book.getBook_student() != null) {
            throw new Exception("This book has already been issued to " + book.getBook_student().getName());
        }

        if(student.getBookList().size() >= maxBooksAllowed) {
            throw new Exception("Issue limit reached for this student");
        }

        Transaction transaction = null;
        try {

             transaction = Transaction.builder()
                    .transactionId(UUID.randomUUID().toString())
                    .student(student)
                    .book(book)
                    .admin(admin)
                    .transactionType(TransactionType.ISSUE)
                    .transactionStatus(TransactionStatus.PENDING)
                    .build();

            transactionDao.save(transaction);


            book.setBook_student(student);
            bookService.addBookOrUpdate(book);

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


    private String returnBook(InitiateTransactionRequest initiateTransactionRequest) throws Exception {

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


            transactionDao.save(transaction);


            book.setBook_student(null);
            bookService.addBookOrUpdate(book);

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

    public List<Transaction> searchTransaction(String searchKey, String searchValue) throws Exception {
        if(searchKey.equals("TransactionType")) {
            if(searchValue.equals("ISSUE")) {
                return transactionDao.findByTransactionType(TransactionType.ISSUE);
            }

            if(searchValue.equals("RETURN")) {
                return transactionDao.findByTransactionType(TransactionType.RETURN);
            }
        }

        if(searchKey.equals("TransactionStatus")) {
            switch (searchValue) {
                case "SUCCESS" -> {
                    return transactionDao.findByTransactionStatus(TransactionStatus.SUCCESS);
                }
                case "FAILURE" -> {
                    return transactionDao.findByTransactionStatus(TransactionStatus.FAILURE);
                }
                case "PENDING" -> {
                    return transactionDao.findByTransactionStatus(TransactionStatus.PENDING);
                }
            }

        }

        throw new Exception("Invalid Search Key: " + searchKey);
    }
}
