package testwebapp;

import static spark.Spark.*;
import org.json.*;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class Main {
	static String papa = "  ";
	
    public static void main(String[] args) {
    	
    	
    	JSONObject obj = new JSONObject(" {a : '1999' }");
    	String pageName = obj.getString("a");
    	
    	
    	
    	try {
			papa = Unirest.get("http://google.com/robotss.txt").asString().getBody();
		} catch (UnirestException e) {
			e.printStackTrace();
		} 
    	  
    	
    	
    	
        get("/hello", (req, res) -> ("Hello World" + papa ));
        
        
        get("/ms", (req, res) -> "Hello World or microsoft word ... sam nigga ");
        
    }
}