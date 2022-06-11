package com.metro.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metro.entities.ApiResponse;
import com.metro.model.Authentication;

@CrossOrigin(origins = {"http://localhost:3000", "http://metro2-furniture-ny.com.s3-website-us-east-1.amazonaws.com", "https://wwww.metrofurnitureny.com"})
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {


	@PostMapping(value = "/login")
	public ResponseEntity<ApiResponse<Authentication>> authenticate(@RequestBody Authentication auth) {
		System.out.println("enter with " + auth);
		auth.authenticate();
		return new ResponseEntity<ApiResponse<Authentication>>(new ApiResponse<Authentication>(auth,HttpStatus.OK, ""),HttpStatus.OK);
	}
	

	@PostMapping(value = "/login2")
	public ResponseEntity<ApiResponse<Authentication>> authenticate() {
		Authentication auth = new Authentication("kiwi","iscool");
		System.out.println("enter with " + auth);
		return new ResponseEntity<ApiResponse<Authentication>>(new ApiResponse<Authentication>(auth,HttpStatus.OK, ""),HttpStatus.OK);
	}

	@GetMapping(value = "/login")
	public  ResponseEntity<ApiResponse<Authentication>> getAll() {
		Authentication auth = new Authentication("kiwi","iscool");
		return new ResponseEntity<ApiResponse<Authentication>>(new ApiResponse<Authentication>(auth,HttpStatus.OK, ""),HttpStatus.OK);
	}

	@GetMapping(value = "/login2")
	public  ResponseEntity<ApiResponse<Authentication>> getAll2() {
		Authentication auth = new Authentication("kiwi","kiwiisfat");
		return new ResponseEntity<ApiResponse<Authentication>>(new ApiResponse<Authentication>(auth,HttpStatus.OK, ""),HttpStatus.OK);
	}
	
    
 
}
