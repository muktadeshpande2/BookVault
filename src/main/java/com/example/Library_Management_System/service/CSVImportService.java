package com.example.Library_Management_System.service;

import com.example.Library_Management_System.model.*;
import com.example.Library_Management_System.repository.AdminDao;
import com.example.Library_Management_System.repository.AuthorDao;
import com.example.Library_Management_System.repository.BookDao;
import com.example.Library_Management_System.repository.StudentDao;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVImportService {

    @Autowired
    StudentDao studentDao;

    @Autowired
    AdminDao adminDao;

    @Autowired
    AuthorDao authorDao;

    @Autowired
    BookDao bookDao;

    public Integer processCSVAndSaveStudent(MultipartFile file) throws Exception {
        try {
            List<Student> students = parseCSVToStudents(file.getInputStream());
            studentDao.saveAll(students);
            return students.size();
        } catch (IOException e) {
            throw new Exception("Unable to parse the csv file");
        }

    }

    private List<Student> parseCSVToStudents(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)
        )) {
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader("Name", "Age", "Email", "RollNumber")
                    .build();

            CSVParser csvParser = csvFormat.parse(reader);
            List<CSVRecord> csvRecords = csvParser.getRecords();
            csvRecords.removeFirst();

            List<Student> students = new ArrayList<>();
            for (CSVRecord csvRecord : csvRecords) {
                Student student = Student.builder()
                        .name(csvRecord.get("Name"))
                        .age(Integer.parseInt(csvRecord.get("Age")))
                        .rollNumber(csvRecord.get("RollNumber"))
                        .email(csvRecord.get("Email"))
                        .build();

                students.add(student);
            }

            return students;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer processCSVAndSaveAdmin(MultipartFile file) throws Exception {
        try {
            List<Admin> admins = parseCSVToAdmins(file.getInputStream());
            adminDao.saveAll(admins);
            return admins.size();
        } catch (IOException e) {
            throw new Exception("Unable to parse the csv file");
        }
    }

    private List<Admin> parseCSVToAdmins(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)
        )) {
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader("Name", "Email")
                    .build();

            CSVParser csvParser = csvFormat.parse(reader);
            List<CSVRecord> csvRecords = csvParser.getRecords();
            csvRecords.removeFirst();

            List<Admin> admins = new ArrayList<>();
            for (CSVRecord csvRecord : csvRecords) {
                Admin admin = Admin.builder()
                        .name(csvRecord.get("Name"))
                        .email(csvRecord.get("Email"))
                        .build();

                admins.add(admin);
            }

            return admins;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer processCSVAndSaveAuthor(MultipartFile file) throws Exception {
        try {
            List<Author> authors = parseCSVToAuthors(file.getInputStream());
            authorDao.saveAll(authors);
            return authors.size();
        } catch (IOException e) {
            throw new Exception("Unable to parse the csv file");
        }

    }

    private List<Author> parseCSVToAuthors(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)
        )) {
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader("Name", "Email")
                    .build();

            CSVParser csvParser = csvFormat.parse(reader);
            List<CSVRecord> csvRecords = csvParser.getRecords();
            csvRecords.removeFirst();

            List<Author> authors = new ArrayList<>();
            for (CSVRecord csvRecord : csvRecords) {
                Author author = Author.builder()
                        .name(csvRecord.get("Name"))
                        .email(csvRecord.get("Email"))
                        .build();

                authors.add(author);
            }

            return authors;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer processCSVAndSaveBook(MultipartFile file) throws Exception {
        try {
            List<Book> books = parseCSVToBooks(file.getInputStream());
            bookDao.saveAll(books);
            return books.size();
        } catch (IOException e) {
            throw new Exception("Unable to parse the csv file");
        }

    }

    private List<Book> parseCSVToBooks(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)
        )) {
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader("Name", "Price", "Genre", "Publication", "Book_Author_Id")
                    .build();

            CSVParser csvParser = csvFormat.parse(reader);
            List<CSVRecord> csvRecords = csvParser.getRecords();
            csvRecords.removeFirst();

            List<Book> books = new ArrayList<>();
            for (CSVRecord csvRecord : csvRecords) {
                Book book = Book.builder()
                        .bookName(csvRecord.get("Name"))
                        .bookPrice(Double.parseDouble(csvRecord.get("Price")))
                        .genre(Genre.valueOf(csvRecord.get("Genre")))
                        .publication(Publication.valueOf(csvRecord.get("Publication")))
                        .build();

                books.add(book);
            }

            return books;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
