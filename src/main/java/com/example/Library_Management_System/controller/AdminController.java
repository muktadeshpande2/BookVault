package com.example.Library_Management_System.controller;

import com.example.Library_Management_System.dto.CreateAdminRequest;
import com.example.Library_Management_System.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/create")
    public ResponseEntity<String>createOrUpdateAdmin(@RequestBody @Valid CreateAdminRequest createAdminRequest) {

        try{
            adminService.createOrUpdateAdmin(createAdminRequest.to());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Admin got created successfully");

        } catch(Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
