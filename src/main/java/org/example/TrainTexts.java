package org.example;

import java.util.*;

public class TrainTexts {
    private List<Text> texts = new ArrayList<Text>();

    public TrainTexts(int trainingSetProportion, HashMap<String, Integer> countryList, TestTexts testTexts, Texts listTexts) {
        countryList.forEach((k, v) -> {
            int i = 0;
            Iterator<Text> iterator = listTexts.getTexts().iterator();
            while (iterator.hasNext()) {
                Text text = iterator.next();
                text.setPrediction(null);
                if (text.getPlaces().equals(k)) {
                    if (trainingSetProportion * v / 100 > i) {
                        this.texts.add(text);
                        i++;
                    } else {
                        testTexts.addTexts(text);
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
