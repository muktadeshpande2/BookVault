package com.example.Library_Management_System.repository;

import com.example.Library_Management_System.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AuthorDao extends JpaRepository<Author, Integer> {

    Author findByEmail(String email);

    @Transactional
    @Modifying
    @Query("Delete from Author a where a.name = :authorName")
    void deleteByName(@Param("authorName")String authorName);

}
