package com.example.boot01.utility;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class FileUtility {

    @Value("${kr.bit.upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init(){
        File file=new File(uploadPath);

        if(file.exists()==false){
            file.mkdir();
        }
        uploadPath=file.getAbsolutePath();
    }

    public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException {

        List<String> uploadNames = new ArrayList<>();

        for (MultipartFile multipartFile : files) {
            String sName = UUID.randomUUID().toString() + "-" + multipartFile.getOriginalFilename();
            //파일명 충돌 방지

            Path sPath = Paths.get(uploadPath, sName);  //저장 경로

            try {
                Files.copy(multipartFile.getInputStream(), sPath);

                String contentType=multipartFile.getContentType();  //text/html , multipart/form-data image

                if(contentType!=null && contentType.startsWith("image")){  //이미지 파일 여부 확인

                    Path thumPath=Paths.get(uploadPath, "thum_"+sName);

                    Thumbnails.of(sPath.toFile())
                            .size(500,500)
                            .toFile(thumPath.toFile());
                }
                uploadNames.add(sName);  //저장된 파일명 리스트에 추가

            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }

        }
        return uploadNames;
    }

    public ResponseEntity<Resource> getFile(String filename){

        Resource resource = new FileSystemResource(uploadPath + File.separator + filename);

        if(!resource.exists()){
            resource = new FileSystemResource(uploadPath+ File.separator + "loopy.jpg");
        }

        HttpHeaders httpHeaders=new HttpHeaders();

        try{
            httpHeaders.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(httpHeaders).body(resource);
    }

    public void deleteFile(List<String> filename) {

        if (filename == null) {
            return;
        }
        filename.forEach(filenames -> {
            String thumFile = "thumDelete_" + filenames;

            Path thumPath = Paths.get(uploadPath, thumFile);
            Path filePath = Paths.get(uploadPath, filenames);

            try {
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(thumPath);

            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }
}
