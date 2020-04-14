package com.order.dining.utils;

import com.order.dining.config.ImageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author: baojx
 * @Date: 2020/04/08 21:25
 * @Desc:
 */
@Component
public class UploadUtil {
    @Resource
    private ImageConfig imageConfig;

    private static ImageConfig imageConfigProxy;

    @PostConstruct
    public void init() {
        UploadUtil.imageConfigProxy = imageConfig;
    }

    public static String uploadImage(MultipartFile imageFile) {
        String newImageName = "";
        if (!imageFile.isEmpty()) {
            String realPath = imageConfigProxy.getRealPath();
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
