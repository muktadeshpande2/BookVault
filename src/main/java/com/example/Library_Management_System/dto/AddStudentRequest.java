package com.example.Library_Management_System.dto;

import com.example.Library_Management_System.model.Student;
import com.example.Library_Management_System.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddStudentRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String rollNumber;

    @NotBlank
    private String gender;

    @NotNull
    private Integer age;

    @NotBlank
    private String username;

    @NotBlank
    private String password;


    public Student to() {
        return Student.builder()
                .name(this.name)
                .email(this.email)
                .gender(this.gender)
                .rollNumber(this.rollNumber)
                .age(this.age)
                .user(User.builder()
                        .username(this.username)
                        .password(this.password)
                        .build())
                .build();
    }


}
