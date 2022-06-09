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

import com.metro.model.EdgeOption;
import com.metro.model.SizeOption;
import com.metro.repository.SizeOptionRepository;

@CrossOrigin(origins = {"http://localhost:3000", "http://metro2-furniture-ny.com.s3-website-us-east-1.amazonaws.com"})
@RestController
@RequestMapping("/sizeoption")
public class SizeOptionController {

	@Autowired
	private SizeOptionRepository repository;


	@PostMapping
	public String insertIntoDynamoDB(@RequestBody SizeOption p) {
		repository.insert(p);
		return "Succuess in inserting";
	}
    

	@GetMapping
	public ResponseEntity<SizeOption> getOneSizeOptionDetails(@RequestParam String edge) {
		SizeOption p = repository.getOneBySize(edge);
		return new ResponseEntity<SizeOption>(p, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "{size}")
	public void deleteSizeOption(@PathVariable("size") String size) {
		repository.delete(SizeOption.createSizeOption(size));
	}
    

	@GetMapping(value = "/all")
	public ResponseEntity<List<SizeOption>> getAll() {
		List<SizeOption> p = repository.getAll();
		return new ResponseEntity<List<SizeOption>>(p, HttpStatus.OK);
	}
 
}
