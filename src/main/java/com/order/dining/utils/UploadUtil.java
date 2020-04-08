package com.order.dining.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author: baojx
 * @Date: 2020/04/08 21:25
 * @Desc:
 */
public class UploadUtil {
    public static String uploadImage(MultipartFile imageFile) {
        String newImageName = "";
        if (!imageFile.isEmpty()) {    //(1)
            String realPath = "G:\\JavaSource\\dining\\src\\main\\resources\\static\\image\\upload\\";  //(2)
            String originalName = imageFile.getOriginalFilename();  //(3)
            String uuidName = UUID.randomUUID().toString();  //(4)
            newImageName = uuidName + originalName.substring(originalName.lastIndexOf("."));  //(4)
            File file = new File(realPath + newImageName);  //(5)
            try {
                imageFile.transferTo(file);  //(6)
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newImageName;  //(7)
    }
}
