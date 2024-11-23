package com.example.Library_Management_System.service;

import com.example.Library_Management_System.Utils.Constants;
import com.example.Library_Management_System.model.Admin;
import com.example.Library_Management_System.model.User;
import com.example.Library_Management_System.repository.AdminDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminDao mockAdminDao;

    @Mock
    private UserService mockUserService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrUpdateAdmin_Exception() throws Exception {
        Admin admin = new Admin();
        User user = new User();
        user.setId(null);
        admin.setUser(user);

        when(mockUserService.save(Constants.ADMIN_USER, user)).thenReturn(user);


        Exception exception = assertThrows(Exception.class, () ->{adminService.createOrUpdateAdmin(admin);
        });

        assertEquals("Invalid User", exception.getMessage());
        verify(mockUserService, times(1)).save(Constants.ADMIN_USER, user);
        verifyNoInteractions(mockAdminDao);
    }

    @Test
    public void testCreateOrUpdateAdmin() throws Exception {
        Admin admin = new Admin();
        User user = new User();
        user.setId(1);
        admin.setUser(user);

        when(mockUserService.save(Constants.ADMIN_USER, user)).thenReturn(user);

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
