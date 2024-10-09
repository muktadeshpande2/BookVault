package com.example.Library_Management_System.service;

import com.example.Library_Management_System.model.Author;
import com.example.Library_Management_System.model.Book;
import com.example.Library_Management_System.model.Genre;
import com.example.Library_Management_System.model.Publication;
import com.example.Library_Management_System.repository.AuthorDao;
import com.example.Library_Management_System.repository.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    @Autowired
    AuthorService authorService;


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

    public List<Book> searchBook(String searchKey, String searchValue) throws Exception {
        return switch (searchKey) {
            case "id" -> {
                Optional<Book> book = bookDao.findById(Integer.valueOf(searchValue));
                yield book.map(List::of).orElseGet(ArrayList::new);
            }
            case "name" -> bookDao.findByBookName(searchValue);
            case "genre" -> bookDao.findByGenre(Genre.valueOf(searchValue));
            case "publication" -> bookDao.findByPublication(Publication.valueOf(searchValue));
            case "author_name" -> bookDao.findByAuthorName(searchValue);
            default -> throw new Exception("Invalid Search Key: " + searchKey);
        };


    }


}
