package com.example.Library_Management_System.repository;

import com.example.Library_Management_System.model.Book;
import com.example.Library_Management_System.model.Student;
import com.example.Library_Management_System.model.Transaction;
import com.example.Library_Management_System.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Integer> {

//    @Query("Select t from Transaction t where t.student = :student and t.book = :book and t.transactionType =  :transactionType")
    Transaction findTransactionByStudentAndBookAndTransactionTypeOrderByIdDesc(Student student, Book book, TransactionType transactionType);
}

