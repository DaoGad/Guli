package com.guigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String uplocadFileAvator(MultipartFile file);
}
