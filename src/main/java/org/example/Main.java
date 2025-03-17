package org.example;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Texts texts = new Texts();

        ReutersProcessor rp = new ReutersProcessor(texts);

        System.out.println("Liczba przetworzonych artykułów: " + texts.getTexts().size());

        HashMap<String, Integer> countryList = new HashMap<String, Integer>();

        for(Text text : texts.getTexts()){
            if ( countryList.containsKey(text.getPlaces())){
                countryList.put(text.getPlaces(), countryList.get(text.getPlaces()) + 1);
            } else {
                countryList.put(text.getPlaces(), 1);
            }
        }

        System.out.println(countryList);
    }
}