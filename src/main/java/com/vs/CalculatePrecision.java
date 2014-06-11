package com.vs;


import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CalculatePrecision {
    LanguageModelPOS lmPOS;
    int tagCount = 0;
    int correctTags = 0;

    public CalculatePrecision(LanguageModelPOS lmPOS) {
        this.lmPOS = lmPOS;

    }


    public String guessTags (String previousTAG,Document.Token token) {

        double occ_bigram = 0.0;
        double occ_previoustag = 0.0;
        double occ_currenttag=0.0;
        String actualGuess = "NN";
        double highestProbaility = 0.0;
        double prop_result = 0.0;



        //Calculate Unigram
        if (lmPOS.getMatrix().get(new Document.Token(null,previousTAG)) != null) {


            for (double f : lmPOS.getMatrix().get(new Document.Token(null,previousTAG)).values()) {

                occ_previoustag += f;

            }

        }

        //Looping through all possible previous tag + x combinations
        for(Map.Entry<Document.Token, Integer> entry :
                lmPOS.getMatrix().get(new Document.Token(null,previousTAG)).entrySet())   {
            prop_result = 0.0;
            occ_currenttag = 0.0;

            for (double f : lmPOS.getMatrix().get(new Document.Token(null,entry.getKey().getPOS())).values()) {

                occ_currenttag += f;

            }

            for (Map.Entry<Document.Token, Integer> findwordentry :
                    lmPOS.getMatrix().get(new Document.Token(null,entry.getKey().getPOS())).entrySet()) {

                if(token.getWord() == findwordentry.getKey().getWord()) {
                    prop_result = (entry.getValue().doubleValue()/occ_previoustag)* (entry.getValue().doubleValue()/occ_currenttag) ;

                    if(prop_result>highestProbaility && Double.isInfinite(prop_result) == false) {
                        highestProbaility = prop_result;
                        actualGuess = entry.getKey().getPOS();

                        }
                    }

            }
        }


        return actualGuess;
     }




    public void guessCorrectTags(Document document) {


        for (Document.Sentence sentence : document.getSentences()) {

            List<Document.Token> words = sentence.getWords();
            String GuessedTag = null;
            for (int i = 0; i < words.size()-1; i++) {

                if(i==0) {
                    GuessedTag = guessTags("NN",words.get(i));
                }
                else {
                    GuessedTag = guessTags(GuessedTag, words.get(i));
                }
                if(words.get(i).getPOS() == GuessedTag) {
                    correctTags += 1;
                }
                tagCount +=1;


            }



        }


    }



    public void getPrecision(String basepath, List<String> filenames)
            throws InterruptedException, XMLStreamException, IOException {
        Parser documentparser = new Parser();
        double overall_complexity = 0;
        for (int i = 0; i < filenames.size(); i++) {
            Document document = new Document();
            document = documentparser.parseDocument(basepath + filenames.get(i));

            guessCorrectTags(document);


        }
        int precision = correctTags / (tagCount +correctTags);
        System.out.print("Precision:" + precision);
    }
}
