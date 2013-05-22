package com.evi.domain;
/**
 * 
 *  This class will set up the application properties for the testsuite 
 *  @author Fathea Chowdhury
 *  @version 1.0
 *  
 *  
 */
import java.util.Properties;
public class ApplicationProperties {
	private Properties properties = new Properties();
	private String [] params = {"location", "url","username","password"};
	private String [] arguments;
	public ApplicationProperties(String [] args){
		arguments = args;
	}
	/*
	 * getter method to return the value from the properties map
	 */
	public Properties setup(){
		for(int i=0;i<arguments.length;i++){
			properties.setProperty(params[i],arguments[i]);
		}
		return properties;
	}
	public String getProperty(String key){	
		return properties.getProperty(key);
	}
}
