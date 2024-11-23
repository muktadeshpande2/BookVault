package com.example.Library_Management_System;

import com.example.Library_Management_System.model.Admin;
import com.example.Library_Management_System.model.User;
import com.example.Library_Management_System.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryManagementSystemApplication implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(LibraryManagementSystemApplication.class);

//	@Autowired
//	AdminService adminService;

	public static void main(String[] args) {
		log.info("Here Before run");
		SpringApplication.run(LibraryManagementSystemApplication.class, args);
		log.info("Here after run");
	}


	@Override
	public void run(String... args) throws Exception {
		log.info("Here while run");
//		Admin admin = Admin.builder()
//				.name("Admin")
//				.email("admin@hotmail.com")
//				.user(User.builder()
//						.username("admin")
//						.password("123456789")
//						.build())
//				.build();
//
//		adminService.createOrUpdateAdmin(admin);
	}
}
