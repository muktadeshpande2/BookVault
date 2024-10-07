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
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "roll_number",unique = true, nullable = false)
    private String rollNumber;

    private String gender;

    private Integer age;

    @OneToMany(mappedBy = "book_student")
    private List<Book> bookList;

    //n transactions here
    //What is the relationship type
    @OneToMany(mappedBy = "transaction_student")
    private List<Transaction> transactionList;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;
}
