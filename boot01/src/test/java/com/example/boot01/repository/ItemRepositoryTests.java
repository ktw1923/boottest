package com.example.boot01.repository;


import com.example.boot01.dto.PageDTO;
import com.example.boot01.entity.Item;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ItemRepositoryTests {

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void testAdd(){
        for(int i=0;i<10;i++){
            Item item= Item.builder()
                    .pname("Test item")
                    .price(200)
                    .pdesc("Test desc")
                    .build();

            item.addImageString("img1.jpg");
            item.addImageString("img2.jpg");

            itemRepository.save(item);

        }
    }

    @Test
    public void testItemList(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("pnum").descending());

        Page<Object[]> page=itemRepository.selectList(pageable);

        page.getContent().forEach(arr ->
                           log.info(Arrays.toString(arr)));
    }

    @Test
    public void testRead(){
        Long pnum=1L;

        Optional<Item> optional=itemRepository.selectOne(pnum);

        Item item=optional.orElseThrow();

        log.info(item);
        log.info(item.getImgList());
    }

    @Commit
    @Transactional
    @Test
    public void testUpdate(){
        Long pnum=2L;

       itemRepository.updateToDelete(pnum, true);
    }

    @Test
    public void testUpdate2(){
       Item item=itemRepository.selectOne(1L).get();
       item.setPrice(2000);
       item.clearImage();
       item.addImageString(UUID.randomUUID()+"_"+"itemImage1.jpg");
       item.addImageString(UUID.randomUUID()+"_"+"itemImage2.jpg");

       itemRepository.save(item);

    }

}
