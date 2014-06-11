package com.vs;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;

public class Parsing {
    File path;
    Parser parser;


    public Parsing(String path) {
        parser = new Parser();
        this.path = new File(path);

    }


    public String[] getXMLFileNameArray() {


        FilenameFilter XMLFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (lowercaseName.endsWith(".xml")) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        return path.list(XMLFilter);

    }

    public int getRandomFile(int range) {
        Random random = new Random();
        return random.nextInt(range);
    }


    public LinkedList<String> BuildLanguageModel (LanguageModel lm,LanguageModelPOS lmPOS)
            throws XMLStreamException, IOException, InterruptedException {


        if (path.isDirectory()) {
            String[] directorycontent  = getXMLFileNameArray();
            LinkedList<String> filenames = new LinkedList(Arrays.asList(directorycontent));

            int files_training_set = (int) Math.floor(filenames.size() * 0.9);

            for(int i=0;i<files_training_set;i++) {
                Document document = new Document();
                int RandomIndicie = getRandomFile(filenames.size());
                document = parser.parseDocument(path + "/" + filenames.get(RandomIndicie));
                lm.addDocument(document);
                lmPOS.addDocument(document);
                filenames.remove(RandomIndicie);

            }
            return filenames;
        }
         return  null;

        }












    }

