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
import com.metro.model.SeatOption;
import com.metro.repository.SeatOptionRepository;

@CrossOrigin(origins = {"http://localhost:3000", "http://metro2-furniture-ny.com.s3-website-us-east-1.amazonaws.com", "https://wwww.metrofurnitureny.com"})
@RestController
@RequestMapping("/seatoption")
public class SeatOptionController {

	@Autowired
	private SeatOptionRepository repository;


	@PostMapping
	public String insertIntoDynamoDB(@RequestBody SeatOption p) {
		repository.insert(p);
		return "Succuess in inserting";
	}
    

	@GetMapping
	public ResponseEntity<SeatOption> getOneSeatOptionDetails(@RequestParam String edge) {
		SeatOption p = repository.getOneBySeat(edge);
		return new ResponseEntity<SeatOption>(p, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "{subtype}")
	public void deleteSeatOption(@PathVariable("subtype") String subtype) {
		repository.delete(SeatOption.createSeatOption(subtype));
	}
    

	@GetMapping(value = "/all")
	public ResponseEntity<List<SeatOption>> getAll() {
		List<SeatOption> p = repository.getAll();
		return new ResponseEntity<List<SeatOption>>(p, HttpStatus.OK);
	}
 
}
