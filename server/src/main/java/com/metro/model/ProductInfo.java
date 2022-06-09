package com.metro.model;

import java.io.Serializable;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "ProductInfo")
public class ProductInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String item_code; 		// combination of base code and size  --  UVCT-2424   - will be unique to determine the price for this item
									// UVCT1-2424  / WC101 (no need for sizing here)
	private String base_code;		// UVCT1 / ELT10 / VWT12 / WC101         filtering here should have multiple size and color options
    private String notes;
    private String cost;
    private String thickness;
    private String m_type;		//chair / table / base
    private String m_subtype;		// metal barstool / formica / beechwood     
    private List<String> tag;
    private String m_size;			// price dependant
    private List<Color> color;		// not price dependant, will effect picture url
    private List<String> edge_option;
    private List<SeatOption> seat_option;
 
    
    public static ProductInfo createProductInfo(String item_code){
    	ProductInfo p = new ProductInfo();
    	p.setItem_code(item_code);
    	return p;
    }
    
    @DynamoDBHashKey
    public String getItem_code() {
        return item_code;
    }
 
    @DynamoDBAttribute
    public String getCost() {
        return cost;
    }

    

	@DynamoDBAttribute
    public String getBase_code() {
		return base_code;
	}

	public void setBase_code(String base_code) {
		this.base_code = base_code;
	}

	@DynamoDBAttribute
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

    @DynamoDBAttribute
	public String getThickness() {
		return thickness;
	}

	public void setThickness(String thickness) {
		this.thickness = thickness;
	}


    @DynamoDBAttribute
	public List<String> getTag() {
		return tag;
	}

	public void setTag(List<String> tag) {
		this.tag = tag;
	}


    @DynamoDBAttribute
	public List<String> getEdge_option() {
		return edge_option;
	}

	public void setEdge_option(List<String> edge_option) {
		this.edge_option = edge_option;
	}


	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

    @DynamoDBAttribute
	public List<Color> getColor() {
		return color;
	}

	public void setColor(List<Color> color) {
		this.color = color;
	}


    @DynamoDBAttribute
	public List<SeatOption> getSeat_option() {
		return seat_option;
	}

	public void setSeat_option(List<SeatOption> seat_option) {
		this.seat_option = seat_option;
	}

    @DynamoDBAttribute
	public String getM_type() {
		return m_type;
	}

	public void setM_type(String m_type) {
		this.m_type = m_type;
	}

    @DynamoDBAttribute
	public String getM_subtype() {
		return m_subtype;
	}

	public void setM_subtype(String m_subtype) {
		this.m_subtype = m_subtype;
	}

    @DynamoDBAttribute
	public String getM_size() {
		return m_size;
	}

	public void setM_size(String m_size) {
		this.m_size = m_size;
	}


    
	

}