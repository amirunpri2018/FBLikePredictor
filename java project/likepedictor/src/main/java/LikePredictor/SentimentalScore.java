package LikePredictor;

//http://sentiwordnet.isti.cnr.it/code/SentiWordNetDemoCode.java


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SentimentalScore {

	private Map<String, Double> dictionary;

	public SentimentalScore(String pathToSWN) throws IOException {
		// This is our main dictionary representation
		dictionary = new HashMap<String, Double>();

		// From String to list of doubles.
		HashMap<String, HashMap<Integer, Double>> tempDictionary = new HashMap<String, HashMap<Integer, Double>>();

		BufferedReader csv = null;
		try {
			csv = new BufferedReader(new FileReader(pathToSWN));
			int lineNumber = 0;

			String line;
			while ((line = csv.readLine()) != null) {
				lineNumber++;

				// If it's a comment, skip this line.
				if (!line.trim().startsWith("#")) {
					// We use tab separation
					String[] data = line.split("\t");
					String wordTypeMarker = data[0];

					// Calculate synset score as score = PosS - NegS
					Double synsetScore = Double.parseDouble(data[2])
							- Double.parseDouble(data[3]);

					// Get all Synset terms
					String[] synTermsSplit = data[4].split(" ");

					// Go through all terms of current synset.
					for (String synTermSplit : synTermsSplit) {
						// Get synterm and synterm rank
						String[] synTermAndRank = synTermSplit.split("#");
						String synTerm = synTermAndRank[0]+  "#"+ wordTypeMarker ;

						int synTermRank = Integer.parseInt(synTermAndRank[1]);
					 
						if (!dictionary.containsKey(synTerm)) {
//							System.out.println(synTerm);
							dictionary.put(synTerm, synsetScore  );
						}

					}
				}
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (csv != null) {
				csv.close();
			}
		}
	}

	public double getWordSentiment(String word) {
		word = word.toLowerCase();
		char[] types = {'a' , 'v' , 'F' , 'n'};
		
		Double max = 0.0;
		Double ans = 0.0;
		for(char t : types)
		{
			String w = word + "#" + t;
			if(dictionary.containsKey(w))
			{
				if(Math.abs(dictionary.get(w)) > max)
				{
					max = Math.abs(dictionary.get(w));
					ans = dictionary.get(w);
					
				}
			}
			
		}
		
		return ans;
		
		
	}
	
	public double getStringSentiment(String line)
	{
		String[] parts = line.split("[\\W]");
		Double sum = 0.0;
		for(String w : parts)
		{
//			System.out.println(getWordSentiment(w));
			sum += getWordSentiment(w);
		}
		
		return sum;

	}
	
	public static void main(String [] args) throws IOException {
		
		
		String pathToSWN = "src/main/resources/senti_dataset.txt";
		SentimentalScore sentiwordnet = new SentimentalScore(pathToSWN);
		
		System.out.println(sentiwordnet.getStringSentiment("i will help you"));
		System.out.println(sentiwordnet.getStringSentiment("i will murder you"));
		System.out.println(sentiwordnet.getStringSentiment("i will kill you"));
		System.out.println(sentiwordnet.getStringSentiment("i like cute dogs"));
		System.out.println(sentiwordnet.getStringSentiment("i hate you"));
	}
}