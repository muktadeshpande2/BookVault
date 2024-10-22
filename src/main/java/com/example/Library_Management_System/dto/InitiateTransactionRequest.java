package com.example.Library_Management_System.dto;

import com.example.Library_Management_System.model.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class InitiateTransactionRequest {


    @NotNull
    private String studentRollNumber;

    @NotNull
    private Integer adminId;

    @NotNull
    private Integer bookId;

    @NotNull
    private TransactionType transactionType;


}
