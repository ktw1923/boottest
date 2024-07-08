package com.example.boot01.dto;

import lombok.Builder;
import lombok.Data;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageDTO2<E> {

    private PageDTO pageDTO;

    private List<Integer> pageNumList;

    private List<E> dtoList;

    private boolean prev, next;

    private int total, prevPage, nextPage, totalPage, currentPage;

    @Builder(builderMethodName = "withAll")
    public PageDTO2(List<E> dtoList, PageDTO pageDTO, long total){
        this.dtoList=dtoList;
        this.pageDTO=pageDTO;
        this.total=(int)total;

        int end=(int)(Math.ceil(pageDTO.getPage()/10.0)) *10;

        int start=end-9;

        int last=(int)(Math.ceil((total/(double)pageDTO.getNum())));


        end = end > last ? last:end;

        this.prev=start > 1;

        this.next=total > end * pageDTO.getNum();

        this.pageNumList= IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());

        this.prevPage = prev ? start - 1:0;
        this.nextPage= next ? end + 1 : 0;

        this.totalPage=this.pageNumList.size();
        this.currentPage=pageDTO.getPage();

    }

}
