package com.example.boot01.dto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bit_item")
@Getter
@ToString(exclude = "imgList")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pnum;

    private String pname;

    private int price;

    private String pdesc;

    private boolean deFlag;


    public void chDel(boolean deFlag) {
        this.deFlag = deFlag;
    }


    @ElementCollection
    @Builder.Default
    private List<ItemImage> imgList = new ArrayList<>();

    public void chPrice(int price) {
        this.price = price;
    }

    public void chDesc(String desc){
        this.pdesc = desc;
    }

    public void chName(String name){
        this.pname = name;
    }

    public void addImage(ItemImage image) {

        image.setItemnum(this.imgList.size());
        imgList.add(image);
    }

    public void addImgString(String fileName){

        ItemImage itemImage = ItemImage.builder()
                .fileName(fileName)
                .build();
        addImage(itemImage);

    }

    public void clearList() {
        this.imgList.clear();
    }
}
