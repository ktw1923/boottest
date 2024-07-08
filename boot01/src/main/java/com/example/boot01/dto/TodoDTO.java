package com.example.boot01.dto;


import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoDTO {
    //Data Transfer Object
    private Long todonum;

    private String title;

    private String content;

    private boolean finale;

    private LocalDate dueDate;

}
