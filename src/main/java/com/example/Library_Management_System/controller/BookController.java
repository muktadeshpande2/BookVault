package com.example.Library_Management_System.controller;

import com.example.Library_Management_System.dto.AddBookRequest;
import com.example.Library_Management_System.dto.BookResponse;
import com.example.Library_Management_System.dto.SearchBookRequest;
import com.example.Library_Management_System.dto.SearchBookResponse;
import com.example.Library_Management_System.model.Book;
import com.example.Library_Management_System.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;


    //JpaRepository interface uses the same method for creating and updating a DB
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
            //createResponse appropriate exception
            return new SearchBookResponse();
        }

    }
    //TODO->delete a book
}
