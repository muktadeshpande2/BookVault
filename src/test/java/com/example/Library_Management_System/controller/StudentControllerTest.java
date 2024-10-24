package com.example.Library_Management_System.controller;

import com.example.Library_Management_System.dto.AddStudentRequest;
import com.example.Library_Management_System.dto.SearchStudentRequest;
import com.example.Library_Management_System.dto.SearchStudentResponse;
import com.example.Library_Management_System.model.Student;
import com.example.Library_Management_System.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    // Test case for adding or updating a student
    @Test
    void testAddOrUpdateStudent_Success() throws Exception {
        AddStudentRequest request = AddStudentRequest.builder()
                                    .gender("Male")
                                    .name("XYZ")
                                    .email("xyz@example.com")
                                    .rollNumber("123")
                                    .age(18)
                                    .build();


        mockMvc.perform(post("/student/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Student Added Successfully"));
    }

    // Test case for searching a student
    @Test
    void testSearchStudent_Success() throws Exception {

        SearchStudentRequest searchRequest = SearchStudentRequest.builder()
                .searchKey("email")
                .searchValue("xyz@example.com")
                .build();

        Student expectedStudent = Student.builder()
                .id(1)
                .name("XYZ")
                .age(12)
                .email("xyz@example.com")
                .rollNumber("12")
                .build();


        List<Student> students = new ArrayList<>();
        students.add(expectedStudent);

        when(studentService.searchStudent(searchRequest.getSearchKey(), searchRequest.getSearchValue()))
                .thenReturn(students);


        // POST API with valid JSON body
        mockMvc.perform(get("/student/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SearchStudentResponse(new ArrayList<>(Arrays.asList(expectedStudent.to()))))));

    }

    // Test case for handling an empty search result
    @Test
    void testSearchStudent_NoResult() throws Exception {
        SearchStudentRequest searchRequest = new SearchStudentRequest();
        searchRequest.setSearchKey("email");
        searchRequest.setSearchValue("unknown@example.com");

        List<Student> emptyList = new ArrayList<>();

        // Mocking the service
        when(studentService.searchStudent("email", "unknown@example.com")).thenReturn(emptyList);

        mockMvc.perform(get("/student/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentResponseList").isEmpty());
    }

    // Test case for handling an exception in searchStudent
    @Test
    public void testSearchStudent_Exception() throws Exception {

        SearchStudentRequest searchRequest = SearchStudentRequest.builder()
                .searchKey("invalidKey")
                .searchValue("1")
                .build();

        when(studentService.searchStudent(searchRequest.getSearchKey(), searchRequest.getSearchValue()))
                .thenThrow(new Exception("Invalid Search Key " + searchRequest.getSearchKey()));


        // POST API with valid JSON body
        mockMvc.perform(get("/student/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentResponseList").isEmpty());
    }
}
