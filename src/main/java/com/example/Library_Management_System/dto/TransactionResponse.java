package com.example.Library_Management_System.dto;

import com.example.Library_Management_System.model.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class TransactionResponse {

    private String transactionId;

    private TransactionType transactionType;

    private TransactionStatus transactionStatus;

    private Integer fine;

}
