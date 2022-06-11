package com.metro.configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.metro.model.Authentication;

@Configuration
public class S3Config {

	private AmazonS3 s3client;

	@Value("${amazon.aws.s3.end-point}")
	private String endpointUrl;
	@Value("${amazon.aws.s3.bucketName}")
	private String bucketName;
	@Value("${amazon.aws.accesskey}")
	private String accessKey;
	@Value("${amazon.aws.secretkey}")
	private String secretKey;

	@Value("${auth.username}")
	public String username;
	@Value("${auth.password}")
	public String password;

	
	@PostConstruct
	private void initializeAmazon() {
		Authentication.correctUsername =username;
		Authentication.correctPassword =password;
		
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		this.s3client = new AmazonS3Client(credentials);
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
	    File convFile = new File(file.getOriginalFilename());
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(file.getBytes());
	    fos.close();
	    return convFile;
	}
	private String generateFileName(MultipartFile multiPart) {
	    return  multiPart.getOriginalFilename().replace(" ", "_") + "-" + new Date().getTime();
	}
	
	private void uploadFileTos3bucket(String fileName, File file) {
	    s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
	            .withCannedAcl(CannedAccessControlList.PublicRead));
	}
	public String uploadFile(MultipartFile multipartFile) throws Exception{

	    String fileUrl = "";
        File file = convertMultiPartToFile(multipartFile);
        String fileName = generateFileName(multipartFile);
        fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
        uploadFileTos3bucket(fileName, file);
        file.delete();
	    return fileUrl;
	}

	// path is a relative path, not including the metro buck, https etc
	// just like /types or /chairs/woodchair etc...
	public String uploadFileWithPath(MultipartFile multipartFile, String path) throws Exception{

	    String fileUrl = "";
        File file = convertMultiPartToFile(multipartFile);
        String relativePath =  path + generateFileName(multipartFile);
        fileUrl = endpointUrl + "/" + bucketName + "/" + relativePath;
        uploadFileTos3bucket(relativePath, file);
        file.delete();
	    return fileUrl;

	    
	}
	public String deleteFileFromS3Bucket(String fileUrl) {
		
		try {
	    String fileName = fileUrl.substring(fileUrl.lastIndexOf(bucketName) + bucketName.length()+1);
	    String resourceName = fileUrl.substring(fileUrl.lastIndexOf("/") +1);
	    System.out.println(fileName);
	    
	     s3client.deleteObject(new DeleteObjectRequest(bucketName + "/", resourceName));
//	     s3client.deleteObject(new DeleteObjectRequest(fileUrl));
	     
	     return "success?";
		} 
		catch (Exception e) {
			return "failure?";
		}
	}
	
	
	
	
	
}