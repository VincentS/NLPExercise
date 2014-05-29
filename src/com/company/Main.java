package com.company;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.URL;

public class Main {

    public static void main(String[] args) {



        Document D = new Document();
        Parser parser = new Parser();
        String filename = "/Users/Vincent/git/nlp-exercise1/data/1279685.xml";


        try {
            D = parser.parseDocument(filename);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println(D.getSentences());
    }
}
