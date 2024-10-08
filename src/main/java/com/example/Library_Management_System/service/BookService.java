package com.example.Library_Management_System.service;

import com.example.Library_Management_System.model.Author;
import com.example.Library_Management_System.model.Book;
import com.example.Library_Management_System.repository.AuthorDao;
import com.example.Library_Management_System.repository.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    @Autowired
    AuthorService authorService;

    @Autowired
    AuthorDao authorDao;

    public void addBookOrUpdate(Book book) {
        //first check if author already exists or not
        Author author = authorService.getOrCreate(book.getBook_author());
        //if it doesn't, create it

        // set the author
        //the author object returned from the dto does not contain an id, the author object
        //return from getOrCreate method has the complete author object with all the fields
        book.setBook_author(author);
        //save the book
        bookDao.save(book);
    }
}
