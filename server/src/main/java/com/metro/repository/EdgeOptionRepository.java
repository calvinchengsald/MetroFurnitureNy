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
import com.metro.model.EdgeOption;

@Repository
public class EdgeOptionRepository {

	
	@Autowired
	private DynamoDBMapper mapper;
	
	public void insert(EdgeOption p) {
		mapper.save(p);
	}
	
	public EdgeOption getOneByEdge(String edge) {
		return mapper.load(EdgeOption.class, edge);
	}
	

	public List<EdgeOption> getAll() {
		List<EdgeOption> all = mapper.scan(EdgeOption.class, new DynamoDBScanExpression());
		return all;

	}
	
	
	public void update(EdgeOption p) {
		try {
			mapper.save(p, buildDynamoDBSaveExpression(p));
		} catch (ConditionalCheckFailedException e) {
			System.out.println("invalid data - " + e.getMessage());
		}
	}
	
	public void delete(EdgeOption p) {
		mapper.delete(p);
	}
	
	public DynamoDBSaveExpression buildDynamoDBSaveExpression(EdgeOption p ) {
		DynamoDBSaveExpression exp = new DynamoDBSaveExpression();
		Map<String , ExpectedAttributeValue> expected = new HashMap<>();
		expected.put("edge", new ExpectedAttributeValue(new AttributeValue(p.getEdge())).withComparisonOperator(ComparisonOperator.EQ));
		exp.setExpected(expected);
		return exp;
	}
	
	
	
}
