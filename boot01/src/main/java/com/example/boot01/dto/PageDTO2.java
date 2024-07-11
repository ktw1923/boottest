package com.example.boot01.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Data
public class PageDTO2<E> {

    private List<E> dtoList;

    private List<Integer> pageNumList;

    private PageDTO pageDTO;

    private boolean prev, next;

    private int totalCount, prevPage, nextPage, totalPage, current;

    @Builder(builderMethodName = "withAll")
    public PageDTO2(List<E> dtoList, PageDTO pageDTO, long totalCount) {

        this.dtoList = dtoList;
        this.pageDTO = pageDTO;
        this.totalCount = (int)totalCount;

        int end =   (int)(Math.ceil( pageDTO.getPage() / 10.0 )) *  10;

        int start = end - 9;

        int last =  (int)(Math.ceil((totalCount/(double)pageDTO.getSize())));

        end =  end > last ? last: end;

        this.prev = start > 1;


        this.next =  totalCount > end * pageDTO.getSize();

        this.pageNumList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());

        if(prev) {
            this.prevPage = start -1;
        }

        if(next) {
            this.nextPage = end + 1;
        }

        this.totalPage = this.pageNumList.size();

        this.current = pageDTO.getPage();

    }
}
