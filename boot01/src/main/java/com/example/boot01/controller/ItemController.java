package com.example.boot01.controller;


import com.example.boot01.dto.ItemDTO;
import com.example.boot01.dto.PageDTO;
import com.example.boot01.dto.PageDTO2;
import com.example.boot01.service.ItemService;
import com.example.boot01.utility.FileUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bit/items")
@Log4j2
public class ItemController {

    private final FileUtility fileUtility;

    private final ItemService itemService;

    @PostMapping("/")
    public Map<String, Long> register(ItemDTO itemDTO) {


        List<MultipartFile> files=itemDTO.getFiles();
        List<String> fileNames=fileUtility.saveFiles(files);

        itemDTO.setFileNames(fileNames);

        Long pnum = itemService.register(itemDTO);

        return Map.of("final", pnum);
    }

    @GetMapping("/item/{filename}")
    public ResponseEntity<Resource> fileGet(@PathVariable String filename){
        return fileUtility.getFile(filename);
    }

    @GetMapping("/list")
    public PageDTO2<ItemDTO> list(PageDTO pageDTO) {return itemService.getList(pageDTO);}

    @GetMapping("/{pnum}")
    public ItemDTO read(@PathVariable Long pnum) {
        return itemService.get(pnum);
    }

    @PutMapping("/{pnum}")
    public Map<String, String> modify(@PathVariable(name = "pnum") Long pnum, ItemDTO itemDTO) {

        itemDTO.setPnum(pnum);

        //지워진 파일 알아보기 위해 가져옴
        ItemDTO oldItemDTO = itemService.get(pnum); //조회

        //조회한 상품으로부터 상품이미지 파일명 저장
        List<String> oldFileName = oldItemDTO.getFileNames();

        //조회

        //새롭게 업로드 해야하는 파일들
        List<MultipartFile> files = itemDTO.getFiles();

        //업로드되어 만들어진 파일명들 - 업로드폴더에 들어간 이미지들
        List<String> currentUpfiles = fileUtility.saveFiles(files);

        //수정없이 계속 유지되는 파일들
        List<String> uploadedFiles = itemDTO.getFileNames();

        if(currentUpfiles != null && currentUpfiles.size() > 0) {
            uploadedFiles.addAll(currentUpfiles);
        }

        itemService.modify(itemDTO);

        if(oldFileName != null && oldFileName.size() > 0) {

            List<String> rmFiles = oldFileName.stream()
                    .filter(fileName -> uploadedFiles.indexOf(fileName)==-1).collect(Collectors.toList());

            //수정과정에서 지워져야 할 파일들만 걸러서 리스트로 만들겠다.

            //파일경로에서 실제 파일 삭제
            fileUtility.deleteFile(rmFiles);
        }
        return Map.of("final", "success");
    }
}
