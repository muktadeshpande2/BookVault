package com.example.Library_Management_System.service;

import com.example.Library_Management_System.dto.InitiateTransactionRequest;
import com.example.Library_Management_System.model.*;
import com.example.Library_Management_System.repository.TransactionDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = "spring.config.location=classpath:application.properties")

public class TransactionServiceTest {

    @Mock
    private TransactionDao mockTransactionDao;

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private StudentService studentService;

    @Mock
    private AdminService adminService;

    @Mock
    private BookService bookService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        transactionService.maxBooksAllowed = 2;
        transactionService.allowedDuration = 15;
    }

    @Test
    public void testInitiateTxn_InvalidStudentRollNumber() throws Exception {
        InitiateTransactionRequest request = new InitiateTransactionRequest();
        request.setStudentRollNumber("Roll_Number");
        request.setTransactionType(TransactionType.valueOf("ISSUE"));

        when(studentService.searchStudent("rollNumber", "Roll_Number")).thenThrow(new Exception("Invalid Search Key: rollNumber"));

        Exception exception = assertThrows(Exception.class, ()->{
            transactionService.initiateTxn(request);
        });

        assertEquals(exception.getMessage(), "Invalid student roll number");
    }

    @Test
    public void testInitiateTxn_InvalidAdmin() throws Exception {
        InitiateTransactionRequest request = new InitiateTransactionRequest();
        request.setStudentRollNumber("18UCS070");
        request.setAdminId(100);
        request.setTransactionType(TransactionType.valueOf("ISSUE"));

        when(studentService.searchStudent("rollNumber", "18UCS070")).thenReturn(List.of(new Student()));
        when(adminService.findAdmin(100)).thenThrow(new RuntimeException());

        Exception exception = assertThrows(Exception.class, ()->{
            transactionService.initiateTxn(request);
        });

        assertEquals(exception.getMessage(), "Invalid admin");
    }

    @Test
    public void testInitiateTxn_InvalidBook() throws Exception {
        InitiateTransactionRequest request = new InitiateTransactionRequest();
        request.setStudentRollNumber("18UCS070");
        request.setAdminId(100);
        request.setBookId(20000);
        request.setTransactionType(TransactionType.valueOf("ISSUE"));

        when(studentService.searchStudent("rollNumber", "18UCS070")).thenReturn(List.of(new Student()));
        when(adminService.findAdmin(100)).thenReturn(new Admin());
        when(bookService.searchBook("id", "20000")).thenThrow(new Exception());

        Exception exception = assertThrows(Exception.class, ()->{
            transactionService.initiateTxn(request);
        });

        assertEquals(exception.getMessage(), "Invalid Book");
    }


    @Test
    public void testInitiateTxn_BookAlreadyIssued() throws Exception {
        InitiateTransactionRequest request = new InitiateTransactionRequest();
        request.setStudentRollNumber("18UCS070");
        request.setAdminId(100);
        request.setBookId(2);
        request.setTransactionType(TransactionType.valueOf("ISSUE"));

        Student student = Student.builder()
                .id(1)
                .name("William H")
                .build();


        Book issuedBook = Book.builder()
                .id(2)
                .bookName("Harry Potter")
                .book_student(student)
                .build();

        when(studentService.searchStudent("rollNumber", "18UCS070")).thenReturn(List.of(new Student()));
        when(adminService.findAdmin(100)).thenReturn(new Admin());
        when(bookService.searchBook("id", "2")).thenReturn(List.of(issuedBook));

        Exception exception = assertThrows(Exception.class, ()->{
            transactionService.initiateTxn(request);
        });

        assertEquals(exception.getMessage(), "This book has already been issued to " + issuedBook.getBook_student().getName());
    }


    @Test
    public void testInitiateTxn_IssueLimitReached() throws Exception {
        InitiateTransactionRequest request = new InitiateTransactionRequest();
        request.setStudentRollNumber("18UCS070");
        request.setAdminId(100);
        request.setBookId(1);
        request.setTransactionType(TransactionType.valueOf("ISSUE"));

        Student student = Student.builder()
                .id(1)
                .rollNumber("18UCS070")
                .name("William H")
                .build();

        Book book1 = Book.builder()
                .id(1)
                .bookName("Harry Potter")
                .book_student(null)
                .build();

        Book book2 = Book.builder()
                .id(2)
                .bookName("Wuthering Heights")
                .book_student(null)
                .build();

        List<Book> bookList = List.of(book1, book2);

        student.setBookList(bookList);

        when(studentService.searchStudent("rollNumber", "18UCS070")).thenReturn(List.of(student));
        when(adminService.findAdmin(100)).thenReturn(new Admin());
        when(bookService.searchBook("id", "1")).thenReturn(List.of(book1));

        Exception exception = assertThrows(Exception.class, ()->{
            transactionService.initiateTxn(request);
        });

        assertEquals(exception.getMessage(), "Issue limit reached for this student");
    }


    @Test
    public void testInitiateTxn_ValidationSuccessful() throws Exception {
        InitiateTransactionRequest request = new InitiateTransactionRequest();
        request.setStudentRollNumber("18UCS070");
        request.setAdminId(100);
        request.setBookId(1);
        request.setTransactionType(TransactionType.valueOf("ISSUE"));

        Student student = Student.builder()
                .id(1)
                .rollNumber("18UCS070")
                .name("William H")
                .build();

        Book book1 = Book.builder()
                .id(1)
                .bookName("Harry Potter")
                .book_student(null)
                .build();


        List<Book> bookList = List.of(book1);

        student.setBookList(bookList);

        when(studentService.searchStudent("rollNumber", "18UCS070")).thenReturn(List.of(student));
        when(adminService.findAdmin(100)).thenReturn(new Admin());
        when(bookService.searchBook("id", "1")).thenReturn(List.of(book1));

        transactionService.initiateTxn(request);

        verify(mockTransactionDao, atLeastOnce()).save(any(Transaction.class));
    }


    @Test
    public void testInitiateTxn_InvalidStudentRollNumberReturnBook() throws Exception {
        InitiateTransactionRequest request = new InitiateTransactionRequest();
        request.setStudentRollNumber("Roll_Number");
        request.setTransactionType(TransactionType.valueOf("RETURN"));

        when(studentService.searchStudent("rollNumber", "Roll_Number")).thenThrow(new Exception("Invalid Search Key: rollNumber"));

        Exception exception = assertThrows(Exception.class, ()->{
            transactionService.initiateTxn(request);
        });

        assertEquals(exception.getMessage(), "Invalid student roll number");
    }

    @Test
    public void testInitiateTxn_InvalidAdminReturnBook() throws Exception {
        InitiateTransactionRequest request = new InitiateTransactionRequest();
        request.setStudentRollNumber("18UCS070");
        request.setAdminId(100);
        request.setTransactionType(TransactionType.valueOf("RETURN"));

        when(studentService.searchStudent("rollNumber", "18UCS070")).thenReturn(List.of(new Student()));
        when(adminService.findAdmin(100)).thenThrow(new RuntimeException());

        Exception exception = assertThrows(Exception.class, ()->{
            transactionService.initiateTxn(request);
        });

        assertEquals(exception.getMessage(), "Invalid admin");
    }

    @Test
    public void testInitiateTxn_InvalidBookReturnBook() throws Exception {
        InitiateTransactionRequest request = new InitiateTransactionRequest();
        request.setStudentRollNumber("18UCS070");
        request.setAdminId(100);
        request.setBookId(20000);
        request.setTransactionType(TransactionType.valueOf("RETURN"));

        when(studentService.searchStudent("rollNumber", "18UCS070")).thenReturn(List.of(new Student()));
        when(adminService.findAdmin(100)).thenReturn(new Admin());
        when(bookService.searchBook("id", "20000")).thenThrow(new Exception());

        Exception exception = assertThrows(Exception.class, ()->{
            transactionService.initiateTxn(request);
        });

        assertEquals(exception.getMessage(), "Invalid Book");
    }


    @Test
    public void testInitiateTxn_ValidationSuccessfulReturnTxn() throws Exception {
        InitiateTransactionRequest request = new InitiateTransactionRequest();
        request.setStudentRollNumber("18UCS070");
        request.setAdminId(100);
        request.setBookId(1);
        request.setTransactionType(TransactionType.valueOf("RETURN"));

        Student student = Student.builder()
                .id(1)
                .rollNumber("18UCS070")
                .name("William H")
                .build();

        Book book1 = Book.builder()
                .id(1)
                .bookName("Harry Potter")
                .book_student(null)
                .build();


        Transaction issuanceTransaction = Transaction.builder()
                .createdOn(new Date(2014, Calendar.APRIL, 5))
                .build();

        List<Book> bookList = List.of(book1);

        student.setBookList(bookList);

        when(studentService.searchStudent("rollNumber", "18UCS070")).thenReturn(List.of(student));
        when(adminService.findAdmin(100)).thenReturn(new Admin());
        when(bookService.searchBook("id", "1")).thenReturn(List.of(book1));
        when(mockTransactionDao.findTransactionByStudentAndBookAndTransactionTypeOrderByIdDesc(student, book1, TransactionType.ISSUE))
                .thenReturn(issuanceTransaction);

        transactionService.initiateTxn(request);

        verify(mockTransactionDao, atLeastOnce()).save(any(Transaction.class));
    }


    @Test
    public void testSearchTransaction_IssueTransaction() throws Exception {
        String searchKey = "TransactionType";
        String searchValue = "ISSUE";

        Transaction issuanceTransaction = Transaction.builder()
                .transactionType(TransactionType.ISSUE)
                .build();

        when(mockTransactionDao.findByTransactionType(TransactionType.ISSUE)).thenReturn(List.of(issuanceTransaction));

        List<Transaction> transactionList = transactionService.searchTransaction(searchKey, searchValue);

        assertEquals(transactionList.getFirst().getTransactionType(), TransactionType.ISSUE);
    }


    @Test
    public void testSearchTransaction_ReturnTransaction() throws Exception {
        String searchKey = "TransactionType";
        String searchValue = "RETURN";

        Transaction returnTransaction = Transaction.builder()
                .transactionType(TransactionType.RETURN)
                .build();

        when(mockTransactionDao.findByTransactionType(TransactionType.RETURN)).thenReturn(List.of(returnTransaction));

        List<Transaction> transactionList = transactionService.searchTransaction(searchKey, searchValue);

        assertEquals(transactionList.getFirst().getTransactionType(), TransactionType.RETURN);
    }


    @Test
    public void testSearchTransaction_SuccessStatus() throws Exception {
        String searchKey = "TransactionStatus";
        String searchValue = "SUCCESS";

        Transaction successTransaction = Transaction.builder()
                .transactionStatus(TransactionStatus.SUCCESS)
                .build();

        when(mockTransactionDao.findByTransactionStatus(TransactionStatus.SUCCESS)).thenReturn(List.of(successTransaction));

        List<Transaction> transactionList = transactionService.searchTransaction(searchKey, searchValue);

        assertEquals(transactionList.getFirst().getTransactionStatus(), TransactionStatus.SUCCESS);
    }


    @Test
    public void testSearchTransaction_FailureStatus() throws Exception {
        String searchKey = "TransactionStatus";
        String searchValue = "FAILURE";

        Transaction successTransaction = Transaction.builder()
                .transactionStatus(TransactionStatus.FAILURE)
                .build();

        when(mockTransactionDao.findByTransactionStatus(TransactionStatus.FAILURE)).thenReturn(List.of(successTransaction));

        List<Transaction> transactionList = transactionService.searchTransaction(searchKey, searchValue);

        assertEquals(transactionList.getFirst().getTransactionStatus(), TransactionStatus.FAILURE);
    }

    @Test
    public void testSearchTransaction_PendingStatus() throws Exception {
        String searchKey = "TransactionStatus";
        String searchValue = "PENDING";

        Transaction pendingTransaction = Transaction.builder()
                .transactionStatus(TransactionStatus.PENDING)
                .build();

        when(mockTransactionDao.findByTransactionStatus(TransactionStatus.PENDING)).thenReturn(List.of(pendingTransaction));

        List<Transaction> transactionList = transactionService.searchTransaction(searchKey, searchValue);

        assertEquals(transactionList.getFirst().getTransactionStatus(), TransactionStatus.PENDING);
    }


    @Test
    public void testSearchTransaction_Exception() throws Exception {
        String searchKey = "TransactionDate";
        String searchValue = "SUCCESS";


        Exception exception = assertThrows(Exception.class, () -> {
                                transactionService.searchTransaction(searchKey, searchValue);
        });

        assertEquals(exception.getMessage(), "Invalid Search Key: " + searchKey);
    }
}
