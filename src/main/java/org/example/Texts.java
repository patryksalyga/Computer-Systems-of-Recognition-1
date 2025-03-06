package org.example;

import java.util.ArrayList;
import java.util.List;

public class Texts {
    private List<Text> texts = new ArrayList<Text>();

    public void addTexts(Text text) {
        this.texts.add(text);
    }

    public List<Text> getTexts() {
        return texts;
    }
}
