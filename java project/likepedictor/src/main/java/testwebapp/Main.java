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
    	
        get("/predict/:id", (req, res) ->  LikePredictor.LikePredictor.predictLikes(  req.params(":id") ) );
        get("/fetchposts/:token", (req, res) -> fbloader.FetchPosts.startDownload(req.params(":token"))    );
        get("/status/:id", (req, res) ->  fbloader.FetchPosts.getStatus( Integer.parseInt( req.params(":id")) ) );
        
        
    }
}