package com.example.Library_Management_System.model;

import com.example.Library_Management_System.dto.BookResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String bookName;

    private Double bookPrice;

    @Enumerated(value=EnumType.STRING)
    private Genre genre;

    @Enumerated(value=EnumType.STRING)
    private Publication publication;

    @ManyToOne
    @JoinColumn
    private Author book_author;

    @ManyToOne
    @JoinColumn
    private Student book_student;

    @OneToMany(mappedBy = "book")
    private List<Transaction> transactionList;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;


    public BookResponse to() {
        return BookResponse.builder()
                .bookName(this.bookName)
                .bookPrice(this.bookPrice)
                .genre(this.genre)
                .publication(this.publication)
                .createdOn(this.createdOn)
                .updatedOn(this.updatedOn)
                .build();
    }
}

