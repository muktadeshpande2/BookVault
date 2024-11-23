package com.example.Library_Management_System.service;

import com.example.Library_Management_System.Utils.Constants;
import com.example.Library_Management_System.model.Student;
import com.example.Library_Management_System.model.User;
import com.example.Library_Management_System.repository.StudentCacheRepo;
import com.example.Library_Management_System.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentDao studentDao;

    @Autowired
    UserService userService;

    @Autowired
    StudentCacheRepo studentCacheRepo;

    public void addStudentOrUpdate(Student student) throws Exception {
        User user = student.getUser();
        user = userService.save(Constants.STUDENT_USER, user);

        if(user.getId() == null) {
            throw new Exception("Invalid User");
        }

        student.setUser(user);
        studentDao.save(student);
    }

    public List<Student> searchStudent(String searchKey, String searchValue) throws Exception {
        return switch (searchKey) {
            case "id" -> List.of(searchById(Integer.parseInt(searchValue)));
            case "rollNumber" -> studentDao.findByRollNumber(searchValue);
            case "name" -> studentDao.findByName(searchValue);
            case "email" -> studentDao.findByEmail(searchValue);
            default -> throw new Exception("Invalid Search Key: " + searchKey);
        };
    }

    public Student searchById(Integer studentId) {
        //Checking if the details are present in the cache
        Student student = studentCacheRepo.getStudent(studentId);

        if(student != null) {
            return student;
        }

        //If null i.e. not present in cache, then fetching from the DB or else creating new student
         student = studentDao.findById(studentId).orElse(new Student());
         studentCacheRepo.setStudent(student);

        return student;
    }
}
