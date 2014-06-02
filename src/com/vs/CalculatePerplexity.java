package com.vs;

import java.util.List;
import static com.vs.Document.*;


public class CalculatePerplexity {
    LanguageModel ml;

    public CalculatePerplexity(LanguageModel ml) {
        this.ml = ml;

    }







    public double getsmoothedBigramProbability (Token word1,Token word2) {

        double occ_bigram = 0.0;
        double occ_unigram = 0.0;

        if(ml.getMatrix().containsKey(word1.getWord())){

          if(ml.getMatrix().get(word1.getWord()).containsKey(word2.getWord())) {

              occ_bigram = ml.getMatrix().get(word1.getWord()).get(word2.getWord()).doubleValue()+1.0;
          }

          else {

              occ_bigram = 1.0;

          }

          for (double f : ml.getMatrix().get(word1.getWord()).values()) {

              occ_unigram += f;

          }

          double occ_unigram_smoothed = occ_unigram + ml.getMatrix().get(word1.getWord()).size();

          return occ_bigram/occ_unigram_smoothed;
        }

        else {

            return 1.0;

        }

    }




    public double getDocumentPerplexity(Document document) {
        double document_perplexity = 1;

        for (Document.Sentence sentence : document.getSentences()) {

            List<Document.Token> words = sentence.getWords();
            double sentence_perplexity = 1.0;

            for (int i = 0;i < words.size()-1; i++) {

                double bigram_prob = 1.0/getsmoothedBigramProbability(words.get(i),words.get(i+1));
                sentence_perplexity = sentence_perplexity * bigram_prob;

            }

            sentence_perplexity = Math.pow(sentence_perplexity,(-1/words.size()));
            document_perplexity = document_perplexity * sentence_perplexity;

        }

        return document_perplexity/document.getSentences().size();
    }





}


