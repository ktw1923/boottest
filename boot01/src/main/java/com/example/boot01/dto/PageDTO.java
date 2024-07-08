package com.example.boot01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDTO {

    @Builder.Default
    private int page=1;


    @Builder.Default
    private int num=10;
}
