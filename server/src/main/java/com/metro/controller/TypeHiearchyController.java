package com.metro.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.metro.entities.ApiResponse;
import com.metro.exception.AuthenticationException;
import com.metro.exception.DatabaseExceptions;
import com.metro.exception.ItemAlreadyExistsException;
import com.metro.exception.UndefinedItemCodeException;
import com.metro.model.Authentication;
import com.metro.model.DeleteUpdateModel;
import com.metro.model.ProductInfo;
import com.metro.model.SubtypeHiearchy;
import com.metro.model.TypeHiearchy;
import com.metro.repository.ProductInfoRepository;
import com.metro.repository.TypeHiearchyRepository;
import com.metro.utils.Standardization;

@CrossOrigin(origins = {"http://localhost:3000", "http://metro2-furniture-ny.com.s3-website-us-east-1.amazonaws.com", "https://wwww.metrofurnitureny.com"})
@RestController                             
@RequestMapping("/typehiearchy")
public class TypeHiearchyController {

	@Autowired
	private TypeHiearchyRepository repository;
	@Autowired
	private ProductInfoRepository productInfoRepository;


	@PostMapping(value = "/insert")
	public ResponseEntity<ApiResponse<TypeHiearchy>> insertIntoDynamoDB(@RequestHeader Map<String, String> headers, @RequestBody TypeHiearchy p) {
		try {
			Authentication.authenticate(headers);
			repository.insert(p);
			return new ResponseEntity<ApiResponse<TypeHiearchy>>(new ApiResponse<TypeHiearchy>(p,HttpStatus.OK, ""),HttpStatus.OK);
		} catch (DatabaseExceptions e ) {
			if (e.getClass().equals(ItemAlreadyExistsException.class)){
				return new ResponseEntity<ApiResponse<TypeHiearchy>>(new ApiResponse<TypeHiearchy>(p,HttpStatus.BAD_REQUEST, "An item with this item code: [" +p.getM_type() + "] already exists"),HttpStatus.BAD_REQUEST);
			}
			
			return new ResponseEntity<ApiResponse<TypeHiearchy>>(new ApiResponse<TypeHiearchy>(p,HttpStatus.BAD_REQUEST, e.getMessage()),HttpStatus.BAD_REQUEST);
		} catch (AuthenticationException e ) {
			return new ResponseEntity<ApiResponse<TypeHiearchy>>(new ApiResponse<TypeHiearchy>(p,HttpStatus.UNAUTHORIZED, e.getMessage()),HttpStatus.UNAUTHORIZED);
		}
		
		
	}
	


	@GetMapping
	public ResponseEntity<ApiResponse<TypeHiearchy>> getOneProductInfoDetails(@RequestParam String item_code) {
		TypeHiearchy p = repository.getOneByType(item_code);
		return new ResponseEntity<ApiResponse<TypeHiearchy>>(new ApiResponse<TypeHiearchy>(p,HttpStatus.OK, ""),HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/delete")
	public ResponseEntity<ApiResponse<TypeHiearchy>> deleteProductInfo(@RequestHeader Map<String, String> headers, @RequestBody TypeHiearchy p) {
		try {
			Authentication.authenticate(headers);
			repository.delete(p);
			return new ResponseEntity<ApiResponse<TypeHiearchy>>(new ApiResponse<TypeHiearchy>(p,HttpStatus.OK, ""),HttpStatus.OK);
		} catch (AuthenticationException e ) {
			return new ResponseEntity<ApiResponse<TypeHiearchy>>(new ApiResponse<TypeHiearchy>(null,HttpStatus.UNAUTHORIZED, e.getMessage()),HttpStatus.UNAUTHORIZED);
		}
	}
    


	@PostMapping(value= "/update")
	public ResponseEntity<ApiResponse<TypeHiearchy>> updateDynamoDB(@RequestHeader Map<String, String> headers, @RequestBody TypeHiearchy p) {
		try {
			Authentication.authenticate(headers);
			repository.update(p);
			return new ResponseEntity<ApiResponse<TypeHiearchy>>(new ApiResponse<TypeHiearchy>(p,HttpStatus.OK, ""),HttpStatus.OK);
		} catch (DatabaseExceptions e ) {
			if (e.getClass().equals(ItemAlreadyExistsException.class)){
				return new ResponseEntity<ApiResponse<TypeHiearchy>>(new ApiResponse<TypeHiearchy>(p,HttpStatus.BAD_REQUEST, "An item with this item code: [" +p.getM_type() + "] already exists"),HttpStatus.BAD_REQUEST);
			}
			
			return new ResponseEntity<ApiResponse<TypeHiearchy>>(new ApiResponse<TypeHiearchy>(p,HttpStatus.BAD_REQUEST, e.getMessage()),HttpStatus.BAD_REQUEST);
		} catch (AuthenticationException e ) {
			return new ResponseEntity<ApiResponse<TypeHiearchy>>(new ApiResponse<TypeHiearchy>(p,HttpStatus.UNAUTHORIZED, e.getMessage()),HttpStatus.UNAUTHORIZED);
		}
	}
	

	//Used when you want to change the primary key. Will delete the record with that key and add in a new record
	// will update all products with link to this type and use the new type key
	@PostMapping(value= "/deleteupdate")
	public ResponseEntity<ApiResponse<TypeHiearchy>> deleteUpdateDynamoDB(@RequestHeader Map<String, String> headers, @RequestBody DeleteUpdateModel<TypeHiearchy> p) {
		try {
			Authentication.authenticate(headers);
			if(Standardization.isInvalidString(p.getModel().getM_type())) { 
				throw new UndefinedItemCodeException("Unable to process item with invalid item code: [" +p.getModel().getM_type() + "]" );
			}
			repository.delete( TypeHiearchy.createTypeHiearchy(p.getPrePrimaryKey()));
			repository.insert(p.getModel());
			


			//updating products that had existing linkages this this subtype
			List<ProductInfo> needUpdateProducts = productInfoRepository.getAllByAttr("m_type", p.getPrePrimaryKey());
			for( ProductInfo t : needUpdateProducts) {
				t.setM_type(p.getModel().getM_type());
			}
			productInfoRepository.batchUpdate(needUpdateProducts);
			
			
			return new ResponseEntity<ApiResponse<TypeHiearchy>>(new ApiResponse<TypeHiearchy>(p.getModel(),HttpStatus.OK, ""),HttpStatus.OK);
		} catch (DatabaseExceptions e ) {
			
			return new ResponseEntity<ApiResponse<TypeHiearchy>>(new ApiResponse<TypeHiearchy>(p.getModel(),HttpStatus.BAD_REQUEST, e.getMessage()),HttpStatus.BAD_REQUEST);
		} catch (AuthenticationException e ) {
			return new ResponseEntity<ApiResponse<TypeHiearchy>>(new ApiResponse<TypeHiearchy>(p.getModel(),HttpStatus.UNAUTHORIZED, e.getMessage()),HttpStatus.UNAUTHORIZED);
		}
	}
	


	@GetMapping(value = "/all")
	public  ResponseEntity<ApiResponse<List<TypeHiearchy>>> getAll() {
		List<TypeHiearchy> p = repository.getAll();
		return new ResponseEntity<ApiResponse<List<TypeHiearchy>>>(new ApiResponse<List<TypeHiearchy>>(p,HttpStatus.OK, ""),HttpStatus.OK);
	}
 
}
