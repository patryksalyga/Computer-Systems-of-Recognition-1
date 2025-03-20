package org.example;

import java.util.ArrayList;
import java.util.List;

public class Texts {
    private int maxLongWords;
    private int longestWord;

    private List<Text> texts = new ArrayList<Text>();
    private Dictionaries dictionaries;

    public Texts(Dictionaries dictionaries) {
        this.dictionaries = dictionaries;
    }

    public void addTexts(Text text) {
        this.texts.add(text);
    }

    public List<Text> getTexts() {
        return texts;
    }

    public Dictionaries getDictionaries() {
        return dictionaries;
    }

    public void createVectors(){
        for(Text text : texts){
            text.createVector();
            System.out.println(text.toString());
        }
//        texts.get(0).createVector();
    }
}
