package com.puneetverse.processes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VideoProcess {
	@Autowired private AmazonS3 s3Client;
	
	@Value("${s3.bucketName}")
    private String bucketName; 
	
	//upload video
	public String uploadToS3(MultipartFile file) {
        try {
        	  File fileObj = convertMultiPartFileToFile(file);
        	  String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        	  s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        	  fileObj.delete();
        	  return fileName;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error uploading video to S3");
        }
    }
	
	//convert multipart into video file for uploading
	 private File convertMultiPartFileToFile(MultipartFile file) {
	     	File convertedFile = new File(file.getOriginalFilename());
	        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
	            fos.write(file.getBytes());
	        } catch (IOException e) {
	            log.error("Error converting multipartFile to file", e);
	        }
	     return convertedFile;
	}
	 
	 //sending byte information for downloading video file
	 public byte[] downloadFile(String fileName) {
	        S3Object s3Object = s3Client.getObject(bucketName, fileName);
	        S3ObjectInputStream inputStream = s3Object.getObjectContent();
	        try {
	            byte[] content = IOUtils.toByteArray(inputStream);
	            return content;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	 public String getS3VideoUrl(String videoName) {
		 String url = String.valueOf(s3Client.getUrl(bucketName, videoName));
		 return url;
	 }
	 
	 public boolean deleteFileFromS3(String fileName) {
		 try {
			 s3Client.deleteObject(bucketName, fileName);
			 return true;
		 }catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Unable to delete Videos"); 
		}
	}
}
