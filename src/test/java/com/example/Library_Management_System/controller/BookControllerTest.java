package com.example.Library_Management_System.controller;

import com.example.Library_Management_System.dto.*;
import com.example.Library_Management_System.model.Book;
import com.example.Library_Management_System.model.Genre;
import com.example.Library_Management_System.model.Publication;
import com.example.Library_Management_System.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void testAddOrUpdateBook_Success() throws Exception{
        AddBookRequest request = new AddBookRequest();
        request.setName("Book");
        request.setPrice(200.08);
        request.setGenre(Genre.valueOf("FICTION"));
        request.setPublication(Publication.valueOf("INSPIRATION"));
        request.setAuthorName("Author Name");
        request.setAuthorEmail("author@xyz.com");

        doNothing().when(bookService).addBookOrUpdate(any());

        // POST API with valid JSON body
        mockMvc.perform(post("/book/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Book Added Successfully"));
    }

    @Test
    void testSearchBook_Success() throws Exception {

        SearchBookRequest searchRequest = new SearchBookRequest();
        searchRequest.setSearchKey("name");
        searchRequest.setSearchValue("J K Rowling");

        Book book = Book.builder()
                .bookName("BookName")
                .id(1)
                .bookPrice(200.0)
                .genre(Genre.valueOf("FICTION"))
                .publication(Publication.valueOf("INSPIRATION"))
                .build();

        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        List<BookResponse> responseList = new ArrayList<>();
        responseList.add(BookResponse.builder()
                .bookName("BookName")
                .bookPrice(200.0)
                .genre(Genre.valueOf("FICTION"))
                .publication(Publication.valueOf("INSPIRATION"))
                .build());

        when(bookService.searchBook(searchRequest.getSearchKey(), searchRequest.getSearchValue()))
                .thenReturn(bookList);

        // GET API with valid JSON body
        mockMvc.perform(get("/book/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SearchBookResponse(responseList))));

    }

    @Test
    void testSearchBook_NoResult() throws Exception {
        SearchBookRequest searchRequest = new SearchBookRequest();
        searchRequest.setSearchKey("name");
        searchRequest.setSearchValue("J K Rowling");

        List<Book> emptyList = new ArrayList<>();

        // Mocking the service
        when(bookService.searchBook(searchRequest.getSearchKey(), searchRequest.getSearchValue()))
                .thenReturn(emptyList);

        // GET API with valid JSON body
        mockMvc.perform(get("/book/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookResponseList").isEmpty());
    }

    @Test
    public void testSearchBook_Exception() throws Exception {

        SearchStudentRequest searchRequest = SearchStudentRequest.builder()
                .searchKey("invalidKey")
                .searchValue("1")
                .build();

        when(bookService.searchBook(searchRequest.getSearchKey(), searchRequest.getSearchValue()))
                .thenThrow(new Exception("Invalid Search Key " + searchRequest.getSearchKey()));


        // GET API with valid JSON body
        mockMvc.perform(get("/book/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookResponseList").isEmpty());
    }


    @Test
    public void testDeleteBook_Success() throws Exception {
        DeleteBookRequest request = new DeleteBookRequest(2);
        String message = "Book Deleted Successfully";

        when(bookService.deleteBook(any())).thenReturn(message);


        // DELETE API with valid JSON body
        mockMvc.perform(delete("/book/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new DeleteBookResponse(message))));


    }


    @Test
    public void testDeleteBook_Exception() throws Exception {
        DeleteBookRequest request = new DeleteBookRequest(9);

        when(bookService.deleteBook(any()))
                .thenThrow(new Exception("Invalid Book Id " + request.to().getId()));

        // DELETE API with valid JSON body
        mockMvc.perform(delete("/book/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new DeleteBookResponse())));
    }
}
