package org.example;

import java.util.ArrayList;
import java.util.List;

public class TestTexts extends Texts {
    private double lmaxMax;
    private double lmaxMin;
    private double lavgMax;
    private double lavgMin;
    private double sdictMax;
    private double sdictMin;
    private double s7Max;
    private double s7Min;

    private List<Text> texts = new ArrayList<Text>();
    private Dictionaries dictionaries;

    public TestTexts(Dictionaries dictionaries) {
        this.dictionaries = dictionaries;
        lmaxMin = Integer.MAX_VALUE;
        lavgMin = Integer.MAX_VALUE;
        sdictMin = Integer.MAX_VALUE;
        s7Min = Integer.MAX_VALUE;
        lmaxMax = Integer.MIN_VALUE;
        lavgMax = Integer.MIN_VALUE;
        sdictMax = Integer.MIN_VALUE;
        s7Max = Integer.MIN_VALUE;
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

    public Dictionaries getDictionaries() {
        return dictionaries;
    }

    public void createVectors(double ngramTolerance){
        for(Text text : texts){
            text.createVector(ngramTolerance);
            //System.out.println(text.toString());

            if(text.getLmax() > lmaxMax){
                lmaxMax = text.getLmax();
            }
            if(text.getLmax() < lmaxMin){
                lmaxMin = text.getLmax();
            }
            if(text.getLavg() > lavgMax){
                lavgMax = text.getLavg();
            }
            if(text.getLavg() < lavgMin){
                lavgMin = text.getLavg();
            }
            if(text.getSdict() > sdictMax){
                sdictMax = text.getSdict();
            }
            if(text.getSdict() < sdictMin){
                sdictMin = text.getSdict();
            }
            if(text.getS7() > s7Max){
                s7Max = text.getS7();
            }
            if(text.getS7() < s7Min){
                s7Min = text.getS7();
            }

        }

        System.out.println("lmaxMax: " + lmaxMax + " lmaxMin: " + lmaxMin + " lavgMax: " + lavgMax + " lavgMin: " + lavgMin + " sdictMax: " + sdictMax + " sdictMin: " + sdictMin + " s7Max: " + s7Max + " s7Min: " + s7Min);

        for (Text text : texts){
            text.normalize(lmaxMax, lmaxMin, lavgMax, lavgMin, sdictMax, sdictMin, s7Max, s7Min);
//            String response = text.toString();
//            System.out.println(response);
        }

//        texts.get(0).createVector();
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
        System.out.println("-----------------");
        System.out.println("Correct: " + correct + " Incorrect: " + incorrect);
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
        return (double) correct / (correct + incorrect);
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
        return (double) correct / (correct + incorrect);
    }

    public double rateF1Score(double precision, double recall) {
        return 2 * (precision * recall) / (precision + recall);
    }
}
