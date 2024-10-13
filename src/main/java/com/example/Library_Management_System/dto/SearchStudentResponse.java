package com.example.Library_Management_System.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder

//Why another wrapper dto and not directly use the list object
public class SearchStudentResponse {

    private List<StudentResponse> studentResponseList;
}
