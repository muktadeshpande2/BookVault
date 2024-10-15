package com.example.Library_Management_System.model;

import com.example.Library_Management_System.dto.TransactionResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String transactionId;

    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;

    private Integer fine;

    @CreationTimestamp
    private Date createdOn;

    @ManyToOne
    @JoinColumn
    private Admin admin;

    @ManyToOne
    @JoinColumn
    private Book book;

    @ManyToOne
    @JoinColumn
    private Student student;

    public TransactionResponse to() {
        return TransactionResponse.builder()
                .fine(this.fine)
                .transactionId(this.transactionId)
                .transactionStatus(this.transactionStatus)
                .transactionType(this.transactionType)
                .build();
    }
}