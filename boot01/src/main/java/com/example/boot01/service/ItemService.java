package com.example.boot01.service;

import com.example.boot01.dto.ItemDTO;
import com.example.boot01.dto.PageDTO;
import com.example.boot01.dto.PageDTO2;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ItemService {

    //상품 목록 조회
    PageDTO2<ItemDTO> getList(PageDTO pageDTO);

    //상품 등록
    Long register(ItemDTO itemDTO);

    //상품 상세 조회
    ItemDTO get(Long pnum);

    //상품 수정
    void modify(ItemDTO itemDTO);

    //상품 삭제

}
