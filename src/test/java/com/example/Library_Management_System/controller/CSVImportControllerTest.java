package com.example.Library_Management_System.controller;

import com.example.Library_Management_System.service.CSVImportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CSVImportControllerTest {

    @Mock
    private CSVImportService csvImportService;

    @InjectMocks
    private CSVImportController csvImportController;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadCSVStudent_Success() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "test data".getBytes());
        when(csvImportService.processCSVAndSaveStudent(file)).thenReturn(5);

        ResponseEntity<String> response = csvImportController.uploadCSV(file, "Student");

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("CSV file parsed successfully and uploaded 5 records", response.getBody());
    }


    @Test
    void testUploadCSVStudent_Exception() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "test data".getBytes());
        when(csvImportService.processCSVAndSaveStudent(file)).thenThrow(new Exception("Unable to parse the csv file"));

        ResponseEntity<String> response = csvImportController.uploadCSV(file, "Student");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unable to parse the csv file", response.getBody());
    }

    @Test
    void testUploadCSVAdmin_Success() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "test data".getBytes());
        when(csvImportService.processCSVAndSaveAdmin(file)).thenReturn(5);

        ResponseEntity<String> response = csvImportController.uploadCSV(file, "Admin");

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("CSV file parsed successfully and uploaded 5 records", response.getBody());
    }


    @Test
    void testUploadCSVAdmin_Exception() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "test data".getBytes());
        when(csvImportService.processCSVAndSaveAdmin(file)).thenThrow(new Exception("Unable to parse the csv file"));

        ResponseEntity<String> response = csvImportController.uploadCSV(file, "Admin");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unable to parse the csv file", response.getBody());
    }

    @Test
    void testUploadCSVAuthor_Success() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "test data".getBytes());
        when(csvImportService.processCSVAndSaveAuthor(file)).thenReturn(5);

        ResponseEntity<String> response = csvImportController.uploadCSV(file, "Author");

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("CSV file parsed successfully and uploaded 5 records", response.getBody());
    }


    @Test
    void testUploadCSVAuthor_Exception() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "test data".getBytes());
        when(csvImportService.processCSVAndSaveAuthor(file)).thenThrow(new Exception("Unable to parse the csv file"));

        ResponseEntity<String> response = csvImportController.uploadCSV(file, "Author");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unable to parse the csv file", response.getBody());
    }

    @Test
    void testUploadCSVBook_Success() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "test data".getBytes());
        when(csvImportService.processCSVAndSaveBook(file)).thenReturn(5);

        ResponseEntity<String> response = csvImportController.uploadCSV(file, "Book");

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("CSV file parsed successfully and uploaded 5 records", response.getBody());
    }


    @Test
    void testUploadCSVBook_Exception() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "test data".getBytes());
        when(csvImportService.processCSVAndSaveBook(file)).thenThrow(new Exception("Unable to parse the csv file"));

        ResponseEntity<String> response = csvImportController.uploadCSV(file, "Book");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unable to parse the csv file", response.getBody());
    }

    @Test
    void testUploadCSV_Exception() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "exception data".getBytes());

        ResponseEntity<String> response = csvImportController.uploadCSV(file, "InvalidKey");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Invalid entity data passed", response.getBody());
    }
}

