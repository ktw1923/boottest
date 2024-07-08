package com.example.boot01.entity;


import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
public class ItemImage {

    private String fileName;

    private int itemnum;

    public void setItemnum(int itemnum){
        this.itemnum=itemnum;
    }

}
