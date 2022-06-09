package com.metro.utils;

public class Standardization {

	
	public static boolean isInvalidString( String s) {
		return s==null || s.equals("") || s.toUpperCase().equals("UNDEFINED") || s.toUpperCase().equals("NULL");
	}
}
