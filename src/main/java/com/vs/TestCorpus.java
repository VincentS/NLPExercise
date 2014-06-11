package com.vs;


import java.util.LinkedList;
import java.util.List;

public class TestCorpus {

    private String id;
    private List<Sentence> sentences;

    public TestCorpus() {
        sentences = new LinkedList<Sentence>();

    }

    public List<Sentence> getSentences() {
        return sentences;
    }


    public void add(Sentence sentences) {
        this.sentences.add(sentences);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

        @Override
        public int hashCode() { return pos.hashCode(); }


        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            if (!(o instanceof Token)) return false;
            Token token = (Token) o;
            return this.pos.equals(token.getPOS());
        }
    }



}
