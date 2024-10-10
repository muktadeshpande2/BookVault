package com.example.Library_Management_System.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SearchBookRequest {

    @NotBlank
    private String searchKey;

    @NotBlank
    private String searchValue;


}
