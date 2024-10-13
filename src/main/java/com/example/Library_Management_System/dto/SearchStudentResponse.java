package com.example.Library_Management_System.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class SearchStudentResponse {

    private List<StudentResponse> studentResponseList;
}
