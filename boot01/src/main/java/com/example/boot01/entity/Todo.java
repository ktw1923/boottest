package com.example.boot01.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todonum;

    @Setter
    @Column(length = 300, nullable = false)
    private String title;

    private String content;

    private boolean finale;

    private LocalDate dueDate;


}
