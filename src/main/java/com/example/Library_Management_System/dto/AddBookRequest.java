package com.example.Library_Management_System.dto;

import com.example.Library_Management_System.model.Book;
import com.example.Library_Management_System.model.Genre;
import com.example.Library_Management_System.model.Publication;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddBookRequest {

    @NotBlank
    private String name;

    @NotBlank
    private Double price;


    private Genre genre;

   
    private Publication publication;

    public Book to() {
        return Book
                .builder()
                .bookName(this.name)
                .bookPrice(this.price)
                .genre(this.genre)
                .publication(this.publication)
                .build();
    }
}
