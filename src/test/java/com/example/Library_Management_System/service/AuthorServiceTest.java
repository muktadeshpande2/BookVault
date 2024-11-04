package com.example.Library_Management_System.service;

import com.example.Library_Management_System.model.Author;
import com.example.Library_Management_System.repository.AuthorDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthorServiceTest {

    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorDao mockAuthorDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetOrCreate_NullAuthor() {
        Author bookAuthor = Author.builder()
                .id(1)
                .name("J K Rowling")
                .email("jk@example.com")
                .build();

        when(mockAuthorDao.findByEmail("jk@example.com")).thenReturn(null);
        when(mockAuthorDao.save(bookAuthor)).thenReturn(bookAuthor);


        Author author = authorService.getOrCreate(bookAuthor);

        verify(mockAuthorDao, times(1)).findByEmail("jk@example.com");
        verify(mockAuthorDao, times(1)).save(bookAuthor);
        assertEquals(bookAuthor, author);
    }


    @Test
    public void testGetOrCreate_AuthorExists() {
        Author bookAuthor = Author.builder()
                .id(1)
                .name("J K Rowling")
                .email("jk@example.com")
                .build();

        when(mockAuthorDao.findByEmail("jk@example.com")).thenReturn(bookAuthor);


        Author author = authorService.getOrCreate(bookAuthor);

        verify(mockAuthorDao, times(1)).findByEmail("jk@example.com");
        assertEquals(bookAuthor, author);
    }
}
