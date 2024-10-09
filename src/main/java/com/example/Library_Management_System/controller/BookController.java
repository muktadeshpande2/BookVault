package com.example.Library_Management_System.controller;

import com.example.Library_Management_System.dto.AddBookRequest;
import com.example.Library_Management_System.dto.GetBookResponse;
import com.example.Library_Management_System.dto.RemoveBookRequest;
import com.example.Library_Management_System.dto.SearchRequest;
import com.example.Library_Management_System.model.Book;
import com.example.Library_Management_System.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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


    //TODO -> convert to dto
    //Search book by id, name, genre, publication, author_name
    @GetMapping("/search")
    public List<Book> searchBook(@RequestBody @Valid SearchRequest searchRequest){
        try {
            return bookService.searchBook(searchRequest.getSearchKey(), searchRequest.getSearchValue());
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }

    //TODO->delete a book
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeBook(@RequestBody @Valid RemoveBookRequest removeBookRequest) {

    }
}
