package com.aoh.ghumdim.places.service.impl;

import com.aoh.ghumdim.places.service.ImageService;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class ImageServiceimpl implements ImageService {

    private String getExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }
    public String upload(MultipartFile multipartFile){
        try{
            String fileName = multipartFile.getOriginalFilename();
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));
            File file = this.convertToFile(multipartFile, fileName);
            String url = this.uploadFile(file, fileName );
            file.delete();
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "Image couldn't upload, something went wrong";
        }
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException{
        File tempFile = new File(fileName);
        try(FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }
    private String uploadFile(File file, String fileName) throws IOException{
        BlobId blobId = BlobId.of("ghumdim.appspot.com",fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = DestinationServiceImpl.class.getClassLoader().getResourceAsStream("serviceAccountKey.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        String DOWNLOAD_URL ="https://firebasestorage.googleapis.com/v0/b/ghumdim.appspot.com/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));

    }

}
