package org.example;

import java.util.*;

import static java.lang.Double.NaN;

public class Text {
    private String places;
    private String prediction;
    private String body;
    private Dictionaries dictionaries;

    private String pmax = null;
    private String wmax = null;
    private String omax = null;
    private String imax = null;
    private String gmax = null;
    private boolean fkraj = false;
    private double lmax;
    private double lavg;
    private double sdict;
    private double s7;

    public Text(String places, String body, Dictionaries dictionaries) {
        this.places = places;
        this.body = body;
        this.dictionaries = dictionaries;
    }

    public String getPlaces() {
        return places;
    }

    public String getBody() {
        return body;
    }

    public void createVector() {
        String[] words = body.replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&#\\d+;", "")
                .replaceAll("<[^>]*>", "")
                .replaceAll("[^\\p{L}\\p{N}/\\-\\s]", "")
                .split("\\s+");

        String[] firstSentenceWords = body.replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&#\\d+;", "")
                .replaceAll("<[^>]*>", "")
                .split("\\.")[0]
                .replaceAll("[^\\p{L}\\p{N}/\\-\\s]", "")
                .split("\\s+");

        Map<String, Integer> countries = new LinkedHashMap<String, Integer>();
        Map<String, Integer> currencies = new LinkedHashMap<String, Integer>();
        Map<String, Integer> persons = new LinkedHashMap<String, Integer>();
        Map<String, Integer> organisations = new LinkedHashMap<String, Integer>();
        Map<String, Integer> exchanges = new LinkedHashMap<String, Integer>();
        float upperCaseWordsCount = 0;
        float upperCaseWordsLength = 0;
        int longestUpperCaseWordLength = 0;
        int longWordsCount = 0;
        int wordsFromDictionariesCount = 0;

        for(String word : firstSentenceWords){
            if(dictionaries.getCountries().contains(word.toLowerCase())){
                fkraj = true;
            }
        }

        //System.out.println("----");

        for (String word : words) {
            //System.out.println(word);

            if(word.length() == 0){
                continue;
            }

            if(dictionaries.getCountries().contains(word.toLowerCase())){
                if (countries.containsKey(word)){
                    countries.put(word, countries.get(word) + 1);
                    wordsFromDictionariesCount++;
                } else {
                    countries.put(word, 1);
                    wordsFromDictionariesCount++;
                }
            }

            if(dictionaries.getCurrencies().contains(word.toLowerCase())){
                if (currencies.containsKey(word)){
                    currencies.put(word, currencies.get(word) + 1);
                    wordsFromDictionariesCount++;
                } else {
                    currencies.put(word, 1);
                    wordsFromDictionariesCount++;
                }
            }

            if(dictionaries.getPersons().contains(word.toLowerCase())){
                if (persons.containsKey(word)){
                    persons.put(word, persons.get(word) + 1);
                    wordsFromDictionariesCount++;
                } else {
                    persons.put(word, 1);
                    wordsFromDictionariesCount++;
                }
            }

            if(dictionaries.getOrganizations().contains(word.toLowerCase())){
                if (organisations.containsKey(word)){
                    organisations.put(word, organisations.get(word) + 1);
                    wordsFromDictionariesCount++;
                } else {
                    organisations.put(word, 1);
                    wordsFromDictionariesCount++;
                }
            }

            if(dictionaries.getExchanges().contains(word.toLowerCase())){
                if (exchanges.containsKey(word)){
                    exchanges.put(word, exchanges.get(word) + 1);
                    wordsFromDictionariesCount++;
                } else {
                    exchanges.put(word, 1);
                    wordsFromDictionariesCount++;
                }
            }

            if(Character.isUpperCase(word.charAt(0))){
                upperCaseWordsCount++;
                upperCaseWordsLength += word.length();

                if(word.length() > longestUpperCaseWordLength){
                    longestUpperCaseWordLength = word.length();
                }
            }

            if(word.length() >= 7){
                longWordsCount++;
            }
        }

        lmax = longestUpperCaseWordLength;
        lavg = upperCaseWordsLength/upperCaseWordsCount;
        s7 = longWordsCount;
        sdict = wordsFromDictionariesCount;

        if(countries.size() > 0){
            int maxValue = countries.values().stream()
                    .max(Integer::compareTo)
                    .get();

            pmax = countries.entrySet().stream()
                    .filter(entry -> entry.getValue() == maxValue)
                    .findFirst()
                    .get()
                    .getKey();
        }
        if(currencies.size() > 0){
            int maxValue = currencies.values().stream()
                    .max(Integer::compareTo)
                    .get();

            wmax = currencies.entrySet().stream()
                    .filter(entry -> entry.getValue() == maxValue)
                    .findFirst()
                    .get()
                    .getKey();
        }
        if(persons.size() > 0){
            int maxValue = persons.values().stream()
                    .max(Integer::compareTo)
                    .get();

            omax = persons.entrySet().stream()
                    .filter(entry -> entry.getValue() == maxValue)
                    .findFirst()
                    .get()
                    .getKey();
        }
        if(organisations.size() > 0){
            int maxValue = organisations.values().stream()
                    .max(Integer::compareTo)
                    .get();

            imax = organisations.entrySet().stream()
                    .filter(entry -> entry.getValue() == maxValue)
                    .findFirst()
                    .get()
                    .getKey();
        }
        if(exchanges.size() > 0){
            int maxValue = exchanges.values().stream()
                    .max(Integer::compareTo)
                    .get();

            gmax = exchanges.entrySet().stream()
                    .filter(entry -> entry.getValue() == maxValue)
                    .findFirst()
                    .get()
                    .getKey();
        }
    }

    public String getPmax() {
        return pmax;
    }

    public String getWmax() {
        return wmax;
    }

    public String getOmax() {
        return omax;
    }

    public String getImax() {
        return imax;
    }

    public String getGmax() {
        return gmax;
    }

    public boolean isFkraj() {
        return fkraj;
    }

    public double getLmax() {
        return lmax;
    }

    public double getLavg() {
        return lavg;
    }

    public double getSdict() {
        return sdict;
    }

    public double getS7() {
        return s7;
    }

    @Override
    public String toString() {
        return "Text{" +
                "pmax='" + pmax + '\'' +
                ", wmax='" + wmax + '\'' +
                ", omax='" + omax + '\'' +
                ", imax='" + imax + '\'' +
                ", gmax='" + gmax + '\'' +
                ", fkraj=" + fkraj +
                ", lmax=" + lmax +
                ", lavg=" + lavg +
                ", sdict=" + sdict +
                ", s7=" + s7 +
                '}';
    }

    public void normalize(double lmaxMax, double lmaxMin, double lavgMax, double lavgMin, double sdictMax, double sdictMin, double s7Max, double s7Min) {
        lmax = (lmax - lmaxMin) / (lmaxMax - lmaxMin);
        lavg = (lavg - lavgMin) / (lavgMax - lavgMin);
        sdict = (sdict - sdictMin) / (sdictMax - sdictMin);
        s7 = (s7 - s7Min) / (s7Max - s7Min);
    }

    public void decide(List<Text> texts) {
        for(Text text : texts){
            //Zliczanie odleglosci do kazdego tekstu
//            System.out.println(text.toString());
//            if(text.toString().equals("Text{pmax='null', wmax='null', omax='null', imax='null', gmax='null', fkraj=false, lmax=0.0, lavg=NaN, sdict=0.0, s7=0.0}")){
//                System.out.println(text.toString());
//                System.out.println(text.getBody());
//                System.out.println(text.getPlaces());
//            }
//            if (Double.isNaN(Metrics.euclidean(this, text))){
//                System.out.println(Metrics.euclidean(this, text) + "_____" + this.toString() + "------ " + text.toString());
//            }
            System.out.println(Metrics.euclidean(this, text));
        }
        //Decyzja
    }
}