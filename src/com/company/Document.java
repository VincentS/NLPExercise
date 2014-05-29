package com.company;


import java.util.LinkedList;
import java.util.List;

public class Document {
    private int id;
    private List<Sentence> sentences;

    public Document() {
        sentences = new LinkedList<Sentence>();



    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public void addSentence(Sentence sentences) {
        this.sentences.add(sentences);
    }


    public static class Sentence {

        private List<Token> words;


        public Sentence() {
        words = new LinkedList<Token>();
        }


        public List<Token> getWords() {
            return words;
        }

        public void add(Token token) {
            this.words.add(token);

        }

        public void setWords(List<Token> words) {
            this.words = words;
        }

        @Override
        public String toString() {
            return words.toString();
        }



    }


    public static class Token {
        private final String word;
        private final String pos;

        public Token(String word, String pos) {
            this.word = word;
            this.pos = pos;
        }

        public String getWord() {
            return word;
        }

        public String getPOS() {
            return pos;
        }

        @Override
        public String toString() {
            return (word + ";" + pos);
        }
    }



}
