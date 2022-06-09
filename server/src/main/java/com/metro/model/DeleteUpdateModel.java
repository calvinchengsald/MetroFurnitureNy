package com.metro.model;

import java.io.Serializable;

/**
 * stores the pre primary key of an object, along with the new object
 * used when you want to update the primary key of a record
 * first remove existing record with primary key, then insert the new record with the updated primary key
 * @author Calvin
 *
 * @param <T>
 */
public class DeleteUpdateModel<T> implements Serializable {

	private T model;
	private String prePrimaryKey;
	
	
	public DeleteUpdateModel<T> CreateDeleteUpdateModel ( T model, String prePrimaryKey){
		DeleteUpdateModel<T> p = new DeleteUpdateModel<T>();
		p.setModel(model);
		p.setPrePrimaryKey(prePrimaryKey);
		return p;
	}
	
	public T getModel() {
		return model;
	}
	public void setModel(T model) {
		this.model = model;
	}
	public String getPrePrimaryKey() {
		return prePrimaryKey;
	}
	public void setPrePrimaryKey(String prePrimaryKey) {
		this.prePrimaryKey = prePrimaryKey;
	}
	
	
	
}
