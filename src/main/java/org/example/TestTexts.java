package org.example;

import java.util.ArrayList;
import java.util.List;

public class TestTexts {
    private List<Text> texts = new ArrayList<Text>();

    public void addTexts(Text text) {
        this.texts.add(text);
    }

    public List<Text> getTexts() {
        return texts;
    }

    public void deleteTexts(Text text) {
        this.texts.remove(text);
    }

    public double rateAccuracy() {
        int correct = 0;
        int incorrect = 0;
        for (Text text : texts) {
            if (text.getPlaces().equals(text.getPrediction())) {
                correct++;
            } else {
                incorrect++;
            }
        }
//        System.out.println("-----------------");
//        System.out.println("Correct: " + correct + " Incorrect: " + incorrect);
        return (double) correct / (correct + incorrect);
    }

    public double ratePrecision(String place) {
        int correct = 0;
        int incorrect = 0;
        for (Text text : texts) {
            if (text.getPlaces().equals(place) && text.getPrediction().equals(place)) {
                correct++;
            } else if (!text.getPlaces().equals(place) && text.getPrediction().equals(place)) {
                incorrect++;
            }
        }
        return (correct + incorrect) == 0 ? 0.0 : (double) correct / (correct + incorrect);
    }

    public double rateRecall(String place) {
        int correct = 0;
        int incorrect = 0;
        for (Text text : texts) {
            if (text.getPlaces().equals(place) && text.getPrediction().equals(place)) {
                correct++;
            } else if (text.getPlaces().equals(place) && !text.getPrediction().equals(place)) {
                incorrect++;
            }
        }
        return (correct + incorrect) == 0 ? 0.0 : (double) correct / (correct + incorrect);
    }

    public double rateF1Score(double precision, double recall) {
        return (precision + recall) == 0 ? 0.0 : 2 * (precision * recall) / (precision + recall);
    }
}
