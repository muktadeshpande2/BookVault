package com.example.Library_Management_System.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;


/*
@Target({ElementType.TYPE}) - Define where in the code the annotations may appear
@Retention(RetentionPolicy.RUNTIME) - Defines how long will the annotations will be retained in the entire compilation-execution process
what are these?
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;


    //n transactions here
    //What is the relationship type
    @OneToMany(mappedBy = "transaction_admin")
    private List<Transaction> transactionList;

    @CreationTimestamp
    private Date createdOn;
}
