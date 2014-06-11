package com.vs;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;
import static com.vs.Document.*;

/*
* Used following formula
*   { 2 }^{ -\frac { 1 }{ M } \sum _{ i=1 }^{ m }{ \log _{ 2 }{ p({ x }_{ i }) }  }  }
*
*   M = number of words
*   m = number of sentences
*   p({ x }_{ i }) = probability of sentence
*
*   Source: http://www.cs.columbia.edu/~mcollins/lm-spring2013.pdf
*
*
 */
public class CalculatePerplexity {
    LanguageModel ml;
    Document testCorpus;
    int wordcount = 0;

    public CalculatePerplexity(LanguageModel ml) {
        this.ml = ml;
        this.testCorpus = new Document();

    }


    public double getfirstwordProbability(Token word1) {

        double occ_unigram = 0.0;
        double occ_unigram_smoothed = 0.0;
        double occ_enumerator = 0.0;



        //Calculate Unigram
        if (ml.getMatrix().get(word1.getWord()) != null) {


            for (double f : ml.getMatrix().get(word1.getWord()).values()) {

                occ_unigram += f;

            }

            occ_unigram_smoothed = occ_unigram + 1;
        } else {
            occ_unigram_smoothed = 1.0;
        }

        occ_enumerator = testCorpus.getSentences().size() + ml.getMatrix().size();



        return occ_unigram_smoothed / occ_enumerator;
    }


    public double getsmoothedBigramProbability(Token word1, Token word2) {

        double occ_bigram = 0.0;
        double occ_unigram = 0.0;
        double occ_unigram_smoothed = 0.0;
        //Calculate Bigram
        if (ml.getMatrix().get(word1.getWord()) != null && ml.getMatrix().get(word1.getWord()).get(word2.getWord()) != null) {
            occ_bigram = ml.getMatrix().get(word1.getWord()).get(word2.getWord()).doubleValue() + 1.0;
        } else {
            occ_bigram = 1.0;
        }


        //Calculate Unigram
        if (ml.getMatrix().get(word1.getWord()) != null) {


            for (double f : ml.getMatrix().get(word1.getWord()).values()) {

                occ_unigram += f;

            }

            occ_unigram_smoothed = occ_unigram + ml.getMatrix().size();
        } else {
            occ_unigram_smoothed = ml.getMatrix().size();
        }

        return occ_bigram / occ_unigram_smoothed;
    }


    public double getDocumentPerplexity(Document document) {
        double corpus_perplexity = 0.0;


        for (Document.Sentence sentence : document.getSentences()) {

            List<Document.Token> words = sentence.getWords();
            double sentence_perplexity = 0.0;

            for (int i = 0; i < words.size(); i++) {
                double bigram_prob = 0.0;
                if(i == 0) {

                    bigram_prob = getfirstwordProbability(words.get(i));
                }
                else {

                    bigram_prob = getsmoothedBigramProbability(words.get(i-1), words.get(i));
                }


                sentence_perplexity += (Math.log(bigram_prob) / Math.log(2));

            }

            corpus_perplexity += sentence_perplexity;
            wordcount += words.size();

        }

        return corpus_perplexity;

    }

    //Neccesarry to have all objects in one document to get # sentences for Probability of first sentence
    public void buildTestCorpus(Document document) {
        for (Document.Sentence sentence : document.getSentences()) {
                testCorpus.add(sentence);

        }


    }

    public void getCorpusComplexity(String basepath, List<String> filenames)
            throws InterruptedException, XMLStreamException, IOException {
        Parser documentparser = new Parser();
        double overall_complexity = 0;
        for (int i = 0; i < filenames.size(); i++) {
            Document document = new Document();
            document = documentparser.parseDocument(basepath + filenames.get(i));
            buildTestCorpus(document);




        }
        overall_complexity = getDocumentPerplexity(testCorpus);
        overall_complexity = overall_complexity/wordcount;
        overall_complexity = Math.pow(2.0, -overall_complexity);
        System.out.print(overall_complexity);
    }

}




