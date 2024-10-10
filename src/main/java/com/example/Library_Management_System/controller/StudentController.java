package com.example.Library_Management_System.controller;

import com.example.Library_Management_System.dto.AddStudentRequest;
import com.example.Library_Management_System.dto.SearchStudentRequest;
import com.example.Library_Management_System.model.Student;
import com.example.Library_Management_System.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;


    @PostMapping("/add")
    public ResponseEntity<String> addOrUpdateStudent(@RequestBody @Valid AddStudentRequest addStudentRequest) {
        studentService.addStudentOrUpdate(addStudentRequest.to());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Student Added Successfully");
    }


    @GetMapping("/search")
    public List<Student> searchStudent(@RequestBody @Valid SearchStudentRequest searchStudentRequest){
        try {
            return studentService.searchStudent(searchStudentRequest.getSearchKey(), searchStudentRequest.getSearchValue());
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }



}
