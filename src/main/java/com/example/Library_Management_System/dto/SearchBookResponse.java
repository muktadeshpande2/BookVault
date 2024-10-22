package com.example.Library_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter

public class SearchBookResponse {

    private List<BookResponse> bookResponseList;


}
