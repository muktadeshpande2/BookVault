package com.example.Library_Management_System.service;

import com.example.Library_Management_System.Utils.Constants;
import com.example.Library_Management_System.model.Admin;
import com.example.Library_Management_System.model.User;
import com.example.Library_Management_System.repository.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    AdminDao adminDao;

    @Autowired
    UserService userService;

    public void createOrUpdateAdmin(Admin admin) throws Exception {

        User user = admin.getUser();
        user = userService.save(Constants.ADMIN_USER, user);

        if(user.getId() == null) {
            throw new Exception("Invalid User");
        }

        admin.setUser(user);
        adminDao.save(admin);
    }

    public Admin findAdmin(Integer adminId) {
        return adminDao.findById(adminId).orElse(null);
    }
}
