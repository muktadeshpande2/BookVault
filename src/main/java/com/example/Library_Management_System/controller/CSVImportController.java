package com.example.Library_Management_System.controller;

import com.example.Library_Management_System.service.CSVImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/csv")
public class CSVImportController {

    @Autowired
    CSVImportService csvImportService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCSV(@RequestParam("file")MultipartFile file, @RequestParam("entity") String key) {
        Integer records = null;

        switch(key) {
            case "Student": {
                try {
                    records = csvImportService.processCSVAndSaveStudent(file);
                    return ResponseEntity
                            .status(HttpStatus.ACCEPTED)
                            .body("CSV file parsed successfully and uploaded " + records + " records");
                } catch (Exception e) {
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(e.getMessage());
                }
            }

            case "Admin": {
                try {
                    records = csvImportService.processCSVAndSaveAdmin(file);
                    return ResponseEntity
                            .status(HttpStatus.ACCEPTED)
                            .body("CSV file parsed successfully and uploaded " + records + " records");
                } catch (Exception e) {
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(e.getMessage());
                }
            }

            case "Author": {
                try {
                    records = csvImportService.processCSVAndSaveAuthor(file);
                    return ResponseEntity
                            .status(HttpStatus.ACCEPTED)
                            .body("CSV file parsed successfully and uploaded " + records + " records");
                } catch (Exception e) {
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(e.getMessage());
                }
            }

            case "Book": {
                try {
                    records = csvImportService.processCSVAndSaveBook(file);
                    return ResponseEntity
                            .status(HttpStatus.ACCEPTED)
                            .body("CSV file parsed successfully and uploaded " + records + " records");
                } catch (Exception e) {
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(e.getMessage());
                }
            }
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Invalid entity data passed");
    }
}
