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
        if (!imageFile.isEmpty()) {
            //TODO 页面待配置
            String realPath = "G:\\JavaSource\\dining\\src\\main\\resources\\static\\image\\upload\\";
            String originalName = imageFile.getOriginalFilename();
            String uuidName = UUID.randomUUID().toString();
            newImageName = uuidName + originalName.substring(originalName.lastIndexOf("."));
            File file = new File(realPath + newImageName);
            try {
                imageFile.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newImageName;
    }
}
