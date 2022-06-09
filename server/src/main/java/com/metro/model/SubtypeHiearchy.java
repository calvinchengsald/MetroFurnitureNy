package com.metro.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "SubtypeHiearchy")
public class SubtypeHiearchy implements Serializable {

	private static final long serialVersionUID = -2799150963436786782L;
	private String m_subtype;
	private String m_description;
	private String m_url;
	


    public static SubtypeHiearchy createSubtypeHiearchy(String size){
    	SubtypeHiearchy p = new SubtypeHiearchy();
    	p.setM_subtype(size);
    	return p;
    }



    @DynamoDBHashKey
	public String getM_subtype() {
		return m_subtype;
	}



	public void setM_subtype(String m_subtype) {
		this.m_subtype = m_subtype;
	}



    @DynamoDBAttribute
    @Value("")
	public String getM_description() {
		return m_description;
	}



	public void setM_description(String m_description) {
		this.m_description = m_description;
	}



    @DynamoDBAttribute
    @Value("")
	public String getM_url() {
		return m_url;
	}



	public void setM_url(String m_url) {
		this.m_url = m_url;
	}


    
	
	
}
