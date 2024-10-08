package com.example.Library_Management_System.service;

import com.example.Library_Management_System.model.Author;
import com.example.Library_Management_System.repository.AuthorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorDao authorDao;

    public Author getOrCreate(Author author) {

        //try to get the author with same email
        Author existingAuthor = authorDao.findByEmail(author.getEmail());
        //if it exists -> return the author object
        if(existingAuthor == null) {
            existingAuthor = authorDao.save(author);
        }

        //if it doesn't exist -> create the author and return the same
        return existingAuthor;

    }
}
