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
import com.metro.repository.SubtypeHiearchyRepository;
import com.metro.repository.TypeHiearchyRepository;
import com.metro.utils.Standardization;

@CrossOrigin(origins = {"http://localhost:3000", "http://metro2-furniture-ny.com.s3-website-us-east-1.amazonaws.com", "https://wwww.metrofurnitureny.com"})
@RestController
@RequestMapping("/subtypehiearchy")
public class SubtypeHiearchyController {

	@Autowired
	private SubtypeHiearchyRepository repository;
	@Autowired
	private TypeHiearchyRepository typeRepository;
	@Autowired
	private ProductInfoRepository productInfoRepository;


	@PostMapping
	public ResponseEntity<ApiResponse<SubtypeHiearchy>> insertIntoDynamoDB(@RequestHeader Map<String, String> headers, @RequestBody SubtypeHiearchy p) {
		try {
			Authentication.authenticate(headers);
			repository.insert(p);
			return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p,HttpStatus.OK, ""),HttpStatus.OK);
		} catch (DatabaseExceptions e ) {
			if (e.getClass().equals(ItemAlreadyExistsException.class)){
				return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p,HttpStatus.BAD_REQUEST, "An item with this item code: [" +p.getM_subtype() + "] already exists"),HttpStatus.BAD_REQUEST);
			}
			
			return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p,HttpStatus.BAD_REQUEST, e.getMessage()),HttpStatus.BAD_REQUEST);
		} catch (AuthenticationException e ) {
			return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p,HttpStatus.UNAUTHORIZED, e.getMessage()),HttpStatus.UNAUTHORIZED);
		}
		
		
	}
    


	@GetMapping
	public ResponseEntity<ApiResponse<SubtypeHiearchy>> getOneProductInfoDetails(@RequestParam String item_code) {
		SubtypeHiearchy p = repository.getOneBySubtype(item_code);
		return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p,HttpStatus.OK, ""),HttpStatus.OK);
	}
	
	@PostMapping(value = "/delete")
	public ResponseEntity<ApiResponse<SubtypeHiearchy>> deleteProductInfo(@RequestHeader Map<String, String> headers, @RequestBody SubtypeHiearchy p) {
		try {
			Authentication.authenticate(headers);
			repository.delete(p);
			return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p,HttpStatus.OK, ""),HttpStatus.OK);
		} catch (AuthenticationException e ) {
			return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(null,HttpStatus.UNAUTHORIZED, e.getMessage()),HttpStatus.UNAUTHORIZED);
		}
	}
    
	@GetMapping(value = "/all")
	public  ResponseEntity<ApiResponse<List<SubtypeHiearchy>>> getAll() {
		List<SubtypeHiearchy> p = repository.getAll();
		return new ResponseEntity<ApiResponse<List<SubtypeHiearchy>>>(new ApiResponse<List<SubtypeHiearchy>>(p,HttpStatus.OK, ""),HttpStatus.OK);
	}
 

	@PostMapping(value= "/update")
	public ResponseEntity<ApiResponse<SubtypeHiearchy>> updateDynamoDB(@RequestHeader Map<String, String> headers, @RequestBody SubtypeHiearchy p) {
		
		try {
			Authentication.authenticate(headers);
			repository.update(p);
			return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p,HttpStatus.OK, ""),HttpStatus.OK);
		} catch (DatabaseExceptions e ) {
			
			return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p,HttpStatus.BAD_REQUEST, e.getMessage()),HttpStatus.BAD_REQUEST);
		} catch (AuthenticationException e ) {
			return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p,HttpStatus.UNAUTHORIZED, e.getMessage()),HttpStatus.UNAUTHORIZED);
		}
	}



	//Used when you want to change the primary key. Will delete the record with that key and add in a new record
	// will update all types with links to this subtype and use the subype name in their m_subtype array
	// will update all products with links to this subtype and use the new subtype key
	@PostMapping(value= "/deleteupdate")
	public ResponseEntity<ApiResponse<SubtypeHiearchy>> deleteUpdateDynamoDB(@RequestHeader Map<String, String> headers, @RequestBody DeleteUpdateModel<SubtypeHiearchy> p) {
		try {
			Authentication.authenticate(headers);
			if(Standardization.isInvalidString(p.getModel().getM_subtype())) { 
				throw new UndefinedItemCodeException("Unable to process item with invalid item code: [" +p.getModel().getM_subtype() + "]" );
			}
			repository.delete( SubtypeHiearchy.createSubtypeHiearchy(p.getPrePrimaryKey()));
			repository.insert(p.getModel());
			
			//updating types that had existing linkages this this subtype
			List<TypeHiearchy> needUpdateTypes = typeRepository.getAllByAttrList("m_subtype", p.getPrePrimaryKey());
			for( TypeHiearchy t : needUpdateTypes) {
				t.updateSubypeList(p.getPrePrimaryKey(), p.getModel().getM_subtype());
			}
			typeRepository.batchUpdate(needUpdateTypes);
			

			//updating products that had existing linkages this this subtype
			List<ProductInfo> needUpdateProducts = productInfoRepository.getAllByAttr("m_subtype", p.getPrePrimaryKey());
			for( ProductInfo t : needUpdateProducts) {
				t.setM_subtype(p.getModel().getM_subtype());
			}
			productInfoRepository.batchUpdate(needUpdateProducts);
			
			return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p.getModel(),HttpStatus.OK, ""),HttpStatus.OK);
		} catch (DatabaseExceptions e ) {
			
			return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p.getModel(),HttpStatus.BAD_REQUEST, e.getMessage()),HttpStatus.BAD_REQUEST);
		} catch (AuthenticationException e ) {
			return new ResponseEntity<ApiResponse<SubtypeHiearchy>>(new ApiResponse<SubtypeHiearchy>(p.getModel(),HttpStatus.UNAUTHORIZED, e.getMessage()),HttpStatus.UNAUTHORIZED);
		}
	}
	
	
 
}
