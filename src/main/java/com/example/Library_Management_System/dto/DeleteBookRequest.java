package com.example.Library_Management_System.dto;

import com.example.Library_Management_System.model.Book;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DeleteBookRequest {

    private Integer bookId;

    public Book to() {
        return Book.builder()
                .id(this.bookId)
                .build();
    }
}
