package com.example.Library_Management_System.model;

import com.example.Library_Management_System.dto.StudentResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student implements Serializable {

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

    @OneToMany(mappedBy = "student")
    private List<Transaction> transactionList;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    @OneToOne
    @JoinColumn
    private User user;

    public StudentResponse to() {
        return StudentResponse.builder()
                .name(this.name)
                .email(this.email)
                .age(this.age)
                .gender(this.gender)
                .rollNumber(this.rollNumber)
                .createdOn(this.createdOn)
                .updatedOn(this.updatedOn)
                .build();
    }
}
