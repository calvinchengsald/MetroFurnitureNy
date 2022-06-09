package com.metro.model;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "EdgeOption")
public class EdgeOption implements Serializable {

	
	private static final long serialVersionUID = -3887064715595210063L;
	private String edge;
	private String description;
	private String url;
	


    public static EdgeOption createEdgeOption(String item_code){
    	EdgeOption p = new EdgeOption();
    	p.setEdge(item_code);
    	return p;
    }
    

    @DynamoDBHashKey
	public String getEdge() {
		return edge;
	}
	public void setEdge(String edge) {
		this.edge = edge;
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
