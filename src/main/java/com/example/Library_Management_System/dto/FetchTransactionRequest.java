package com.example.Library_Management_System.dto;

import com.example.Library_Management_System.model.Transaction;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class FetchTransactionRequest {

    @NotBlank
    private String searchKey;

    @NotBlank
    private String searchValue;

    public List<TransactionResponse> createResponse(List<Transaction> transactionList) {
        List<TransactionResponse> transactionResponseList = new ArrayList<>();
        for(Transaction transaction: transactionList) {
            transactionResponseList.add(transaction.to());
        }
        return transactionResponseList;
    }
}
