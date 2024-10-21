package com.example.Library_Management_System.controller;

import com.example.Library_Management_System.dto.*;
import com.example.Library_Management_System.model.Transaction;
import com.example.Library_Management_System.service.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/initiate")
    public ResponseEntity<String> initiateTxn(@RequestBody @Valid InitiateTransactionRequest initiateTransactionRequest) {
        try {
            String transactionId = transactionService.initiateTxn(initiateTransactionRequest);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("TransactionId: " + transactionId);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

    }

    //Fetch txn on the basis of
    @GetMapping("/fetch")
    public FetchTransactionResponse fetchTxn(@RequestBody @Valid FetchTransactionRequest fetchTransactionRequest) {
        try {
            List<Transaction> transactionList = transactionService.searchTransaction(fetchTransactionRequest.getSearchKey(), fetchTransactionRequest.getSearchValue());
            List<TransactionResponse> transactionResponseList = fetchTransactionRequest.createResponse(transactionList);
            return new FetchTransactionResponse(transactionResponseList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new FetchTransactionResponse();
        }
    }

}




//Kind of transaction - issue and return

//what all is needed for a transaction
//student, book, txn_type, admin