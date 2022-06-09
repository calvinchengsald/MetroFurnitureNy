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
import com.metro.repository.EdgeOptionRepository;


//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin
@RestController            
@RequestMapping("/edgeoption")
public class EdgeOptionController {

	@Autowired
	private EdgeOptionRepository repository;


	@PostMapping
	public String insertIntoDynamoDB(@RequestBody EdgeOption p) {
		repository.insert(p);
		return "Succuess in inserting";
	}
    

	@GetMapping
	public ResponseEntity<EdgeOption> getOneEdgeOptionDetails(@RequestParam String edge) {
		EdgeOption p = repository.getOneByEdge(edge);
		return new ResponseEntity<EdgeOption>(p, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "{item_code}")
	public void deleteEdgeOption(@PathVariable("item_code") String item_code) {
		repository.delete(EdgeOption.createEdgeOption(item_code));
	}
    

	@GetMapping(value = "/all")
	public ResponseEntity<List<EdgeOption>> getAll() {
		List<EdgeOption> p = repository.getAll();
		return new ResponseEntity<List<EdgeOption>>(p, HttpStatus.OK);
	}
	
    
 
}
