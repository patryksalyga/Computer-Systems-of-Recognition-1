package org.example;

import java.util.ArrayList;
import java.util.List;

public class Texts {
    private List<Text> texts = new ArrayList<Text>();;
    private Dictionaries dictionaries;

    private double lmaxMax;
    private double lmaxMin;
    private double lavgMax;
    private double lavgMin;
    private double sdictMax;
    private double sdictMin;
    private double s7Max;
    private double s7Min;

    public Texts(Dictionaries dictionaries) {
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

    public void deleteTexts(Text text) {
        this.texts.remove(text);
    }

    public List<Text> getTexts() {
        return texts;
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

        //System.out.println("lmaxMax: " + lmaxMax + " lmaxMin: " + lmaxMin + " lavgMax: " + lavgMax + " lavgMin: " + lavgMin + " sdictMax: " + sdictMax + " sdictMin: " + sdictMin + " s7Max: " + s7Max + " s7Min: " + s7Min);

        for (Text text : texts){
            text.normalize(lmaxMax, lmaxMin, lavgMax, lavgMin, sdictMax, sdictMin, s7Max, s7Min);
//            String response = text.toString();
//            System.out.println(response);
        }

//        texts.get(0).createVector();
    }
}
