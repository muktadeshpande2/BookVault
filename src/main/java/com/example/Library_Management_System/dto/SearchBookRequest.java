package com.example.Library_Management_System.dto;

import com.example.Library_Management_System.model.Book;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SearchBookRequest {

    @NotBlank
    private String searchKey;

    @NotBlank
    private String searchValue;


    public List<BookResponse> createResponse(List<Book> bookList) {
        List<BookResponse> bookResponseList = new ArrayList<>();
        for(Book book: bookList) {
            bookResponseList.add(book.to());
        }
        return bookResponseList;
    }
}
