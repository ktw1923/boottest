package com.example.boot01.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor  //모든 필드를 파라미터로 가지는 생성자를 자동으로 생성
@NoArgsConstructor  //파라미터로 없는 기본 생성자를 자동으로 생성
public class ItemDTO {

    private Long pnum;

    private String pname;

    private int price;

    private String pdesc;

    private boolean pflag;

    @Builder.Default
    private List<MultipartFile> files= new ArrayList<>();

    @Builder.Default
    private List<String> fileNames=new ArrayList<>();


}
