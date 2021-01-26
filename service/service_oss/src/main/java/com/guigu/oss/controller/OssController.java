package com.guigu.oss.controller;

import com.guigu.commonutils.R;
import com.guigu.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    OssService ossService;

    @PostMapping
    public R uploadOssFile(MultipartFile file){
        String url = ossService.uplocadFileAvator(file);
        return R.ok().data("url",url);
    }
}
