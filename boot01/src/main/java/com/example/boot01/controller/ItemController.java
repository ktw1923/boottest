package com.example.boot01.controller;


import com.example.boot01.dto.ItemDTO;
import com.example.boot01.dto.PageDTO;
import com.example.boot01.dto.PageDTO2;
import com.example.boot01.service.ItemService;
import com.example.boot01.util.FileUtility;
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
@Log4j2
@RequestMapping("/bit/items")
@CrossOrigin(origins = "*")
public class ItemController {

    private final FileUtility fileUtility;

    private final ItemService itemService;


    @PostMapping("/")
    public Map<String, Long> register(ItemDTO itemDTO) {


        List<MultipartFile> files=itemDTO.getFiles();
        List<String> fileNames=fileUtility.saveFiles(files);

        itemDTO.setUploadFileNames(fileNames);

        Long pnum=itemService.register(itemDTO);

        return Map.of("final",pnum);

    }

    @GetMapping("/item/{filename}")
    public ResponseEntity<Resource> fileGet(@PathVariable String filename){
        return fileUtility.getFile(filename);
    }


    @GetMapping("/list")
    public PageDTO2<ItemDTO> list (PageDTO pageDTO){
        return itemService.getList(pageDTO);
    }

    @GetMapping("/{pnum}")
    public ItemDTO read(@PathVariable(name="pnum") Long pnum){
        return itemService.get(pnum);
    }

    @PutMapping("/{pnum}")
    public Map<String, String> modify(@PathVariable(name="pnum") Long pnum, ItemDTO itemDTO) {

        itemDTO.setPnum(pnum);

        //원래 상품을 가져옴 -> 어떤파일들이 지워졌는지 알아보기 위해
        ItemDTO oldProductDTO = itemService.get(pnum);

        //기존의 파일들 (데이터베이스에 존재하는 파일들 - 수정 과정에서 삭제되었을 수 있음)
        //기존의 파일데이터를 알아와야함

        List<String> oldFileNames = oldProductDTO.getUploadFileNames();

        //새로 업로드 해야 하는 파일들
        List<MultipartFile> files = itemDTO.getFiles();

        //새로 업로드되어서 만들어진 파일 이름들
        List<String> currentUploadFile = fileUtility.saveFiles(files);


        List<String> uploadedFileNames = itemDTO.getUploadFileNames();

        //유지되는 파일들  + 새로 업로드된 파일 이름들이 저장해야 하는 파일 목록이 됨
        if(currentUploadFile != null && currentUploadFile.size() > 0) {

            uploadedFileNames.addAll(currentUploadFile);

        }
        //수정 작업
        itemService.modify(itemDTO);

        //ex) abc-> abd로 바뀜 ab까지는 처리
        //c는 지워야함
        //
        if(oldFileNames != null && oldFileNames.size() > 0){

            //지워야 하는 파일 목록 찾기
            //예전 파일들 중에서 지워져야 하는 파일이름들
            List<String> removeFiles =  oldFileNames
                    .stream()
                    .filter(fileName -> uploadedFileNames.indexOf(fileName) == -1).collect(Collectors.toList());
            // 이 파일은 없다는 뜻 -1 걔네만 골라서 모으겠다 -> 수정하는 과정에서
            //                                         삭제되어야할 파일들

            //실제 파일 삭제
            fileUtility.deleteFiles(removeFiles);
        }
        return Map.of("final", "success");
    }

    @DeleteMapping("/{pnum}")
    public Map<String, String> delete(@PathVariable("pnum") Long pnum){

        List<String> file=itemService.get(pnum).getUploadFileNames();

        itemService.delete(pnum);

        fileUtility.deleteFiles(file);

        return Map.of("final", "success");
    }

}