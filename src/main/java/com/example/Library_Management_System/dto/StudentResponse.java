package com.example.Library_Management_System.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class StudentResponse {

    private String name;

    private String email;

    private String rollNumber;

    private String gender;

    private Integer age;

    private Date createdOn;

    private Date updatedOn;
}
