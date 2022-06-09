package com.metro.model;

import java.io.Serializable;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "TypeHiearchy")
public class TypeHiearchy implements Serializable {

	
	private static final long serialVersionUID = 2119134097011967738L;
	private String m_type;
	private List<String> m_subtype;
	private String m_description;
	private String m_url;
	


    public static TypeHiearchy createTypeHiearchy(String size){
    	TypeHiearchy p = new TypeHiearchy();
    	p.setM_type(size);
    	return p;
    }



    public void updateSubypeList (String prevSubtype, String newSubtype) {
    	m_subtype.remove(prevSubtype);
    	m_subtype.add(newSubtype);
    }
    
    @DynamoDBHashKey
	public String getM_type() {
		return m_type;
	}



	public void setM_type(String m_type) {
		this.m_type = m_type;
	}





	@DynamoDBAttribute
	public List<String> getM_subtype() {
		return m_subtype;
	}



	public void setM_subtype(List<String> m_subtype) {
		this.m_subtype = m_subtype;
	}



	@DynamoDBAttribute
	public String getM_description() {
		return m_description;
	}



	public void setM_description(String m_description) {
		this.m_description = m_description;
	}




	@DynamoDBAttribute
	public String getM_url() {
		return m_url;
	}



	public void setM_url(String m_url) {
		this.m_url = m_url;
	}



	@Override
	public String toString() {
		return "TypeHiearchy [m_type=" + m_type + ", subtype=" + m_subtype + ", m_description=" + m_description
				+ ", m_url=" + m_url + "]";
	}


    
	
	
	
}
