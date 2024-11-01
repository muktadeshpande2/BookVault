package com.example.Library_Management_System.service;

import com.example.Library_Management_System.model.Author;
import com.example.Library_Management_System.model.Book;
import com.example.Library_Management_System.model.Genre;
import com.example.Library_Management_System.model.Publication;
import com.example.Library_Management_System.repository.AuthorDao;
import com.example.Library_Management_System.repository.BookDao;
import com.example.Library_Management_System.repository.TransactionDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private TransactionDao mockTransactionDao;

    @Mock
    private BookDao mockBookDao;

    @Mock
    private AuthorDao mockAuthorDao;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testAddBookOrUpdate() throws Exception {

        Author author = new Author();
        author.setName("Author Name");

        Book book = new Book();
        book.setId(1);
        book.setBookName("Test Book");
        book.setBook_author(author);

        when(authorService.getOrCreate(author)).thenReturn(author);
        when(mockBookDao.save(book)).thenReturn(book);

        bookService.addBookOrUpdate(book);

        verify(authorService, times(1)).getOrCreate(author);
        verify(mockBookDao, times(1)).save(book);

    }

    @Test
    public void testSearchBookById() throws Exception {

        String searchKey = "id";
        String searchValue = "1";

        Book expectedBook = Book.builder()
                .id(1)
                .bookPrice(200.9)
                .bookName("Harry Potter")
                .publication(Publication.valueOf("SWAN"))
                .genre(Genre.valueOf("FICTION"))
                .build();

        when(mockBookDao.findById(Integer.parseInt(searchValue))).thenReturn(Optional.ofNullable(expectedBook));
        List<Book> actualStudent = bookService.searchBook(searchKey, searchValue);

        assertEquals(expectedBook, actualStudent.getFirst());
    }

    @Test
    public void testSearchBookByGenre() throws Exception {

        String searchKey = "genre";
        String searchValue = "FICTION";

        Book expectedBook = Book.builder()
                .id(1)
                .bookPrice(200.9)
                .bookName("Harry Potter")
                .publication(Publication.valueOf("SWAN"))
                .genre(Genre.valueOf("FICTION"))
                .build();

        List<Book> expectedBookList = new ArrayList<>();
        expectedBookList.add(expectedBook);

        when(mockBookDao.findByGenre(Genre.valueOf(searchValue))).thenReturn(expectedBookList);
        List<Book> actualBookList = bookService.searchBook(searchKey, searchValue);
        assertEquals(expectedBookList, actualBookList);
    }

    @Test
    public void testSearchBookByName() throws Exception {

        String searchKey = "name";
        String searchValue = "Harry Potter";

        Book expectedBook = Book.builder()
                .id(1)
                .bookPrice(200.9)
                .bookName("Harry Potter")
                .publication(Publication.valueOf("SWAN"))
                .genre(Genre.valueOf("FICTION"))
                .build();

        List<Book> expectedBookList = new ArrayList<>();
        expectedBookList.add(expectedBook);

        when(mockBookDao.findByBookName(searchValue)).thenReturn(expectedBookList);
        List<Book> actualBookList = bookService.searchBook(searchKey, searchValue);
        assertEquals(expectedBookList, actualBookList);
    }


    @Test
    public void testSearchBookByPublication() throws Exception {

        String searchKey = "publication";
        String searchValue = "SWAN";

        Book expectedBook = Book.builder()
                .id(1)
                .bookPrice(200.9)
                .bookName("Harry Potter")
                .publication(Publication.valueOf("SWAN"))
                .genre(Genre.valueOf("FICTION"))
                .build();

        List<Book> expectedBookList = new ArrayList<>();
        expectedBookList.add(expectedBook);

        when(mockBookDao.findByPublication(Publication.valueOf(searchValue))).thenReturn(expectedBookList);
        List<Book> actualBookList = bookService.searchBook(searchKey, searchValue);
        assertEquals(expectedBookList, actualBookList);
    }

    @Test
    public void testSearchBookByAuthorName() throws Exception {

        String searchKey = "author_name";
        String searchValue = "Behrouz A. Forouzan";

        Author bookAuthor = Author.builder()
                .id(4)
                .name("Behrouz A. Forouzan")
                .email("baf@gmail.com")
                .build();

        Book expectedBook = Book.builder()
                .id(4)
                .bookPrice(400.0)
                .bookName("Cryptography And Network Security  ")
                .publication(Publication.valueOf("RED_DOT"))
                .genre(Genre.valueOf("NETWORKING"))
                .book_author(bookAuthor)
                .build();

        List<Book> expectedBookList = new ArrayList<>();
        expectedBookList.add(expectedBook);

        when(mockBookDao.findByAuthorName("Behrouz A. Forouzan")).thenReturn(expectedBookList);
        List<Book> actualBookList = bookService.searchBook(searchKey, searchValue);
        assertEquals(expectedBookList, actualBookList);
    }


    @Test
    public void testSearchBookByInvalidSearchKey() throws Exception {
        String searchKey = "InvalidSearchKey";
        String searchValue = "1";

        assertThrows(Exception.class, () -> bookService.searchBook(searchKey, searchValue));
    }


    @Test
    public void testDeleteBook_Exception() throws Exception {
        Book book = Book.builder()
                .id(19)
                .bookPrice(200.9)
                .bookName("Harry Potter")
                .publication(Publication.valueOf("SWAN"))
                .genre(Genre.valueOf("FICTION"))
                .build();

        Optional<Book> bookResult = Optional.empty();
        when(mockBookDao.findById(19)).thenReturn(bookResult);

        assertThrows(Exception.class, () -> bookService.deleteBook(book));
    }


    @Test
    public void testDeleteBook_Success() throws Exception {
        Author bookAuthor = Author.builder()
                .id(4)
                .name("Behrouz A. Forouzan")
                .email("baf@gmail.com")
                .build();

        Book book = Book.builder()
                .id(4)
                .bookPrice(400.0)
                .bookName("Cryptography And Network Security  ")
                .publication(Publication.valueOf("RED_DOT"))
                .genre(Genre.valueOf("NETWORKING"))
                .book_author(bookAuthor)
                .build();

        Optional<Book> bookResult = Optional.of(book);

        List<Book> bookList = new ArrayList<>(List.of(book));
        when(mockBookDao.findById(4)).thenReturn(bookResult);
        when(mockBookDao.findByAuthorName(any(String.class))).thenReturn(bookList);

        String result = bookService.deleteBook(book);

        verify(mockTransactionDao, times(1)).deleteByBookId(4);
        verify(mockBookDao, times(1)).deleteById(4);
        verify(mockAuthorDao, times(1)).deleteByName("Behrouz A. Forouzan");
        assertEquals(result, "Book has been deleted successfully");

    }

}
