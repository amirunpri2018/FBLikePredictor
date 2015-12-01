package LikePredictor;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class URLScore {
	public static Double getURLScore(String url)
	{
		String apiUrl  = "http://api.facebook.com/restserver.php?method=links.getStats&format=json&urls=" + url;
		
		String responseJson;
		try {
			responseJson = Unirest.get(apiUrl).asString().getBody();			
			JSONArray urlStats = new JSONArray(responseJson);
		
			int share_count = urlStats.getJSONObject(0).getInt("share_count");
			int like_count = urlStats.getJSONObject(0).getInt("like_count");
			int comment_count = urlStats.getJSONObject(0).getInt("comment_count");
			
			return 0.0 +  20*share_count + like_count + comment_count*3   ;
		} catch (UnirestException e) {
			e.printStackTrace();
			return 0.0;
		}		
	}
	
	
	 public static void main(String[] args) {
		 System.out.println(getURLScore("http://idealist4ever.com/23-things-every-person-should-do-for-themselves-at-least-once-a-year/"));
	 }
	
	
}
