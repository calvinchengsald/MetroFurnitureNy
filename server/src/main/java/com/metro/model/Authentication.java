package com.metro.model;

import java.io.Serializable;
import java.util.Map;

import com.metro.exception.AuthenticationException;



public class Authentication implements Serializable {
	public static String correctUsername;
	public static String correctPassword;

	
	private boolean authenticated;
	private String username;
	private String password;
	
	public Authentication() {
		
	}
	public Authentication(String username, String password, boolean authenticated) {
		this.username=username;
		this.password=password;
		this.authenticated=authenticated;
	}
	
	public Authentication(String username, String password) {
		this.username=username;
		this.password=password;
		authenticated = authenticate();
	}
	public boolean authenticate() {
		authenticated = username.equals(correctUsername) && password.equals(correctPassword);
		return authenticated;
	}
	public static boolean authenticate(String username, String password) {
		return username.equals(correctUsername) && password.equals(correctPassword);
	}
	

	public static void authenticate(Map<String, String> headers) throws AuthenticationException {
		if (!Authentication.authenticate(headers.get("username"), headers.get("password"))) {
			throw new AuthenticationException("You must be logged in to make this request");
		}
	}

	public boolean isAuthenticated() {
		return authenticated;
	}
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
