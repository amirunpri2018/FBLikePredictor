package fbloader;

import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.*;
import org.json.JSONObject;

import com.restfb.*;
import com.restfb.types.*;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.lang.Thread;


/*
 * me/feed?fields=likes{name},message,comments{comment_count}
 * me/feed?fields=likes.summary(true){name},message,comments{comment_count}
 * 
 * 
 * */


class AsyncPostFetcher implements Runnable{
	
	String accessToken;
	Integer id;
	
	public AsyncPostFetcher(String accessToken ,Integer id )
	{
		this.accessToken = accessToken;
		this.id = id;
	}
	
	String getPosts(String accessToken) throws Exception
	{
		
		String url  = "https://graph.facebook.com/me/feed?access_token=" + accessToken;
		url += "&fields=status_type,likes.summary(true)%7Bname%7D,message,comments.summary(true)";
		
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
					newPost.put("comments" , oldPost.getJSONObject("comments").getJSONObject("summary").getInt("total_count") );
				} catch (Exception e) { 
					newPost.put("comments" , -1);
				} 
				
				try {
					newPost.put("text" ,  oldPost.getString("message") );
				} catch (Exception e) { 
					newPost.put("text" , "");
				} 
				
				try {
					newPost.put("type" ,  oldPost.getString("status_type") );
				} catch (Exception e) { 
					newPost.put("type" , "-1");
				} 
				
				
				
				allPosts.put(newPost);
			}

			
			try {
				url = posts.getJSONObject("paging").getString("next");
			} catch (Exception e) { 
				url = "-1";
			} 
			
		}
		 
		// now all posts are fetched into allPosts
		
		//save in file
		FileWriter out = null;
		out = new FileWriter("./src/main/java/DataMgr/"+id+".json");
		out.write(allPosts.toString());
		out.close();
		
		return (allPosts.toString());
		
	}
	
	
	
	public void run() {
		
		try {
			FetchPosts.statuses.put(id, "Started" );
			FetchPosts.fetchedData.put(id, getPosts( accessToken) );
			FetchPosts.statuses.put(id, "Done" );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
}






public class FetchPosts {
	
	
	
	public static HashMap<Integer , String> statuses = new HashMap<Integer , String>();
	public static HashMap<Integer , String> fetchedData = new HashMap<Integer , String>();
	
	static Integer nRequests = 0;
	
	
	public static int startDownload(String accessToken)
	{
		nRequests++;
		statuses.put(nRequests, " " );
		AsyncPostFetcher myposter = new AsyncPostFetcher(accessToken ,  nRequests );
		Thread t1 = new Thread(myposter);
	    t1.start();
		return nRequests;
	}
	
	public static String getStatus(Integer id )
	{
		return statuses.get(id);
	}
	
	public static String getData(Integer id )
	{
		return fetchedData.get(id);
	}


	
	 public static void main(String[] args) {
		 
		 String token = "CAACEdEose0cBAE5vYjVQcByFWIFpX3ZBHAcSiAv4aPU3iCFuSg3o2QZBekgHv45r2pxwZBcmCushVGenAr8eZAd8cWsqYyqwER1qvHTV6TNAGoL0yjJXvM6IFcIgH1WWwYfPnNadE90HJRj9yL6PCn8b07Rq9xVyad85lEPvSd0gQNBQPMiBGhV9kFWF0ZCcBXf6pdQVkNwZDZD";
		 
		 int c = startDownload(token);
		// startDownload(token);
		 //startDownload(token);
		 
		 while(!getStatus(c).equals("Done"));
		 System.out.println("yohoyohoyoho");
		 System.out.println(getData(c));
	 }
}
