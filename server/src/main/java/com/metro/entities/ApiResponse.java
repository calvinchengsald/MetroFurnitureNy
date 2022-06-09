package com.metro.entities;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {

	private HttpStatus status;
	private String message;
	private T content;
	
	public ApiResponse(){
		
	}

	public ApiResponse(T content, HttpStatus status, String message){
		this.content=content;
		this.status=status;
		this.message=message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}
	
	
	
}
