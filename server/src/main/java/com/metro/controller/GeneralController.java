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

import com.metro.entities.ApiResponse;
import com.metro.exception.DatabaseExceptions;
import com.metro.exception.ItemAlreadyExistsException;
import com.metro.exception.UndefinedItemCodeException;
import com.metro.model.DeleteUpdateModel;
import com.metro.model.ProductInfo;
import com.metro.repository.ProductInfoRepository;
import com.metro.utils.Standardization;

//@CrossOrigin(origins = {"http://localhost:3000", "http://metro-furniture-ny.com.s3-website.us-east-2.amazonaws.com"})

@CrossOrigin(origins = {"http://localhost:3000", "http://metro2-furniture-ny.com.s3-website-us-east-1.amazonaws.com", "https://wwww.metrofurnitureny.com"})
@RestController
@RequestMapping("/product")
public class GeneralController {

	@Autowired
	private ProductInfoRepository repository;


	@PostMapping
	public ResponseEntity<ApiResponse<ProductInfo>> insertIntoDynamoDB(@RequestBody ProductInfo p) {
		try {
			repository.insert(p);
			return new ResponseEntity<ApiResponse<ProductInfo>>(new ApiResponse<ProductInfo>(p,HttpStatus.OK, ""),HttpStatus.OK);
		} catch (DatabaseExceptions e ) {
			if (e.getClass().equals(ItemAlreadyExistsException.class)){
				return new ResponseEntity<ApiResponse<ProductInfo>>(new ApiResponse<ProductInfo>(p,HttpStatus.BAD_REQUEST, "An item with this item code: [" +p.getItem_code() + "] already exists"),HttpStatus.BAD_REQUEST);
			}
			
			return new ResponseEntity<ApiResponse<ProductInfo>>(new ApiResponse<ProductInfo>(p,HttpStatus.BAD_REQUEST, e.getMessage()),HttpStatus.BAD_REQUEST);
		}
		
		
	}
	

	@PostMapping(value= "/update")
	public ResponseEntity<ApiResponse<ProductInfo>> updateDynamoDB(@RequestBody ProductInfo p) {
		try {
			repository.update(p);
			return new ResponseEntity<ApiResponse<ProductInfo>>(new ApiResponse<ProductInfo>(p,HttpStatus.OK, ""),HttpStatus.OK);
		} catch (DatabaseExceptions e ) {
			if (e.getClass().equals(ItemAlreadyExistsException.class)){
				return new ResponseEntity<ApiResponse<ProductInfo>>(new ApiResponse<ProductInfo>(p,HttpStatus.BAD_REQUEST, "An item with this item code: [" +p.getItem_code() + "] already exists"),HttpStatus.BAD_REQUEST);
			}
			
			return new ResponseEntity<ApiResponse<ProductInfo>>(new ApiResponse<ProductInfo>(p,HttpStatus.BAD_REQUEST, e.getMessage()),HttpStatus.BAD_REQUEST);
		}
	}
    



	//Used when you want to change the primary key. Will delete the record with that key and add in a new record
	@PostMapping(value= "/deleteupdate")
	public ResponseEntity<ApiResponse<ProductInfo>> deleteUpdateDynamoDB(@RequestBody DeleteUpdateModel<ProductInfo> p) {
		try {
			if(Standardization.isInvalidString(p.getModel().getItem_code())) { 
				throw new UndefinedItemCodeException("Unable to process item with invalid item code: [" +p.getModel().getItem_code() + "]" );
			}
			repository.delete( ProductInfo.createProductInfo(p.getPrePrimaryKey()));
			repository.insert(p.getModel());
			return new ResponseEntity<ApiResponse<ProductInfo>>(new ApiResponse<ProductInfo>(p.getModel(),HttpStatus.OK, ""),HttpStatus.OK);
		} catch (DatabaseExceptions e ) {
			
			return new ResponseEntity<ApiResponse<ProductInfo>>(new ApiResponse<ProductInfo>(p.getModel(),HttpStatus.BAD_REQUEST, e.getMessage()),HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping
	public ResponseEntity<ApiResponse<ProductInfo>> getOneProductInfoDetails(@RequestParam String item_code) {
		ProductInfo p = repository.getOneByItemCode(item_code);
		return new ResponseEntity<ApiResponse<ProductInfo>>(new ApiResponse<ProductInfo>(p,HttpStatus.OK, ""),HttpStatus.OK);
	}
	
	@PostMapping(value = "/delete")
	public ResponseEntity<ApiResponse<ProductInfo>> deleteProductInfo(@RequestBody ProductInfo p) {
		repository.delete(p);
		return new ResponseEntity<ApiResponse<ProductInfo>>(new ApiResponse<ProductInfo>(p,HttpStatus.OK, ""),HttpStatus.OK);
	}
    

	@GetMapping(value = "/all")
	public  ResponseEntity<ApiResponse<List<ProductInfo>>> getAll() {
		List<ProductInfo> p = repository.getAll();
		return new ResponseEntity<ApiResponse<List<ProductInfo>>>(new ApiResponse<List<ProductInfo>>(p,HttpStatus.OK, ""),HttpStatus.OK);
	}
	

	@GetMapping(value = "/m_type")
	public ResponseEntity<ApiResponse<List<ProductInfo>>> getAllType(@RequestParam String value) {
		List<ProductInfo> p = repository.getAllByAttr("m_type", value);
		return new ResponseEntity<ApiResponse<List<ProductInfo>>>(new ApiResponse<List<ProductInfo>>(p,HttpStatus.OK, ""),HttpStatus.OK);
	}

	@GetMapping(value = "/m_subtype")
	public ResponseEntity<ApiResponse<List<ProductInfo>>> getAllSubtype(@RequestParam String value) {
		List<ProductInfo> p = repository.getAllByAttr("m_subtype", value);
		return new ResponseEntity<ApiResponse<List<ProductInfo>>>(new ApiResponse<List<ProductInfo>>(p,HttpStatus.OK, ""),HttpStatus.OK);
	}

	@GetMapping(value = "/base_code")
	public ResponseEntity<ApiResponse<List<ProductInfo>>> getAllBase_code(@RequestParam String value) {
		List<ProductInfo> p = repository.getAllByAttr("base_code", value);
		return new ResponseEntity<ApiResponse<List<ProductInfo>>>(new ApiResponse<List<ProductInfo>>(p,HttpStatus.OK, ""),HttpStatus.OK);
	}

	@GetMapping(value = "/tag")
	public ResponseEntity<ApiResponse<List<ProductInfo>>> getAllTag(@RequestParam String value) {
		List<ProductInfo> p = repository.getAllByAttrList("tag", value);
		return new ResponseEntity<ApiResponse<List<ProductInfo>>>(new ApiResponse<List<ProductInfo>>(p,HttpStatus.OK, ""),HttpStatus.OK);
	}
 
}
