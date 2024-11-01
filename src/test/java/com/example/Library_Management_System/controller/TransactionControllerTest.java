package com.example.Library_Management_System.controller;

import com.example.Library_Management_System.dto.FetchTransactionRequest;
import com.example.Library_Management_System.dto.FetchTransactionResponse;
import com.example.Library_Management_System.dto.InitiateTransactionRequest;
import com.example.Library_Management_System.model.Transaction;
import com.example.Library_Management_System.model.TransactionStatus;
import com.example.Library_Management_System.model.TransactionType;
import com.example.Library_Management_System.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testInitiateTxn_Success() throws Exception {
        InitiateTransactionRequest initiateTransactionRequest = new InitiateTransactionRequest();
        initiateTransactionRequest.setBookId(1);
        initiateTransactionRequest.setAdminId(2);
        initiateTransactionRequest.setStudentRollNumber("18UCS070");
        initiateTransactionRequest.setTransactionType(TransactionType.valueOf("ISSUE"));

        when(transactionService.initiateTxn(any())).thenReturn("transactionId");

        // POST API with valid JSON body
        mockMvc.perform(post("/transaction/initiate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(initiateTransactionRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().string("TransactionId: transactionId"));
    }

    @Test
    public void testInitiateTxn_Exception() throws Exception {
        InitiateTransactionRequest initiateTransactionRequest = new InitiateTransactionRequest();
        initiateTransactionRequest.setBookId(1);
        initiateTransactionRequest.setAdminId(2);
        initiateTransactionRequest.setStudentRollNumber("18UCS070");
        initiateTransactionRequest.setTransactionType(TransactionType.valueOf("ISSUE"));


        when(transactionService.initiateTxn(any())).thenThrow(new Exception("Issue limit reached for this student"));

        // POST API with valid JSON body
        mockMvc.perform(post("/transaction/initiate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(initiateTransactionRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Issue limit reached for this student"));
    }

    @Test
    public void testFetchTxn_Success() throws Exception {
        FetchTransactionRequest transactionRequest = new FetchTransactionRequest();
        transactionRequest.setSearchKey("TransactionStatus");
        transactionRequest.setSearchValue("SUCCESS");

        Transaction transaction = Transaction.builder()
                .id(1)
                .transactionId("transactionId")
                .transactionType(TransactionType.valueOf("RETURN"))
                .transactionStatus(TransactionStatus.valueOf("SUCCESS"))
                .fine(100)
                .build();

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        when(transactionService.searchTransaction(transactionRequest.getSearchKey(), transactionRequest.getSearchValue())).thenReturn(transactionList);

        // GET API with valid JSON body
        mockMvc.perform(get("/transaction/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new FetchTransactionResponse(new ArrayList<>(Arrays.asList(transaction.to()))))));
    }


    @Test
    public void testFetchTxn_Exception() throws Exception {
        FetchTransactionRequest transactionRequest = new FetchTransactionRequest();
        transactionRequest.setSearchKey("TransactionStatus");
        transactionRequest.setSearchValue("SUCCESS");

        when(transactionService.searchTransaction(transactionRequest.getSearchKey(), transactionRequest.getSearchValue()))
                .thenThrow(new Exception("Invalid Search Key: " + transactionRequest.getSearchKey()));

        // GET API with valid JSON body
        mockMvc.perform(get("/transaction/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionResponseList").isEmpty());

    }

}
