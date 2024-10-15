package com.example.Library_Management_System.repository;

import com.example.Library_Management_System.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Integer> {

//    @Query("Select t from Transaction t where t.student = :student and t.book = :book and t.transactionType =  :transactionType")
    Transaction findTransactionByStudentAndBookAndTransactionTypeOrderByIdDesc(Student student, Book book, TransactionType transactionType);

    List<Transaction> findByTransactionType(TransactionType transactionType);

    List<Transaction> findByTransactionStatus(TransactionStatus transactionStatus);
}

