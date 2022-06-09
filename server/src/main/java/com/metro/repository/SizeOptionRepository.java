package com.metro.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.metro.model.ProductInfo;
import com.metro.model.SizeOption;

@Repository
public class SizeOptionRepository {

	
	@Autowired
	private DynamoDBMapper mapper;
	
	public void insert(SizeOption p) {
		mapper.save(p);
	}
	
	public SizeOption getOneBySize(String edge) {
		return mapper.load(SizeOption.class, edge);
	}
	

	public List<SizeOption> getAll() {
		List<SizeOption> all = mapper.scan(SizeOption.class, new DynamoDBScanExpression());
		return all;

	}
	
	
	public void update(SizeOption p) {
		try {
			mapper.save(p, buildDynamoDBSaveExpression(p));
		} catch (ConditionalCheckFailedException e) {
			System.out.println("invalid data - " + e.getMessage());
		}
	}
	
	public void delete(SizeOption p) {
		mapper.delete(p);
	}
	
	public DynamoDBSaveExpression buildDynamoDBSaveExpression(SizeOption p ) {
		DynamoDBSaveExpression exp = new DynamoDBSaveExpression();
		Map<String , ExpectedAttributeValue> expected = new HashMap<>();
		expected.put("size", new ExpectedAttributeValue(new AttributeValue(p.getSize())).withComparisonOperator(ComparisonOperator.EQ));
		exp.setExpected(expected);
		return exp;
	}
	
	
	
}
