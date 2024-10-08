package com.example.Library_Management_System.dto;

import com.example.Library_Management_System.model.Author;
import com.example.Library_Management_System.model.Book;
import com.example.Library_Management_System.model.Genre;
import com.example.Library_Management_System.model.Publication;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    //A book can be meant not for sale (only for distribution)
    private Double price;

    @NotNull
    private Genre genre;

    @NotNull
    private Publication publication;

    @NotBlank
    private String authorName;

    @NotBlank
    private String authorEmail;

    public Book to() {

        Author author = Author.builder()
                .name(this.authorName)
                .email(this.authorEmail)
                .build();

        return Book
                .builder()
                .bookName(this.name)
                .bookPrice(this.price)
                .genre(this.genre)
                .publication(this.publication)
                .book_author(author)
                .build();
    }
}
