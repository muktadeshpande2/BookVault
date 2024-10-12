package com.example.Library_Management_System.dto;

import com.example.Library_Management_System.model.Genre;
import com.example.Library_Management_System.model.Publication;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class BookResponse {

    private String bookName;

    private Double bookPrice;

    private Genre genre;

    private Publication publication;

    private Date createdOn;

    private Date updatedOn;


}
