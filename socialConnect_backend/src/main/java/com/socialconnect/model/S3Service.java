package com.socialconnect.model;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.socialconnect.model.vm.Asset;

@Service
public class S3Service {
	
	private final static String BUCKET = "socialconnects3images";
	
	@Autowired
	private AmazonS3Client amazonS3Client;

	public S3Service() {
	}
	
	public String putObject(MultipartFile multipartFile) {
		String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
		String key =String.format("%s.%s", UUID.randomUUID(),extension);
		
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(multipartFile.getContentType());
		
		try {
			PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, key, multipartFile.getInputStream(), metadata);
			amazonS3Client.putObject(putObjectRequest);
			return key;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	Asset getObject(String key) {
		S3Object s3Object  = amazonS3Client.getObject(BUCKET,key);
		ObjectMetadata metadata = s3Object.getObjectMetadata();
		
		try {
			S3ObjectInputStream inputStream = s3Object.getObjectContent();
			byte[] bytes = IOUtils.toByteArray(inputStream);
			return new Asset(bytes,metadata.getContentType());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

}
