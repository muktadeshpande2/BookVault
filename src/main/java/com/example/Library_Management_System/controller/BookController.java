package com.example.Library_Management_System.controller;

import com.example.Library_Management_System.dto.AddBookRequest;
import com.example.Library_Management_System.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody @Valid AddBookRequest addBookRequest) {
        bookService.addBook(addBookRequest.to());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Book Added Successfully");
    }
}