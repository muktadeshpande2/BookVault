package com.example.Library_Management_System.dto;

import com.example.Library_Management_System.model.Student;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SearchStudentRequest {

    @NotBlank
    private String searchKey;

    @NotBlank
    private String searchValue;


    public List<StudentResponse> createResponse(List<Student> studentList) {
        List<StudentResponse> studentResponseList = new ArrayList<>();
        for(Student student: studentList) {
            studentResponseList.add(student.to());
        }
        return studentResponseList;
    }
}
