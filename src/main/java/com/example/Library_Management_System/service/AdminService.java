package com.example.Library_Management_System.service;

import com.example.Library_Management_System.model.Admin;
import com.example.Library_Management_System.repository.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    AdminDao adminDao;

    public void createAdmin(Admin admin) {
        adminDao.save(admin);
    }
}
