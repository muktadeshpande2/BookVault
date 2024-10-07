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

//@RestController annotation performs the function of @Controller and @ResponseBody
//@Controller annotation is used for a class that contains methods that handle incoming user requests and return response in form of views
//@ResponseBody annotation serializes the response object into JSON(or XML) data and gets written directly to HTTP response
@RestController
@RequestMapping("/admin")
public class AdminController {

    //Field Dependency Injection
    @Autowired
    AdminService adminService;

    //@RequestBody annotation maps the JSON data sent by the client to a java object(the DTO). Spring deserializes JSON data to java object.
    //@Valid annotation performs validations specified for different fields of the DTO
    @PostMapping("/create")
    public ResponseEntity<String>createAdmin(@RequestBody @Valid CreateAdminRequest createAdminRequest) {

        //DTO to Model conversion
        adminService.createAdmin(createAdminRequest.to());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Admin got created successfully");
    }
}
