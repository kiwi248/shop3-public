package com.shop3.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Log
public class FileService {

    public String uploadFile(String uploadPath, String originalName, byte[] fileBytes) throws IOException {
        String ext = "";
        int dot = originalName.lastIndexOf('.');
        if (dot != -1) ext = originalName.substring(dot); // ".jpg" 포함

        String savedName = UUID.randomUUID().toString() + ext; // <-- 확장자 붙임
        Path savePath = Paths.get(uploadPath, savedName);
        Files.createDirectories(savePath.getParent());
        Files.write(savePath, fileBytes);
        return savedName; // ex) 9201bb26-...bc5.jpg
    }


    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);
        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}