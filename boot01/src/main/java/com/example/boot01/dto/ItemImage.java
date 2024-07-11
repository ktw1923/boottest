package com.example.boot01.dto;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemImage {

    private String fileName;

    private int itemnum;

    public void setItemnum(int itemnum){
        this.itemnum=itemnum;
    }


}
