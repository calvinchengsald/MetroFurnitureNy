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
import com.metro.model.SeatOption;

@Repository
public class SeatOptionRepository {

	
	@Autowired
	private DynamoDBMapper mapper;
	
	public void insert(SeatOption p) {
		mapper.save(p);
	}
	
	public SeatOption getOneBySeat(String edge) {
		return mapper.load(SeatOption.class, edge);
	}
	

	public List<SeatOption> getAll() {
		List<SeatOption> all = mapper.scan(SeatOption.class, new DynamoDBScanExpression());
		return all;

	}
	
	
	public void update(SeatOption p) {
		try {
			mapper.save(p, buildDynamoDBSaveExpression(p));
		} catch (ConditionalCheckFailedException e) {
			System.out.println("invalid data - " + e.getMessage());
		}
	}
	
	public void delete(SeatOption p) {
		mapper.delete(p);
	}
	
	public DynamoDBSaveExpression buildDynamoDBSaveExpression(SeatOption p ) {
		DynamoDBSaveExpression exp = new DynamoDBSaveExpression();
		Map<String , ExpectedAttributeValue> expected = new HashMap<>();
		expected.put("seat", new ExpectedAttributeValue(new AttributeValue(p.getSeat())).withComparisonOperator(ComparisonOperator.EQ));
		exp.setExpected(expected);
		return exp;
	}
	
	
	
}
