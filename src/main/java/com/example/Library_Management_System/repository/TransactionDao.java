package com.example.Library_Management_System.repository;

import com.example.Library_Management_System.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Integer> {

    Transaction findTransactionByStudentAndBookAndTransactionTypeOrderByIdDesc(Student student, Book book, TransactionType transactionType);

    List<Transaction> findByTransactionType(TransactionType transactionType);

    List<Transaction> findByTransactionStatus(TransactionStatus transactionStatus);

    @Modifying
    @Transactional
    @Query("DELETE FROM Transaction t WHERE t.book.id = :bookId")
    void deleteByBookId(@Param("bookId") Integer bookId);
}

