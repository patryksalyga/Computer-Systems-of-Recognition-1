package org.example;

import java.util.List;

public abstract class Texts {
    private List<Text> texts;

    public void addTexts(Text text) {
        this.texts.add(text);
    }

    public void deleteTexts(Text text) {
        this.texts.remove(text);
    }

    public List<Text> getTexts() {
        return texts;
    }


}
