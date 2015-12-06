package LikePredictor;


import javax.swing.text.Utilities;

public class FogIndexCalculator
{
    Integer sentences;
    Integer complex;
    Integer words;
    Integer syllables;
   
    public Integer getComplex() {
        return complex;
    }

    public Integer getSentences() {
        return sentences;
    }

    public Integer getSyllables() {
        return syllables;
    }

    public Integer getWords() {
        return words;
    }

    public FogIndexCalculator(String text) {
        
        this.sentences = getNumberOfSentences(text);
        this.complex = getNumberOfComplexWords(text);
        this.words = getNumberOfWords(text);
        this.syllables = getNumberOfSyllables(text);
       
        
    }     
        
      
    
    private static boolean isComplex(String w) {
        int syllables = FindSyllable.getNoOfSyllables(w);
        return (syllables > 2);
    }
    
   
    private static Integer getNumberOfComplexWords(String text) {
      
        String[] words  = text.split("[.?!]+");
        int complex = 0;
        for (String w : words) {
            if (isComplex(w)) complex++;
        }
        return complex;
    }
    
    private static Integer getNumberOfWords(String text) {
       
        String[] word  = text.split("[ ]");
        
        
        return word.length;
    }

   
    private static Integer getNumberOfSyllables(String text) {
        
        String[] word  = text.split(" ");
        int syllables = 0;
        for (String w : word) {
            if (w.length()>0) {
                syllables += FindSyllable.getNoOfSyllables(w);
            }
        }
        return syllables;
    }
    
    private static Integer getNumberOfSentences(String text) {

    	String delims = "[ .?!]+";
    	String[] para = text.split(delims);
    	
    	return para.length;
    	
    	
    	
    }
    
    public Double getFogIndex()
	{
		
        double score = 0.4 * (words/sentences + 100 * complex/words);
        return score;
    }
    
    public static void main(String[] args) {
    	FogIndexCalculator c = new FogIndexCalculator("hello i am a boy");
    	System.out.println(c.getFogIndex());
    
    }

}
