package com.example.Library_Management_System.service;

import com.example.Library_Management_System.repository.AdminDao;
import com.example.Library_Management_System.repository.AuthorDao;
import com.example.Library_Management_System.repository.BookDao;
import com.example.Library_Management_System.repository.StudentDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CSVImportServiceTest {

    @InjectMocks
    private CSVImportService csvImportService;

    @Mock
    private StudentDao mockStudentDao;

    @Mock
    private AdminDao mockAdminDao;

    @Mock
    private AuthorDao mockAuthorDao;

    @Mock
    private BookDao mockBookDao;

    private MultipartFile multipartFile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);


    }


    @Test
    public void testProcessCSVAndSaveStudent_Success() throws Exception {
        String csvContent = "Name,Age,Email,RollNumber\n"
                + "John Doe,20,johndoe@example.com,1001\n"
                + "Jane Smith,22,janesmith@example.com,1002\n";

        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));
        multipartFile = mock(MultipartFile.class);
        try {
            when(multipartFile.getInputStream()).thenReturn(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int expectedStudentCount = 2;

        int savedCount = csvImportService.processCSVAndSaveStudent(multipartFile);

        assertEquals(expectedStudentCount, savedCount);
        verify(mockStudentDao, times(1)).saveAll(anyList());

    }

    @Test
    public void testProcessCSVAndSaveStudent_IOException() throws Exception {
        String csvContent = "Name,Age,Email,RollNumber\n"
                + "John Doe,20,johndoe@example.com,1001\n"
                + "Jane Smith,22,janesmith@example.com,1002\n";

        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));
        multipartFile = mock(MultipartFile.class);
        try {
            when(multipartFile.getInputStream()).thenReturn(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            when(multipartFile.getInputStream()).thenThrow(new IOException("IO Exception"));
            Exception exception = assertThrows(Exception.class, ()-> {
                csvImportService.processCSVAndSaveStudent(multipartFile);
            });

            assertEquals("Unable to parse the csv file", exception.getMessage());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testProcessCSVAndSaveAdmin_Success() throws Exception {

        String csvContent = "Name,Age,Email,RollNumber\n"
                + "John Doe,20,johndoe@example.com,1001\n"
                + "Jane Smith,22,janesmith@example.com,1002\n";

        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));
        multipartFile = mock(MultipartFile.class);
        try {
            when(multipartFile.getInputStream()).thenReturn(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int expectedAdminCount = 2;

        int savedCount = csvImportService.processCSVAndSaveAdmin(multipartFile);

        assertEquals(expectedAdminCount, savedCount);
        verify(mockAdminDao, times(1)).saveAll(anyList());

    }

    @Test
    public void testProcessCSVAndSaveAdmin_IOException() throws Exception {
        String csvContent = "Name,Age,Email,RollNumber\n"
                + "John Doe,20,johndoe@example.com,1001\n"
                + "Jane Smith,22,janesmith@example.com,1002\n";

        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));
        multipartFile = mock(MultipartFile.class);
        try {
            when(multipartFile.getInputStream()).thenReturn(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            when(multipartFile.getInputStream()).thenThrow(new IOException("IO Exception"));
            Exception exception = assertThrows(Exception.class, ()-> {
                csvImportService.processCSVAndSaveAdmin(multipartFile);
            });

            assertEquals("Unable to parse the csv file", exception.getMessage());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testProcessCSVAndSaveAuthor_Success() throws Exception {
        String csvContent = "Name,Age,Email,RollNumber\n"
                + "John Doe,20,johndoe@example.com,1001\n"
                + "Jane Smith,22,janesmith@example.com,1002\n";

        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));
        multipartFile = mock(MultipartFile.class);
        try {
            when(multipartFile.getInputStream()).thenReturn(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int expectedAuthorCount = 2;

        int savedCount = csvImportService.processCSVAndSaveAuthor(multipartFile);

        assertEquals(expectedAuthorCount, savedCount);
        verify(mockAuthorDao, times(1)).saveAll(anyList());

    }

    @Test
    public void testProcessCSVAndSaveAuthor_IOException() throws Exception {
        String csvContent = "Name,Age,Email,RollNumber\n"
                + "John Doe,20,johndoe@example.com,1001\n"
                + "Jane Smith,22,janesmith@example.com,1002\n";

        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));
        multipartFile = mock(MultipartFile.class);
        try {
            when(multipartFile.getInputStream()).thenReturn(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            when(multipartFile.getInputStream()).thenThrow(new IOException("IO Exception"));
            Exception exception = assertThrows(Exception.class, ()-> {
                csvImportService.processCSVAndSaveAuthor(multipartFile);
            });

            assertEquals("Unable to parse the csv file", exception.getMessage());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testProcessCSVAndSaveBook_Success() throws Exception {
        String csvContent = "Name,Price,Genre,Publication, Book_Author_Id\n"
                + "Harry Potter,200.9,FICTION,SWAN,2\n"
                + "Jane Eyre,224,FICTION,SWAN,3\n";

        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));
        multipartFile = mock(MultipartFile.class);
        try {
            when(multipartFile.getInputStream()).thenReturn(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int expectedBookCount = 2;

        int savedCount = csvImportService.processCSVAndSaveBook(multipartFile);

        assertEquals(expectedBookCount, savedCount);
        verify(mockBookDao, times(1)).saveAll(anyList());

    }

    @Test
    public void testProcessCSVAndSaveBook_IOException() throws Exception {
        String csvContent = "Name,Price,Genre,Publication, Book_Author_Id\n"
                + "Harry Potter,200.9,FICTION,SWAN,2\n"
                + "Jane Eyre,224,FICTION,SWAN,3\n";

        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));
        multipartFile = mock(MultipartFile.class);
        try {
            when(multipartFile.getInputStream()).thenReturn(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            when(multipartFile.getInputStream()).thenThrow(new IOException("IO Exception"));
            Exception exception = assertThrows(Exception.class, ()-> {
                csvImportService.processCSVAndSaveAdmin(multipartFile);
            });

            assertEquals("Unable to parse the csv file", exception.getMessage());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
