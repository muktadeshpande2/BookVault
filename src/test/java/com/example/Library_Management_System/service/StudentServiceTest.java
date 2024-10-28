package com.example.Library_Management_System.service;

import com.example.Library_Management_System.model.Student;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class StudentServiceTest {
    @Mock
    private StudentDao mockStudentDao;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddStudentOrUpdate() {
        Student student = Student.builder()
                .id(1)
                .name("John")
                .age(12)
                .email("john@gmail.com")
                .rollNumber("12")
                .build();

        when(mockStudentDao.save(student)).thenReturn(student);
    }

    @Test
    public void testFindStudentById() throws Exception {

        String searchKey = "id";
        String searchValue = "1";

        Student expectedStudent = Student.builder()
                .id(1)
                .name("John")
                .age(12)
                .email("john@gmail.com")
                .rollNumber("12")
                .build();

        when(mockStudentDao.findById(Integer.parseInt(searchValue))).thenReturn(Optional.ofNullable(expectedStudent));
        List<Student> actualStudent = studentService.searchStudent(searchKey, searchValue);

        Assertions.assertEquals(expectedStudent, actualStudent.getFirst());
    }

    @Test
    public void testFindStudentByRollNumber() throws Exception {

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
    public void testFindStudentByName() throws Exception {

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
    public void testFindStudentByInvalidSearchKey() throws Exception {
        String searchKey = "InvalidSearchKey";
        String searchValue = "1";

        assertThrows(Exception.class, () -> studentService.searchStudent(searchKey, searchValue));
    }

    @Test
    public void addStudentOrUpdate() throws Exception {
        Student student = new Student();
        student.setId(1);
        student.setName("John Walker");

        studentService.addStudentOrUpdate(student);

        verify(mockStudentDao, times(1)).save(student);
    }
}
