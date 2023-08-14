package com.socialconnect.model;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.socialconnect.model.vm.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

    private final static String BUCKET_GET_FRONT = "socialconnects3images";

    private final static String BUCKET_PUT_BACK = "socialconnects3userimages";


    /*@Autowired
    private AmazonS3Client amazonS3Client;*/

    @Autowired
    private AmazonS3 amazonS3;


    public String putObject(MultipartFile multipartFile) {
        if (multipartFile == null) {
            return null;
        }
        String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        String key =String.format("%s.%s", UUID.randomUUID(),extension);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_PUT_BACK, key, multipartFile.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putObjectRequest);
            return key;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Asset getObject(String key) {
        if (key == null) {
            return null;
        }
        S3Object s3Object  = amazonS3.getObject(BUCKET_GET_FRONT,key);
        ObjectMetadata metadata = s3Object.getObjectMetadata();

        try {
            S3ObjectInputStream inputStream = s3Object.getObjectContent();
            byte[] bytes = IOUtils.toByteArray(inputStream);
            return new Asset(bytes,metadata.getContentType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteObject(String key){
        if (key != null) {
            amazonS3.deleteObject(BUCKET_PUT_BACK,key);
            amazonS3.deleteObject(new DeleteObjectRequest(BUCKET_GET_FRONT,key));
        }
    }

    public String getObjectUrl(String key){
        if (key == null) {
            return null;
        }
        return String.format("https://%s.s3.amazonaws.com/%s", BUCKET_PUT_BACK,key);
    }
}
