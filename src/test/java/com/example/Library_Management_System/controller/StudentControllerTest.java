package com.example.Library_Management_System.controller;

import com.example.Library_Management_System.dto.AddStudentRequest;
import com.example.Library_Management_System.dto.SearchStudentRequest;
import com.example.Library_Management_System.dto.SearchStudentResponse;
import com.example.Library_Management_System.dto.StudentResponse;
import com.example.Library_Management_System.model.Student;
import com.example.Library_Management_System.model.User;
import com.example.Library_Management_System.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }


    @Test
    void testAddOrUpdateStudent_Success() throws Exception {
        AddStudentRequest request = AddStudentRequest.builder()
                                    .gender("Male")
                                    .name("XYZ")
                                    .email("xyz@example.com")
                                    .rollNumber("123")
                                    .age(18)
                                    .username("student")
                                    .password("5679")
                                    .build();

        doNothing().when(studentService).addStudentOrUpdate(any());

        // POST API with valid JSON body
        mockMvc.perform(post("/student/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Student Added Successfully"));
    }


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


        // GET API with valid JSON body
        mockMvc.perform(get("/student/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SearchStudentResponse(new ArrayList<>(Arrays.asList(expectedStudent.to()))))));

    }


    @Test
    void testSearchStudent_NoResult() throws Exception {
        SearchStudentRequest searchRequest = new SearchStudentRequest();
        searchRequest.setSearchKey("email");
        searchRequest.setSearchValue("unknown@example.com");

        List<Student> emptyList = new ArrayList<>();

        // Mocking the service
        when(studentService.searchStudent("email", "unknown@example.com")).thenReturn(emptyList);

        // GET API with valid JSON body
        mockMvc.perform(get("/student/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentResponseList").isEmpty());
    }


    @Test
    public void testSearchStudent_Exception() throws Exception {

        SearchStudentRequest searchRequest = SearchStudentRequest.builder()
                .searchKey("invalidKey")
                .searchValue("1")
                .build();

        when(studentService.searchStudent(searchRequest.getSearchKey(), searchRequest.getSearchValue()))
                .thenThrow(new Exception("Invalid Search Key " + searchRequest.getSearchKey()));


        // GET API with valid JSON body
        mockMvc.perform(get("/student/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentResponseList").isEmpty());
    }


    @Test
    public void testGetInfo_Success() throws Exception{
        User mockUser = new User();
        Student mockStudent = new Student();
        mockStudent.setId(1);
        mockStudent.setName("Johnny English");
        mockUser.setStudent(mockStudent);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(studentService.searchStudent("id", "1")).thenReturn(Collections.singletonList(mockStudent));

        ResponseEntity<StudentResponse> response = studentController.getInfo();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Johnny English", response.getBody().getName());
        verify(studentService, times(1)).searchStudent("id", "1");
    }

    @Test
    public void testGetInfo_BadRequest() throws Exception {
        User mockUser = new User();
        Student mockStudent = new Student();
        mockStudent.setId(1);
        mockStudent.setName("Johnny English");
        mockUser.setStudent(mockStudent);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(studentService.searchStudent("id", "1")).thenThrow(new Exception("Student Not Found"));

        ResponseEntity<StudentResponse> response = studentController.getInfo();

        assertEquals(400, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Student Not Found", response.getBody().getErrorMessage());
        verify(studentService, times(1)).searchStudent("id", "1");
    }
}
