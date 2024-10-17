package com.example.Library_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter

//Why another wrapper dto and not directly use the list object - To maintain a consistent response format across the codebase.
//ALso, there is room for addition of further data
public class SearchBookResponse {

    private List<BookResponse> bookResponseList;


}
