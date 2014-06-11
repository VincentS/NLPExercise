package com.vs;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    static String basepath = "/Users/Vincent/git/nlp-exercise1/data/";

    public static void main(String[] args) throws InterruptedException, XMLStreamException, IOException {
        LanguageModel lm = new LanguageModel();
        LanguageModelPOS lmPOS = new LanguageModelPOS();
        LinkedList<String> filenames = new LinkedList<String>();


        Parsing parser = new Parsing(basepath);
        filenames =    parser.BuildLanguageModel(lm,lmPOS);


        CalculatePerplexity calc = new CalculatePerplexity(lm);
        //CalculatePrecision calcprec = new CalculatePrecision(lmPOS);
        //calcprec.getPrecision(basepath,filenames);
        calc.getCorpusComplexity(basepath,filenames);



    }




    public static void  ComputePerplexity(List<String> filenames,CalculatePerplexity calc)
            throws InterruptedException, XMLStreamException, IOException {
        Parser documentparser = new Parser();
        double overall_complexity = 0;
        for(int i =0;i<filenames.size();i++) {
            Document document = new Document();
            document = documentparser.parseDocument(basepath + filenames.get(i));

            overall_complexity += calc.getDocumentPerplexity(document);
            System.out.print(calc.getDocumentPerplexity(document));
            System.out.print(System.getProperty("line.separator"));


        }
        System.out.print(overall_complexity);
    }
}
