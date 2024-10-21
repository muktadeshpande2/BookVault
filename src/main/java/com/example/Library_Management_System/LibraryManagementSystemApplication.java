package com.example.Library_Management_System;

import com.example.Library_Management_System.model.Admin;
import com.example.Library_Management_System.repository.AdminDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryManagementSystemApplication implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(LibraryManagementSystemApplication.class);

	@Autowired
	AdminDao adminDao;

	public static void main(String[] args) {
		log.info("Here Before run");
		SpringApplication.run(LibraryManagementSystemApplication.class, args);
		log.info("Here after run");
	}

	//run method is executed before the execution of the application code
	//It can be used to call an API or create data etc beforehand
	@Override
	public void run(String... args) throws Exception {
		log.info("Here while run");
//		Admin admin = Admin.builder()
//				.name("Admin")
//				.email("admin@hotmail.com")
//				.build();
//
//		adminDao.save(admin);
	}
}
