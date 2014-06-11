package com.vs;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LanguageModel {

    Map<String,Map<String,Integer>> matrix;



    public  LanguageModel() {
        matrix = new LinkedHashMap<String,Map<String,Integer>>();

    }

    public Map<String, Map<String, Integer>> getMatrix() {
        return matrix;
    }


    public void addDocument(Document doc) {
        for (Document.Sentence sentence : doc.getSentences()) {
            List<Document.Token> words = sentence.getWords();
            for (int i = 0;i < words.size()-1; i++) {
                if(matrix.containsKey(words.get(i).getWord()) == false) {
                    matrix.put(words.get(i).getWord(), new LinkedHashMap<String, Integer>());
                    matrix.get(words.get(i).getWord()).put(words.get(i+1).getWord(),1);
                }
                else if (matrix.get(words.get(i).getWord()).containsKey(words.get(i+1).getWord()) == false)  {
                   matrix.get(words.get(i).getWord()).put(words.get(i+1).getWord(),1);
                }
                else {
                    int count = matrix.get(words.get(i).getWord()).get(words.get(i+1).getWord());
                    matrix.get(words.get(i).getWord()).put(words.get(i+1).getWord(),count+1);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Map<String, Integer>> sentence : matrix.entrySet()) {
            String key = sentence.getKey();
            Map<String, Integer> map = sentence.getValue();

            sb.append(System.getProperty("line.separator"));
            sb.append(System.getProperty("line.separator"));
            sb.append("First Key:" + key);
            sb.append(System.getProperty("line.separator"));
            sb.append(System.getProperty("line.separator"));

            for (Map.Entry<String,Integer> token : map.entrySet()) {
                sb.append("Second Key:" + token.getKey() + " Occurence:" + token.getValue());
                sb.append(System.getProperty("line.separator"));


            }
        }
        return sb.toString();
    }

}
