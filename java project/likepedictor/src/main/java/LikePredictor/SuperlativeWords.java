package LikePredictor;

public class SuperlativeWords {
	public static Double nSuperlative(String str)
	{
		String[] parts = str.split("[\\W]");
		Double sum = 0.0;
		Double total = 1.0;
		for(String w : parts)
		{
//			System.out.println(getWordSentiment(w));
			if(w.endsWith("st"))
			 sum += 1.0;
			
			total+= 1.0;
		}
		
		return sum/total;
	}
}
