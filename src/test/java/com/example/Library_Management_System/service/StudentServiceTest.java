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
                                .build();

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

        when(mockStudentDao.findById(Integer.parseInt(searchValue))).thenReturn(Optional.ofNullable(expectedStudent));
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
