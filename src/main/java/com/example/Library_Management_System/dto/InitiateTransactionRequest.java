package com.example.Library_Management_System.dto;

import com.example.Library_Management_System.model.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class InitiateTransactionRequest {

    //Student can tell verbally
    @NotNull
    private String studentRollNumber;

    //Admin knows his/her id
    @NotNull
    private Integer adminId;

    //Book id is printed on the book
    @NotNull
    private Integer bookId;

    @NotNull
    private TransactionType transactionType;


}
