package com.vs;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;
import static com.vs.Document.*;


public class CalculatePerplexity {
    LanguageModel ml;
    int wordcount = 0;

    public CalculatePerplexity(LanguageModel ml) {
        this.ml = ml;

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
        double document_perplexity = 0.0;
        int wordcountdoc = 0;


        for (Document.Sentence sentence : document.getSentences()) {

            List<Document.Token> words = sentence.getWords();
            double sentence_perplexity = 0.0;

            for (int i = 0; i < words.size()-1; i++) {

                double bigram_prob = getsmoothedBigramProbability(words.get(i), words.get(i + 1));
                sentence_perplexity += (Math.log(bigram_prob)/Math.log(2));



            }
            System.out.print("Sentence perpelxity:" + Math.pow(2.0,-(sentence_perplexity/words.size())));
            System.out.print(System.getProperty("line.separator"));
            document_perplexity += sentence_perplexity;
            wordcountdoc += words.size();
            wordcount += words.size();

        }
        System.out.print("Document Perplexity:" + Math.pow(2.0,-document_perplexity/wordcountdoc));
        return document_perplexity;

    }


    public void getCorpusComplexity(String basepath, List<String> filenames)
            throws InterruptedException, XMLStreamException, IOException {
        Parser documentparser = new Parser();
        double overall_complexity = 0.0;
        for (int i = 0; i < filenames.size(); i++) {
            Document document = new Document();
            document = documentparser.parseDocument(basepath + filenames.get(i));

            overall_complexity += getDocumentPerplexity(document);


        }
        //overall_complexity= Math.pow(overall_complexity,(1.0/wordcount));
        overall_complexity = overall_complexity/wordcount;
        overall_complexity = Math.pow(2.0, -overall_complexity);
        System.out.print(overall_complexity);
    }

}




