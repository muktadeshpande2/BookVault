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

        Author existingAuthor = authorDao.findByEmail(author.getEmail());

        if(existingAuthor == null) {
            existingAuthor = authorDao.save(author);
        }

        return existingAuthor;

    }
}
