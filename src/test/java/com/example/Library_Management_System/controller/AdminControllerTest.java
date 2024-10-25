package com.example.Library_Management_System.controller;

import com.example.Library_Management_System.dto.CreateAdminRequest;
import com.example.Library_Management_System.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminControllerTest {


    @InjectMocks
    private AdminController adminController;

    @Mock
    AdminService adminService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void testCreateOrUpdateAdmin_Success() throws Exception{
        CreateAdminRequest request = new CreateAdminRequest();
        request.setName("Admin");
        request.setEmail("example@xyz.com");

        doNothing().when(adminService).createOrUpdateAdmin(request.to());

        // POST API with valid JSON body
        mockMvc.perform(post("/admin/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Admin got created successfully"));
    }
}
