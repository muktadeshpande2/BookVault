package com.example.Library_Management_System.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    //n books here
    //What is the relationship type
    @OneToMany(mappedBy = "book_author")
    private List<Book> bookList;

    @CreationTimestamp
    private Date createdOn;

}
