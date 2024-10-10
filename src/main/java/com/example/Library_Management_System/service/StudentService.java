package com.example.Library_Management_System.service;

import com.example.Library_Management_System.model.Student;
import com.example.Library_Management_System.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    StudentDao studentDao;

    public void addStudentOrUpdate(Student student) {
        studentDao.save(student);
    }

    public List<Student> searchStudent(String searchKey, String searchValue) throws Exception {
        return switch (searchKey) {
            case "id" -> {
                Optional<Student> student = studentDao.findById(Integer.valueOf(searchValue));
                yield student.map(List::of).orElseGet(ArrayList::new);
            }
            case "rollNumber" -> studentDao.findByRollNumber(searchValue);
            case "name" -> studentDao.findByName(searchValue);
            case "email" -> studentDao.findByEmail(searchValue);
            default -> throw new Exception("Invalid Search Key: " + searchKey);
        };
    }
}
