package com.boun.semanticweb.base;

import com.google.gson.Gson;

/**
 * this class is to handle Json serialization stuff
 * @author onurm
 *
 */
public class JsonHandler {
	
	/**
	 * this method converts an object to JSON format
	 * @param src
	 * @return
	 */
	public static String convertToJSON(Object src){
		
		String json = new Gson().toJson(src);
		return json;
	}
}
