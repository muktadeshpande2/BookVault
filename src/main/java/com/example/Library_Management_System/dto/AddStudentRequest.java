package com.example.Library_Management_System.dto;

import com.example.Library_Management_System.model.Student;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddStudentRequest {

    private String name;

    private String email;

    private String rollNumber;

    private String gender;

    private Integer age;


    public Student to() {
        return Student.builder()
                .name(this.name)
                .email(this.email)
                .gender(this.gender)
                .rollNumber(this.rollNumber)
                .age(this.age)
                .build();
    }


}
