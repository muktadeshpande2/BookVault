package com.example.Library_Management_System.service;

import com.example.Library_Management_System.Utils.Constants;
import com.example.Library_Management_System.model.Admin;
import com.example.Library_Management_System.model.Student;
import com.example.Library_Management_System.model.User;
import com.example.Library_Management_System.repository.StudentCacheRepo;
import com.example.Library_Management_System.repository.StudentDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class StudentServiceTest {
    @Mock
    private StudentDao mockStudentDao;

    @InjectMocks
    private StudentService studentService;

    @Mock
    private UserService mockUserService;

    @Mock
    private StudentCacheRepo mockStudentCacheRepo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddStudentOrUpdate_Exception() throws Exception {
        Student student = new Student();
        User user = new User();
        user.setId(null);
        student.setUser(user);

        when(mockUserService.save(Constants.STUDENT_USER, user)).thenReturn(user);


        Exception exception = assertThrows(Exception.class, () ->{studentService.addStudentOrUpdate(student);
        });

        assertEquals("Invalid User", exception.getMessage());
        verify(mockUserService, times(1)).save(Constants.STUDENT_USER, user);
        verifyNoInteractions(mockStudentDao);
    }

    @Test
    public void testAddStudentOrUpdate() throws Exception {
        Student student = new Student();
        User user = new User();
        user.setId(1);
        student.setUser(user);

        when(mockUserService.save(Constants.STUDENT_USER, user)).thenReturn(user);

        studentService.addStudentOrUpdate(student);

        verify(mockStudentDao, times(1)).save(student);
    }

    @Test
    public void testSearchStudentById() throws Exception {

        String searchKey = "id";
        String searchValue = "1";

        Student expectedStudent = Student.builder()
                .id(1)
                .name("John")
                .build();


        when(mockStudentCacheRepo.getStudent(1)).thenReturn(null);
        when(mockStudentDao.findById(Integer.parseInt(searchValue))).thenReturn(Optional.ofNullable(expectedStudent));
        List<Student> actualStudent = studentService.searchStudent(searchKey, searchValue);

        Assertions.assertEquals(expectedStudent, actualStudent.getFirst());
    }

    @Test
    public void testSearchStudentById_StudentIsNotInCache() throws Exception {
        String searchKey = "id";
        String searchValue = "1";

        Student expectedStudent = Student.builder()
                .id(1)
                .name("John")
                .build();

        when(mockStudentDao.findById(Integer.parseInt(searchValue))).thenReturn(Optional.ofNullable(expectedStudent));
        when(mockStudentCacheRepo.getStudent(1)).thenReturn(expectedStudent);
        List<Student> actualStudent = studentService.searchStudent(searchKey, searchValue);

        Assertions.assertEquals(expectedStudent, actualStudent.getFirst());
    }

    @Test
    public void testSearchStudentByRollNumber() throws Exception {

        String searchKey = "rollNumber";
        String searchValue = "12";

        Student expectedStudent = Student.builder()
                .id(1)
                .name("John")
                .age(12)
                .email("john@gmail.com")
                .rollNumber("12")
                .build();

        List<Student> expectedStudentList = new ArrayList<>();
        expectedStudentList.add(expectedStudent);

        when(mockStudentDao.findByRollNumber(searchValue)).thenReturn(expectedStudentList);
        List<Student> actualStudentList = studentService.searchStudent(searchKey, searchValue);
        Assertions.assertEquals(expectedStudentList, actualStudentList);
    }

    @Test
    public void testSearchStudentByName() throws Exception {

        String searchKey = "name";
        String searchValue = "John";

        Student expectedStudent = Student.builder()
                .id(1)
                .name("John")
                .age(12)
                .email("john@gmail.com")
                .rollNumber("12")
                .build();

        List<Student> expectedStudentList = new ArrayList<>();
        expectedStudentList.add(expectedStudent);

        when(mockStudentDao.findByName(searchValue)).thenReturn(expectedStudentList);
        List<Student> actualStudentList = studentService.searchStudent(searchKey, searchValue);
        Assertions.assertEquals(expectedStudentList, actualStudentList);
    }


    @Test
    public void testSearchStudentByEmail() throws Exception {

        String searchKey = "email";
        String searchValue = "john12@gmail.com";

        Student expectedStudent = Student.builder()
                .id(1)
                .name("John")
                .age(12)
                .email(searchValue)
                .rollNumber("12")
                .build();

        List<Student> expectedStudentList = new ArrayList<>();
        expectedStudentList.add(expectedStudent);

        when(mockStudentDao.findByEmail(searchValue)).thenReturn(expectedStudentList);
        List<Student> actualStudentList = studentService.searchStudent(searchKey, searchValue);
        Assertions.assertEquals(expectedStudentList, actualStudentList);
    }


    @Test
    public void testSearchStudentByInvalidSearchKey() throws Exception {
        String searchKey = "InvalidSearchKey";
        String searchValue = "1";

        assertThrows(Exception.class, () -> studentService.searchStudent(searchKey, searchValue));
    }

}
