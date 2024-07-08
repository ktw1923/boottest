package com.example.boot01.service;

import com.example.boot01.dto.ItemDTO;
import com.example.boot01.dto.PageDTO;
import com.example.boot01.dto.PageDTO2;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ItemServiceTests {

    @Autowired
    ItemService itemService;

    @Test
    public void testList() {
        PageDTO pageDTO = PageDTO.builder().build();

        PageDTO2<ItemDTO> result = itemService.getList(pageDTO);

        result.getDtoList().forEach(dto -> {
            log.info(dto);
        });
    }
}