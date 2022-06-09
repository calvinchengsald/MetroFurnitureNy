package com.metro.model;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "SizeOption")
public class SizeOption implements Serializable {

	private static final long serialVersionUID = 249787445165268021L;
	
	private String size;
	private String description;
	private String url;
	


    public static SizeOption createSizeOption(String size){
    	SizeOption p = new SizeOption();
    	p.setSize(size);
    	return p;
    }
    

    @DynamoDBHashKey
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}

    @DynamoDBAttribute
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

    @DynamoDBAttribute
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
