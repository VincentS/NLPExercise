package com.vs;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LanguageModelPOS {

    Map<Document.Token,Map<Document.Token,Integer>> matrix;



    public LanguageModelPOS() {
        matrix = new LinkedHashMap<Document.Token,Map<Document.Token,Integer>>();

    }

    public Map<Document.Token, Map<Document.Token, Integer>> getMatrix() {
        return matrix;
    }


    public void addDocument(Document doc) {
        for (Document.Sentence sentence : doc.getSentences()) {
            List<Document.Token> words = sentence.getWords();
            for (int i = 0;i < words.size()-1; i++) {
                if(matrix.get(new Document.Token(null,words.get(i).getPOS())) == null) {
                    matrix.put(words.get(i), new LinkedHashMap<Document.Token, Integer>());
                    String test = words.get(i).getPOS();
                    matrix.get(new Document.Token(null,words.get(i).getPOS())).put(words.get(i + 1), 1);
                }
                else if (matrix.get(new Document.Token(null,words.get(i).getPOS())).get(new Document.Token(null,words.get(i + 1).getPOS())) == null)  {
                   matrix.get(new Document.Token(null,words.get(i).getPOS())).put(words.get(i+1),1);
                }
                else {
                    int count = matrix.get(new Document.Token(null,words.get(i).getPOS())).get(new Document.Token(null,words.get(i + 1).getPOS()));
                    matrix.get(new Document.Token(null,words.get(i).getPOS())).put(words.get(i+1),count+1);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Document.Token, Map<Document.Token, Integer>> sentence : matrix.entrySet()) {
            String key = sentence.getKey().getWord();
            Map<Document.Token, Integer> map = sentence.getValue();

            sb.append(System.getProperty("line.separator"));
            sb.append(System.getProperty("line.separator"));
            sb.append("First Key:" + key);
            sb.append(System.getProperty("line.separator"));
            sb.append(System.getProperty("line.separator"));

            for (Map.Entry<Document.Token,Integer> token : map.entrySet()) {
                sb.append("Second Key:" + token.getKey() + " Occurence:" + token.getValue());
                sb.append(System.getProperty("line.separator"));


            }
        }
        return sb.toString();
    }



}
