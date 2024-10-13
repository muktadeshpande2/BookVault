package com.example.Library_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter

//Why another wrapper dto and not directly use the list object
public class SearchBookResponse {

    private List<BookResponse> bookResponseList;


}
