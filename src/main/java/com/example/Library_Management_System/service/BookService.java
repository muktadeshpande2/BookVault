package com.example.Library_Management_System.service;

import com.example.Library_Management_System.model.Book;
import com.example.Library_Management_System.repository.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    public void addBook(Book book) {
        bookDao.save(book);
    }
}
