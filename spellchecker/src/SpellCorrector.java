import java.util.Map;

public class SpellCorrector {
    final private CorpusReader cr;
    final private ConfusionMatrixReader cmr;
    
    public SpellCorrector(CorpusReader cr, ConfusionMatrixReader cmr) 
    {
        this.cr = cr;
        this.cmr = cmr;
    }
    
    public String correctPhrase(String phrase)
    {
        if(phrase == null || phrase.length() == 0)
        {
            throw new IllegalArgumentException("phrase must be non-empty.");
        }
            
        String[] words = phrase.split(" ");
        String finalSuggestion = "";
        
        /** CODE TO BE ADDED **/
        
        //Debug prints
//        for (String word : words){
//        	System.out.println(word + ":");
//        	Map<String, Double> candidates = getCandidateWords(word);
//        	System.out.println(candidates);
//        }
        
        /*
         * loops over all words in the sentence and checks for words not in the vocabulary
         * If it finds such a word, the most likely typo is taken and the new word is inserted in the sentence.
         * TODO improve this.
         */
        for (String word : words){
        	if (!cr.inVocabulary(word)){
        		Map<String, Double> candidates = getCandidateWords(word);
        		String bestCandidate = "";
        		double bestWeight = -1;
        		for (Map.Entry<String, Double> entry : candidates.entrySet()){
        			if (entry.getValue() > bestWeight){
        				bestWeight = entry.getValue();
        				bestCandidate = entry.getKey();
        			}
        		}
        		word = bestCandidate;
        	}
        	finalSuggestion += word + " ";
        }
        
        return finalSuggestion.trim();
    }    
      
    /** returns a map with candidate words and their noisy channel probability. **/
    public Map<String,Double> getCandidateWords(String typo)
    {
        return new WordGenerator(cr,cmr).getCandidateCorrections(typo);
    }            
}