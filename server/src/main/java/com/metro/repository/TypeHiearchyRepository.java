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
import com.metro.model.TypeHiearchy;
import com.metro.utils.Standardization;

@Repository
public class TypeHiearchyRepository {

	
	@Autowired
	private DynamoDBMapper mapper;
	
	public void insert(TypeHiearchy p) throws DatabaseExceptions {

		if(Standardization.isInvalidString(p.getM_type())) { 
			throw new UndefinedItemCodeException("Unable to process item with invalid item code: [" +p.getM_type() + "]" );
		}
		if (mapper.load(TypeHiearchy.class, p.getM_type()) != null) {
			throw new ItemAlreadyExistsException("Item Code : [" +p.getM_type() + "] already exists in the database. Please use a different item code" );
		}
		mapper.save(p);
	}
	

	
	
	public TypeHiearchy getOneByType(String edge) {
		return mapper.load(TypeHiearchy.class, edge);
	}
	

	public List<TypeHiearchy> getAll() {
		List<TypeHiearchy> all = mapper.scan(TypeHiearchy.class, new DynamoDBScanExpression());
		return all;

	}
	
	
	


	public void update(TypeHiearchy p) throws  DatabaseExceptions{
		if(Standardization.isInvalidString(p.getM_type())) { 
			throw new UndefinedItemCodeException("Unable to process item with invalid item code: [" +p.getM_type() + "]" );
		}
		if (mapper.load(TypeHiearchy.class, p.getM_type()) == null) {
			throw new ItemDoesNotExistsException("Code : [" +p.getM_type() + "] does not exists in the database. Please use a different item code" );
		}
		mapper.save(p);
	}
	
	public void batchUpdate( List<TypeHiearchy> p) {
		mapper.batchSave(p);
	}
	
	
	public void delete(TypeHiearchy p) {
		mapper.delete(p);
	}
	
	public DynamoDBSaveExpression buildDynamoDBSaveExpression(TypeHiearchy p ) {
		DynamoDBSaveExpression exp = new DynamoDBSaveExpression();
		Map<String , ExpectedAttributeValue> expected = new HashMap<>();
		expected.put("type", new ExpectedAttributeValue(new AttributeValue(p.getM_type())).withComparisonOperator(ComparisonOperator.EQ));
		exp.setExpected(expected);
		return exp;
	}
	
	


	public List<TypeHiearchy> getAllByAttr(String attr, String attrVal) {

		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(attrVal));

        DynamoDBScanExpression queryExpression = new DynamoDBScanExpression().withFilterExpression(attr+" = :val1").withExpressionAttributeValues(eav);
        List<TypeHiearchy> TypeHiearchys = mapper.scan(TypeHiearchy.class, queryExpression);
		return TypeHiearchys;
	}

	public List<TypeHiearchy> getAllByAttrList(String attr, String attrVal) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(attrVal));
        DynamoDBScanExpression queryExpression = new DynamoDBScanExpression().withFilterExpression( "contains(" + attr+", :val1) ").withExpressionAttributeValues(eav);
        List<TypeHiearchy> TypeHiearchys = mapper.scan(TypeHiearchy.class, queryExpression);
		return TypeHiearchys;
	}
	
	
	

	
}
