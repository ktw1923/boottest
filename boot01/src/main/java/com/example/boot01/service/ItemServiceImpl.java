package com.example.boot01.service;

import com.example.boot01.dto.ItemDTO;
import com.example.boot01.dto.PageDTO;
import com.example.boot01.dto.PageDTO2;
import com.example.boot01.entity.Item;
import com.example.boot01.entity.ItemImage;
import com.example.boot01.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;

    @Override
    public PageDTO2<ItemDTO> getList(PageDTO pageDTO) {

        Pageable pageable = PageRequest.of(
                pageDTO.getPage()-1,
                pageDTO.getNum(),
                Sort.by("pnum").descending());

        Page<Object[]> result = itemRepository.selectList(pageable); //item, imgList

        List<ItemDTO> dtoList = result.get().map(arr -> {
            Item item = (Item) arr[0]; //상품
            ItemImage itemImage = (ItemImage) arr[1]; //상품 이미지

            ItemDTO itemDTO=ItemDTO.builder()
                    .pnum(item.getPnum())
                    .pname(item.getPname())
                    .pdesc(item.getPdesc())
                    .price(item.getPrice())
                    .build();

            String imageString = itemImage.getFileName();
            itemDTO.setFileNames(List.of(imageString)); //이미지 문자열은 디비에 삽입

            return itemDTO;
        }).collect(toList()); //리스트 변환

        long totalCount = result.getTotalElements();

        return PageDTO2.<ItemDTO>withAll()
                .dtoList(dtoList) //item, itemImage
                .total(totalCount)
                .pageDTO(pageDTO)
                .build();

    }

    private Item dtoToEntity(ItemDTO itemDTO) {
        Item item = Item.builder()
                .pnum(itemDTO.getPnum())
                .pname(itemDTO.getPname())
                .pdesc(itemDTO.getPdesc())
                .price(itemDTO.getPrice())
                .pflag(itemDTO.isPflag())
                .build();

        //업로드 끝난 파일명들의 리스트
        List<String> upFileNames = itemDTO.getFileNames();

        upFileNames.stream().forEach(uploadNames -> {
            item.addImageString(uploadNames);
        });

        return item;
    }

    @Override
    public Long register(ItemDTO itemDTO) {
        Item item = dtoToEntity(itemDTO);
        itemRepository.save(item); // Save the item to the repository
        return item.getPnum(); // Return the generated ID
    }

    private ItemDTO entityToDTO(Item item) {

        ItemDTO itemDTO = ItemDTO.builder()
                .pnum(item.getPnum())
                .pname(item.getPname())
                .pdesc(item.getPdesc())
                .price(item.getPrice())
                .pflag(item.isPflag())
                .build();

        List<ItemImage> itemImages = item.getImgList();

        List<String> fileName = itemImages.stream().map(itemImage ->
            itemImage.getFileName()).toList();

            itemDTO.setFileNames(fileName);

            return itemDTO;
    }

    @Override
    public ItemDTO get(Long pnum) {

        Optional<Item> optional = itemRepository.selectOne(pnum);

        Item item = optional.orElseThrow();

        ItemDTO itemDTO = entityToDTO(item); //entity(Item) -> dto(ItemDTO)

        return itemDTO;
    }

    @Override
    public void modify(ItemDTO itemDTO) {
        //상품 조회 후
        Optional<Item> optional=itemRepository.findById(itemDTO.getPnum());

        Item item = optional.orElseThrow();

        item.setPname(itemDTO.getPname());
        item.setPdesc(itemDTO.getPdesc());
        item.setPrice(itemDTO.getPrice());

        //이미지 처리하기 위해 비워놔야 한다
        item.clearImage();

        List<String> upFileNames = itemDTO.getFileNames();

        if(upFileNames!=null && upFileNames.size()>0) {
            upFileNames.stream().forEach(uploadName -> {
                item.addImageString(uploadName);
            });
        }

        itemRepository.save(item); // Save the item to the repository

    }
}