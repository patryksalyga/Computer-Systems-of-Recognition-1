package org.example;

import java.util.*;

public class TrainTexts extends Texts {
    private List<Text> texts = new ArrayList<Text>();

    public TrainTexts(int p, HashMap<String, Integer> countryList, TestTexts testTexts) {
        countryList.forEach((k, v) -> {
            int i = 0;
            Iterator<Text> iterator = testTexts.getTexts().iterator();
            while (iterator.hasNext()) {
                Text text = iterator.next();
                if (text.getPlaces().equals(k)) {
                    if (p * v / 100 > i) {
                        this.texts.add(text);
                        iterator.remove();
                        i++;
                    }
                }
            }
        });
    }

    public void addTexts(Text text) {
        this.texts.add(text);
    }

    public List<Text> getTexts() {
        return texts;
    }

    public void deleteTexts(Text text) {
        this.texts.remove(text);
    }

}
