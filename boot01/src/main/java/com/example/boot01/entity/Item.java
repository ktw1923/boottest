package com.example.boot01.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@ToString(exclude = "imgList")
@Builder
@Table(name="bit_item")
@NoArgsConstructor
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pnum;

    private String pname;

    private int price;

    private String pdesc;

    private boolean pflag;


    @ElementCollection
    @Builder.Default
    private List<ItemImage> imgList=new ArrayList<>();

    public void addImage(ItemImage itemImage){
        itemImage.setItemnum(this.imgList.size());
        imgList.add(itemImage);
    }

    public void addImageString(String filename) {
        ItemImage itemImage = ItemImage.builder()
                .fileName(filename)
                .build();

        addImage(itemImage);
    }

    public void clearImage(){
        this.imgList.clear();
    }






}
