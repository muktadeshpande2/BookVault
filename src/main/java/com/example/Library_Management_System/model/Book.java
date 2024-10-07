package com.example.Library_Management_System.model;

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

    //For better readability and if the enum would get edited in the future
    //EnumType.ORDINAL is used for efficiency(throughput) and saving space
    @Enumerated(value=EnumType.STRING)
    private Publication publication;

    //Foreign Key as it refers to the primary key of Author table
    @ManyToOne
    @JoinColumn
    private Author book_author;

    //Foreign key as it refers to the primary key of the Student table
    @ManyToOne
    @JoinColumn
    private Student book_student;

    //n transactions here
    //What is the relationship type
    @OneToMany(mappedBy = "transaction_book")
    private List<Transaction> transactionList;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;
}

//@JoinColumn specifies the foreign key column
