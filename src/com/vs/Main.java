package com.vs;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    static String basepath = "/Users/Vincent/git/nlp-exercise1/data/";

    public static void main(String[] args) throws InterruptedException, XMLStreamException, IOException {
        LanguageModel lm = new LanguageModel();
        LinkedList<String> filenames = new LinkedList<String>();

        Parsing parser = new Parsing(basepath);
        filenames =    parser.BuildLanguageModel(lm);
        CalculatePerplexity calc = new CalculatePerplexity(lm);
        calc.getCorpusComplexity(basepath,filenames);



    }

}
