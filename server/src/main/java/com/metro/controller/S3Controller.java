package com.metro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.metro.configuration.S3Config;
import com.metro.entities.ApiResponse;
import com.metro.model.EdgeOption;
import com.metro.model.ProductInfo;
import com.metro.repository.EdgeOptionRepository;


@CrossOrigin(origins = {"http://localhost:3000", "http://metro2-furniture-ny.com.s3-website-us-east-1.amazonaws.com", "https://wwww.metrofurnitureny.com"})
@RestController
@RequestMapping("/s3")
public class S3Controller {

	@Autowired
	S3Config s3;

	@PostMapping (value="/insert")
	public ResponseEntity<ApiResponse<String>> insertImage(@RequestParam("file") MultipartFile file, @RequestParam("filePath") String filePath) {
	
		try {
			return new ResponseEntity<ApiResponse<String>>(new ApiResponse<String>(s3.uploadFileWithPath(file, filePath),HttpStatus.OK, ""),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse<String>>(new ApiResponse<String>(filePath ,HttpStatus.BAD_REQUEST, e.getMessage()),HttpStatus.BAD_REQUEST);
		}
	}

	
	//delete path will be the full image url
	@PostMapping (value="/delete")
	public ResponseEntity<ApiResponse<String>> deleteImage(@RequestParam("filePath") String filePath) {
	
		try {
			return new ResponseEntity<ApiResponse<String>>(new ApiResponse<String>(s3.deleteFileFromS3Bucket( filePath),HttpStatus.OK, ""),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse<String>>(new ApiResponse<String>(filePath ,HttpStatus.BAD_REQUEST, e.getMessage()),HttpStatus.BAD_REQUEST);
		}
	}
	

	@PostMapping (value="/deleteupdate")
	public ResponseEntity<ApiResponse<String>> deleteInsertImage(@RequestParam("file") MultipartFile file, @RequestParam("filePath") String filePath, @RequestParam("deleteFilePath") String deleteFilePath) {
	
		try {
			s3.deleteFileFromS3Bucket( filePath);
			return new ResponseEntity<ApiResponse<String>>(new ApiResponse<String>(s3.uploadFileWithPath(file, filePath),HttpStatus.OK, ""),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse<String>>(new ApiResponse<String>(filePath ,HttpStatus.BAD_REQUEST, e.getMessage()),HttpStatus.BAD_REQUEST);
		}
	}
	
	
    
//
//	@GetMapping
//	public ResponseEntity<EdgeOption> getOneEdgeOptionDetails(@RequestParam String edge) {
//		EdgeOption p = repository.getOneByEdge(edge);
//		return new ResponseEntity<EdgeOption>(p, HttpStatus.OK);
//	}
//	
//	@DeleteMapping(value = "{item_code}")
//	public void deleteEdgeOption(@PathVariable("item_code") String item_code) {
//		repository.delete(EdgeOption.createEdgeOption(item_code));
//	}
//    
//
//	@GetMapping(value = "/all")
//	public ResponseEntity<List<EdgeOption>> getAll() {
//		List<EdgeOption> p = repository.getAll();
//		return new ResponseEntity<List<EdgeOption>>(p, HttpStatus.OK);
//	}
//	
    
 
}
