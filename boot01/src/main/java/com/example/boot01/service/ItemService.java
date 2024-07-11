package com.example.boot01.service;

import com.example.boot01.dto.PageDTO;
import com.example.boot01.dto.PageDTO2;
import com.example.boot01.dto.ItemDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ItemService {

    PageDTO2<ItemDTO> getList(PageDTO pageDTO);


    Long register(ItemDTO itemDTO);

    ItemDTO get(Long pnum);

    void modify(ItemDTO itemDTO);

    void delete(Long pnum);


}