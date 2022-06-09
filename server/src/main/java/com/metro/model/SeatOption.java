package com.metro.model;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBDocument
@DynamoDBTable(tableName = "SeatOption")
public class SeatOption implements Serializable {

	
	private static final long serialVersionUID = 8928438325836772381L;
	private String seat;
	private String url;
	


    public static SeatOption createSeatOption(String item_code){
    	SeatOption p = new SeatOption();
    	p.setSeat(item_code);
    	return p;
    }



    @DynamoDBHashKey
	public String getSeat() {
		return seat;
	}



	public void setSeat(String seat) {
		this.seat = seat;
	}



    @DynamoDBHashKey
	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}
    

    
	
	
	
}
