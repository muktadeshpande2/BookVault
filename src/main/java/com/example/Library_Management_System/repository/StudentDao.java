package com.example.Library_Management_System.repository;

import com.example.Library_Management_System.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao extends JpaRepository<Student, Integer> {

    List<Student> findByRollNumber(String rollNumber);

    List<Student> findByName(String name);

    List<Student> findByEmail(String email);
}

