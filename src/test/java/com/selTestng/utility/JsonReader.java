package com.selTestng.utility;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonReader {
	
	private static JSONObject jsonObj;
	
	public JsonReader (String file,String path) 
	{
		String dataFile = path +"/" + file;
		JSONParser parser = new JSONParser();		
		try {
			Object obj = parser.parse(new FileReader(dataFile));
			jsonObj = (JSONObject) obj;
		}
		catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public String getValue(String key)
	{
			return (String) jsonObj.get(key);		   
    }
	
	public static void main(String[] args)
	{
		JsonReader reader = new JsonReader("search_test.json","src/test/resources/config");
		System.out.println(reader.getValue("worst"));
		System.out.println(reader.getValue("bad"));
		System.out.println(reader.getValue("good"));
		System.out.println(reader.getValue("average"));
		System.out.println(reader.getValue("very good"));
		
	}
}
