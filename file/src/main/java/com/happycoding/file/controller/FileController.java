package com.happycoding.file.controller;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
public class FileController {

    @RequestMapping("upload")
    public void upload(MultipartFile file, HttpServletRequest request) throws IOException {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getBytes().length);
        System.out.println(System.getProperty("user.home"));
        String path = System.getProperty("user.home") + "/temp/temp/";
        File outFile = new File(path);
        if(!outFile.exists()){
            outFile.mkdirs();
        }
        outFile = new File(path + file.getOriginalFilename());
        FileCopyUtils.copy(file.getBytes(), outFile);


    }
}
