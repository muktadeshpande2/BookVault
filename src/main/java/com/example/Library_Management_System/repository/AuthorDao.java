package com.example.Library_Management_System.repository;

import com.example.Library_Management_System.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorDao extends JpaRepository<Author, Integer> {
    //in Hibernate we can create method like findByX() where X is column of the table
    Author findByEmail(String email);

}
