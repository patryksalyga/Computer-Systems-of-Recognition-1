package org.example;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Dictionaries dictionaries = new Dictionaries();
        TestTexts testTexts = new TestTexts(dictionaries);

        ReutersProcessor rp = new ReutersProcessor(testTexts);

        //System.out.println("Liczba przetworzonych artykułów: " + texts.getTexts().size());

        HashMap<String, Integer> countryList = new HashMap<String, Integer>();

        for(Text text : testTexts.getTexts()){
            if ( countryList.containsKey(text.getPlaces())){
                countryList.put(text.getPlaces(), countryList.get(text.getPlaces()) + 1);
            } else {
                countryList.put(text.getPlaces(), 1);
            }
        }

        //System.out.println(countryList);
        //System.out.println(testTexts.getTexts().get(0).getArticle());

        testTexts.createVectors();

        TrainTexts trainTexts = new TrainTexts(20, countryList, testTexts);

        HashMap<String, Integer> testTextCount = new HashMap<String, Integer>();
        HashMap<String, Integer> trainTextCount = new HashMap<String, Integer>();

        for(Text text : testTexts.getTexts()){
            if ( testTextCount.containsKey(text.getPlaces())){
                testTextCount.put(text.getPlaces(), testTextCount.get(text.getPlaces()) + 1);
            } else {
                testTextCount.put(text.getPlaces(), 1);
            }
        }

        for(Text text : trainTexts.getTexts()){
            if ( trainTextCount.containsKey(text.getPlaces())){
                trainTextCount.put(text.getPlaces(), trainTextCount.get(text.getPlaces()) + 1);
            } else {
                trainTextCount.put(text.getPlaces(), 1);
            }
        }

        System.out.println(trainTextCount);
        System.out.println(testTextCount);

//        testTexts.getTexts().get(0).decide(trainTexts.getTexts());
        for(Text text : testTexts.getTexts()){
            text.decide(trainTexts.getTexts());
        }
    }
}