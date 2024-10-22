package com.example.Library_Management_System.controller;

import com.example.Library_Management_System.dto.*;
import com.example.Library_Management_System.model.Book;
import com.example.Library_Management_System.service.BookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;


    @PostMapping("/add")
    public ResponseEntity<String> addOrUpdateBook(@RequestBody @Valid AddBookRequest addBookRequest) {
        bookService.addBookOrUpdate(addBookRequest.to());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Book Added Successfully");
    }


    @GetMapping("/search")
    public SearchBookResponse searchBook(@RequestBody @Valid SearchBookRequest searchbookRequest){
        try {
            List<Book> bookList =  bookService.searchBook(searchbookRequest.getSearchKey(), searchbookRequest.getSearchValue());
            List<BookResponse> bookResponseList = searchbookRequest.createResponse(bookList);
            return new SearchBookResponse(bookResponseList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new SearchBookResponse();
        }

    }


    @DeleteMapping("/delete")
    public DeleteBookResponse deleteBook(@RequestBody @Valid DeleteBookRequest deleteBookRequest){

        try {
            String result = bookService.deleteBook(deleteBookRequest.to());
            return new DeleteBookResponse(result);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new DeleteBookResponse();
        }

    }
}
