package LikePredictor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class LikePredictor {
	
	
	
	static SentimentalScore sentscorer;
	
	static{
		try {
			sentscorer = new SentimentalScore("src/main/resources/senti_dataset.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static ArrayList<Double> PostToVector(JSONObject post)
	{
		ArrayList<Double>  t = new ArrayList<Double> ();
		
		String content = post.getString("text");
		
		int text_length = content.length();
		t.add(text_length + 0.0 );
		
		int wordCount = content.split("[\\W]").length;
		t.add(wordCount + 0.0 );
		
		t.add(   sentscorer.getStringSentiment(content)   );
		
		t.add(   1.0  );
		
//		t.add( SuperlativeWords.nSuperlative(content) + 1.0 );
		
		String type = post.getString("type");
		
		if(type.equals("mobile_status_update"))
			t.add(   1.0  );
		else if(type.equals("wall_post"))
			t.add(   2.0  );
		else if(type.equals("shared_story"))
			t.add(   4.0  );
		else if(type.equals("added_photos"))
			t.add(   8.0  );
		else
			t.add(   0.0  );
		
		
		FogIndexCalculator c = new FogIndexCalculator("hello i am a boy");
//		t.add(c.getFogIndex());
		

		
		
		return t;
	}
	
	static Double getPostsLikes(JSONObject post)
	{
		return post.getInt("likes")+0.0;
	}
	
	public static String predictLikes(String fName )
	{
		 String jsonData = readFile("./src/main/java/DataMgr/"+fName+".json");
		 JSONArray posts = new JSONArray(jsonData);
		 
		 LinierRegression l = new LinierRegression();
		 int i;
		 for( i=0; i < posts.length()*.80 ; i++)
		 {			
				 if ( getPostsLikes(posts.getJSONObject(i)) >= -0.5 )
				 {
					 l.add(PostToVector(posts.getJSONObject(i)  ),   getPostsLikes(posts.getJSONObject(i)) );
					 posts.getJSONObject(i).put("tt", "train");
				 }
		
		 }
		 
		 l.solve();
		 
		Double trainErrorSum = 0.0;
		int traincount = 0;
		
		Double testErrorSum = 0.0;
		int testCount = 0;
		
		 for( i=0; i < posts.length() ; i++)
		 {
			 Double d = l.predict(PostToVector(posts.getJSONObject(i)  ));
			 if ( getPostsLikes(posts.getJSONObject(i)) >=  -0.5 )
			 {
				 
				 
				 if(!posts.getJSONObject(i).has("tt"))
					 posts.getJSONObject(i).put("tt", "test");
				 
				 Double actual = getPostsLikes(posts.getJSONObject(i)) ;
				 d = Math.round(d)+0.0;
				 Double predicted= d;
				
				 Double error = Math.abs(predicted-actual)*100/predicted;
				 
				 posts.getJSONObject(i).put("predicted_likes", predicted);
				 posts.getJSONObject(i).put("error", error );
				 
				 
				 if(posts.getJSONObject(i).getString("tt").equals("train"))
				 {
					 trainErrorSum += error;
					 traincount++;
				 }
				 else
				 {
					 testErrorSum += error;
					 testCount++;
				 }
				 

			 }
				 
		 }
		 
		 System.out.println("Train  Error " + trainErrorSum/traincount );
		 System.out.println("Test  Error " + testErrorSum/testCount );
		 
		 JSONObject o = new JSONObject();
		 
		 o.put("train_error", trainErrorSum/traincount  );
		 o.put("test_error",  testErrorSum/testCount );
		 o.put("posts", posts);
	 	 
	 	
		 return o.toString();
		 
		 
		 
		 
	}
	
	
	public static String readFile(String filename) {
	    String result = "";
	    try {
	        BufferedReader br = new BufferedReader(new FileReader(filename));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            line = br.readLine();
	        }
	        result = sb.toString();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	 public static void main(String[] args) {
		 //predictLikes();
	 
	 }
	
	
	
	
	
	
}
