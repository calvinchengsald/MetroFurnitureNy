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
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.metro.exception.DatabaseExceptions;
import com.metro.exception.ItemAlreadyExistsException;
import com.metro.exception.ItemDoesNotExistsException;
import com.metro.exception.UndefinedItemCodeException;
import com.metro.model.ProductInfo;
import com.metro.model.TypeHiearchy;
import com.metro.utils.Standardization;

@Repository
public class ProductInfoRepository {

	
	@Autowired
	private DynamoDBMapper mapper;
	
	public void insert(ProductInfo p) throws DatabaseExceptions {

		if(Standardization.isInvalidString(p.getItem_code())) { 
			throw new UndefinedItemCodeException("Unable to process item with invalid item code: [" +p.getItem_code() + "]" );
		}
		if (mapper.load(ProductInfo.class, p.getItem_code()) != null) {
			throw new ItemAlreadyExistsException("Item Code : [" +p.getItem_code() + "] already exists in the database. Please use a different item code" );
		}
		
		mapper.save(p);
	}
	
	public ProductInfo getOneByItemCode(String item_code) {
		return mapper.load(ProductInfo.class, item_code);
	}
	

	public List<ProductInfo> getAll() {
		List<ProductInfo> all = mapper.scan(ProductInfo.class, new DynamoDBScanExpression());
		return all;

	}

	public List<ProductInfo> getAllByAttr(String attr, String attrVal) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(attrVal));

        DynamoDBScanExpression queryExpression = new DynamoDBScanExpression().withFilterExpression(attr+" = :val1").withExpressionAttributeValues(eav);
        List<ProductInfo> productInfos = mapper.scan(ProductInfo.class, queryExpression);
		return productInfos;
	}

	public List<ProductInfo> getAllByAttrList(String attr, String attrVal) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(attrVal));

        DynamoDBScanExpression queryExpression = new DynamoDBScanExpression().withFilterExpression( "contains(" + attr+", :val1) ").withExpressionAttributeValues(eav);
        List<ProductInfo> productInfos = mapper.scan(ProductInfo.class, queryExpression);
		return productInfos;
	}
	


	public void batchUpdate( List<ProductInfo> p) {
		mapper.batchSave(p);
	}

	public void update(ProductInfo p) throws  DatabaseExceptions{
		if(Standardization.isInvalidString(p.getItem_code())) { 
			throw new UndefinedItemCodeException("Unable to process item with invalid item code: [" +p.getItem_code() + "]" );
		}
		if (mapper.load(ProductInfo.class, p.getItem_code()) == null) {
			throw new ItemDoesNotExistsException("Item Code : [" +p.getItem_code() + "] does not exists in the database. Please use a different item code" );
		}
		mapper.save(p);
	}
	
	
	public void delete(ProductInfo p) {
		mapper.delete(p);
	}
	
	public DynamoDBSaveExpression buildDynamoDBSaveExpression(ProductInfo p ) {
		DynamoDBSaveExpression exp = new DynamoDBSaveExpression();
		Map<String , ExpectedAttributeValue> expected = new HashMap<>();
		expected.put("item_code", new ExpectedAttributeValue(new AttributeValue(p.getItem_code())).withComparisonOperator(ComparisonOperator.EQ));
		exp.setExpected(expected);
		return exp;
	}
	
	
	
}
