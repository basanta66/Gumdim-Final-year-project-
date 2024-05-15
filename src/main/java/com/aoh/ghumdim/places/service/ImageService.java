package com.aoh.ghumdim.places.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String upload(MultipartFile multipartFile);

}
