package com.metro.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.metro.exception.DatabaseExceptions;
import com.metro.exception.ItemAlreadyExistsException;
import com.metro.exception.ItemDoesNotExistsException;
import com.metro.exception.UndefinedItemCodeException;
import com.metro.model.SubtypeHiearchy;
import com.metro.utils.Standardization;

@Repository
public class SubtypeHiearchyRepository {

	
	@Autowired
	private DynamoDBMapper mapper;
	
	public void insert(SubtypeHiearchy p) throws DatabaseExceptions {

		if(Standardization.isInvalidString(p.getM_subtype())) { 
			throw new UndefinedItemCodeException("Unable to process item with invalid item code: [" +p.getM_subtype() + "]" );
		}
		if (mapper.load(SubtypeHiearchy.class, p.getM_subtype()) != null) {
			throw new ItemAlreadyExistsException("Item Code : [" +p.getM_subtype() + "] already exists in the database. Please use a different item code" );
		}
		
		mapper.save(p);
	}
	
	
	public SubtypeHiearchy getOneBySubtype(String edge) {
		return mapper.load(SubtypeHiearchy.class, edge);
	}
	

	public List<SubtypeHiearchy> getAll() {
		List<SubtypeHiearchy> all = mapper.scan(SubtypeHiearchy.class, new DynamoDBScanExpression());
		return all;

	}
	
	
	public void update(SubtypeHiearchy p) throws  DatabaseExceptions{
		if(Standardization.isInvalidString(p.getM_subtype())) { 
			throw new UndefinedItemCodeException("Unable to process item with invalid item code: [" +p.getM_subtype() + "]" );
		}
		if (mapper.load(SubtypeHiearchy.class, p.getM_subtype()) == null) {
			throw new ItemDoesNotExistsException("Code : [" +p.getM_subtype() + "] does not exists in the database. Please use a different item code" );
		}
		mapper.save(p);
	}
	
	
	public void delete(SubtypeHiearchy p) {
		mapper.delete(p);
	}
	
	public DynamoDBSaveExpression buildDynamoDBSaveExpression(SubtypeHiearchy p ) {
		DynamoDBSaveExpression exp = new DynamoDBSaveExpression();
		Map<String , ExpectedAttributeValue> expected = new HashMap<>();
		expected.put("subtype", new ExpectedAttributeValue(new AttributeValue(p.getM_subtype())).withComparisonOperator(ComparisonOperator.EQ));
		exp.setExpected(expected);
		return exp;
	}
	
	
	
}
