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
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    private String name;

    @Column(unique = true, nullable = false)
    @Getter
    private String email;

    @OneToMany(mappedBy = "book_author")
    private List<Book> bookList;

    @CreationTimestamp
    private Date createdOn;

}
