// source : https://github.com/ipeirotis/ReadabilityMetrics/blob/master/src/main/java/com/ipeirotis/readability/engine/Syllabify.java

package LikePredictor;


	
	
public class  FindSyllable
{

	    static String[] SubSyl = { "cial", "tia", "cius", "cious", "giu", "ion", "iou", "sia$", ".ely$" };
	    static String[] AddSyl = { "ia", "riet", "dien", "iu", "io", "ii", "[aeiouym]bl$", "[aeiou]{3}", "^mc", "ism$", "[^aeiouy][^aeiouy]l$", "[^l]lien","^coa[dglx].", "[^gq]ua[^auieo]", "dnt$" };

	    public static int getNoOfSyllables(String word) {

	        word = word.toLowerCase();
	        word = word.replaceAll("'", " ");
	        
	        if (word.equals("i")) 
	        	{ return 1; }
	        if (word.equals("a")) 
	        	{ return 1; }
	        
	        if (word.endsWith("e")) 
	        {
	            word = word.substring(0, word.length() - 1);
	        }

	        String[] vowelstring = word.split("[^aeiouy]+");

	        int ans = 0;
	        for (int i = 0; i < SubSyl.length; i++) {
	            String syll = SubSyl[i];
	            if (word.matches(syll)) {
	                ans--;
	            }
	        }
	        for (int i = 0; i < AddSyl.length; i++) {
	            String syll = AddSyl[i];
	            if (word.matches(syll)) {
	                ans++;
	            }
	        }
	        if (word.length() == 1) {
	            ans++;
	        }

	        for (int i = 0; i < vowelstring.length; i++) {
	            if (vowelstring[i].length() > 0)
	                ans++;
	        }

	        if (ans == 0) {
	            ans = 1;
	        }

	        return ans;
	    }

	    

	
}

