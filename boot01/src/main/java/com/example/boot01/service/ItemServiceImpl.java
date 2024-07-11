package com.example.boot01.service;

import com.example.boot01.dto.*;
import com.example.boot01.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public PageDTO2<ItemDTO> getList(PageDTO pageDTO) {


        Pageable pageable = PageRequest.of(
                pageDTO.getPage() - 1,  //페이지 시작 번호가 0부터 시작하므로
                pageDTO.getSize(),
                Sort.by("pnum").descending());

        Page<Object[]> result = itemRepository.selectList(pageable);


        List<ItemDTO> dtoList = result.get().map(arr -> {

            Item item = (Item) arr[0]; //상품
            ItemImage itemImage = (ItemImage) arr[1]; //상품이미지

            ItemDTO itemDTO = ItemDTO.builder()
                    .pnum(item.getPnum())
                    .pname(item.getPname())
                    .pdesc(item.getPdesc())
                    .price(item.getPrice())
                    .build();

            String imageStr = itemImage.getFileName();
            itemDTO.setUploadFileNames(List.of(imageStr));
            // 이미지 문자ㅓ열은 디비에 있는값이라고 생각..

            return itemDTO;
        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageDTO2.<ItemDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageDTO(pageDTO)
                .build();
    }

    @Override
    public Long register(ItemDTO itemDTO) {

        Item item = dtoToEntity(itemDTO);

        Item result = itemRepository.save(item);

        return result.getPnum();
    }
    private Item dtoToEntity(ItemDTO itemDTO){

        Item item = Item.builder()
                .pnum(itemDTO.getPnum())
                .pname(itemDTO.getPname())
                .pdesc(itemDTO.getPdesc())
                .price(itemDTO.getPrice())
                .build();

        //업로드 처리가 끝난 파일들의 이름 리스트
        List<String> uploadFileNames = itemDTO.getUploadFileNames();

        if(uploadFileNames == null){
            return item;
        }

        uploadFileNames.stream().forEach(uploadName -> {
            item.addImgString(uploadName);
        });

        return item;
    }
    private ItemDTO entityToDTO(Item item){

        ItemDTO itemDTO = ItemDTO.builder()
                .pnum(item.getPnum())
                .pname(item.getPname())
                .pdesc(item.getPdesc())
                .price(item.getPrice())
                .deFlag(item.isDeFlag())
                .build();


        List<ItemImage> imageList = item.getImgList();

        if(imageList == null || imageList.size() == 0 ){
            return itemDTO;
        }

        List<String> fileNameList = imageList.stream().map(productImage ->
                productImage.getFileName()).toList();

        itemDTO.setUploadFileNames(fileNameList);

        return itemDTO;
    }
    @Override
    public ItemDTO get(Long pnum) {

        Optional<Item> result = itemRepository.selectOne(pnum);

        Item item = result.orElseThrow();

        ItemDTO itemDTO = entityToDTO(item);

        return itemDTO;

    }

    @Override
    public void modify(ItemDTO itemDTO) {


        //step1 read  조회
        Optional<Item> result = itemRepository.findById(itemDTO.getPnum());

        Item item = result.orElseThrow();

        //change pname, pdesc, price 변경내용 반영
        item.chName(itemDTO.getPname());
        item.chDesc(itemDTO.getPdesc());
        item.chPrice(itemDTO.getPrice());

        //upload File -- clear first
        //이미지처리하기 위해 목록 비워놔야한다
        item.clearList();

        List<String> uploadFileNames = itemDTO.getUploadFileNames();

        if(uploadFileNames != null && uploadFileNames.size() > 0 ){
            uploadFileNames.stream().forEach(uploadName -> {
                item.addImgString(uploadName);
            });
        }
        itemRepository.save(item);
    }

    @Override
    public void delete(Long pnum) {
        itemRepository.updateToDelete(pnum, true);
    }


}