package fbloader;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import org.json.*;
import org.json.JSONObject;

import com.restfb.*;
import com.restfb.types.*;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;



/*
 * me/feed?fields=likes{name},message,comments{comment_count}
 * me/feed?fields=likes.summary(true){name},message,comments{comment_count}
 * 
 * 
 * */

public class FetchPosts {
	
	
	String getStatus()
	{
		return "";
	}
	
	static void getPosts(String accessToken) throws Exception
	{
		
		String url  = "https://graph.facebook.com/me/feed?access_token=" + accessToken;
		url += "&fields=likes.summary(true)%7Bname%7D,message,comments.summary(true)";
		
		JSONArray allPosts = new JSONArray();;
		
		
		while(!(url.equals("-1")))
		{
			String responseJson = Unirest.get(url).asString().getBody();
			JSONObject posts = new JSONObject(responseJson);
			
						
			
			for( int i=0; i <  posts.getJSONArray("data").length() ; i++ )
			{
//				System.out.println( posts.getJSONArray("data").getJSONObject(i).toString()   );
				JSONObject oldPost = posts.getJSONArray("data").getJSONObject(i);
				JSONObject newPost = new JSONObject();
				
				try {
					newPost.put("likes" , oldPost.getJSONObject("likes").getJSONObject("summary").getInt("total_count") );
				} catch (Exception e) { 
					newPost.put("likes" , -1);
				} 
				
				try {
					newPost.put("text" ,  oldPost.getString("message") );
				} catch (Exception e) { 
					newPost.put("text" , "");
					System.out.println( posts.getJSONArray("data").getJSONObject(i).toString()   );
				} 
				
				
				
				allPosts.put(newPost);
			}

			
			try {
				url = posts.getJSONObject("paging").getString("next");
			} catch (Exception e) { 
				url = "-1";
			} 
			
			
//			System.out.println(url);
		}
		 
		System.out.println(allPosts.toString());
		 
		
		
		
		
		
		
		
	}
	
	static void GetLikes()
	{
		
	}
	
	 public static void main(String[] args) {
		 
		 String token = "CAACEdEose0cBAISzPaCLZCXYpbH7db4ak5WuTp8ZCmv2QSMYYoA1OnDrE75zTth7ZBb7vGnjs5bkqZCANzxS4lOCkd0u5z7XmnL2Vx8tjC1lM3QxSaCXTsv4yaJv9aE97uiHMKR7ZAYJEudI18pargkIjACRsXNX3AVxOKPD4p5a1BBE12axRse7a2bIeJZAPVZAcMWJlwSvAZDZD";
		 try {
			getPosts(token);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 System.out.println("yohoyohoyoho");
	 }
}
