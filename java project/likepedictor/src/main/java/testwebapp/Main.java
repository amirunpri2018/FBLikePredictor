package testwebapp;

import static spark.Spark.*;
import org.json.*;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class Main {
	static String papa = "  ";
	
    public static void main(String[] args) {
    	port(8081);
    	spark.Spark.staticFileLocation("/public");
    	
        get("/hello", (req, res) -> ("Hello World" + papa ));
        get("/ms", (req, res) -> "Hello World or microsoft word ... sam nigga ");
        
       
        
    }
}