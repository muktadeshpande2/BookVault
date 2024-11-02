package com.example.Library_Management_System.service;

import com.example.Library_Management_System.model.Admin;
import com.example.Library_Management_System.repository.AdminDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminDao mockAdminDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrUpdateAdmin() {
        Admin admin = new Admin();
        admin.setId(1);

        when(mockAdminDao.save(admin)).thenReturn(admin);

        adminService.createOrUpdateAdmin(admin);

        verify(mockAdminDao, times(1)).save(admin);
    }


    @Test
    public void testFindAdmin_Found() {
        Integer adminId = 1;
        Admin admin = new Admin();
        admin.setId(adminId);

        when(mockAdminDao.findById(adminId)).thenReturn(Optional.of(admin));

        Admin expectedAdmin = adminService.findAdmin(adminId);

        assertEquals(expectedAdmin, admin);
    }

    @Test
    public void testFindAdmin_NotFound() {
        Integer adminId = 8;
        Optional<Admin> admin = Optional.empty();

        when(mockAdminDao.findById(adminId)).thenReturn(admin);

        Admin expectedAdmin = adminService.findAdmin(adminId);

        assertNull(expectedAdmin);
    }
}
