package org.example;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }


    public String getPlaces() {
        return places;
    }

    public String getBody() {
        return body;
    }

    public void createVector(double ngramTolerance) {
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

            for (String country : dictionaries.getCountries()) {
                if (Metrics.nGrams(word.toLowerCase(), country) >= ngramTolerance) {
                    if (countries.containsKey(country)){
                        countries.put(country, countries.get(country) + 1);
                        wordsFromDictionariesCount++;
                    } else {
                        countries.put(country, 1);
                        wordsFromDictionariesCount++;
                    }
                }
            }

            for (String currency : dictionaries.getCurrencies()) {
                if (Metrics.nGrams(word.toLowerCase(), currency) >= ngramTolerance) {
                    if (currencies.containsKey(currency)){
                        currencies.put(currency, currencies.get(currency) + 1);
                        wordsFromDictionariesCount++;
                    } else {
                        currencies.put(currency, 1);
                        wordsFromDictionariesCount++;
                    }
                }
            }

            for (String person : dictionaries.getPersons()) {
                if (Metrics.nGrams(word.toLowerCase(), person) >= ngramTolerance) {
                    if (persons.containsKey(person)){
                        persons.put(person, persons.get(person) + 1);
                        wordsFromDictionariesCount++;
                    } else {
                        persons.put(person, 1);
                        wordsFromDictionariesCount++;
                    }
                }
            }

            for (String organization : dictionaries.getOrganizations()) {
                if (Metrics.nGrams(word, organization) >= ngramTolerance) {
                    if (organisations.containsKey(organization)){
                        organisations.put(organization, organisations.get(organization) + 1);
                        wordsFromDictionariesCount++;
                    } else {
                        organisations.put(organization, 1);
                        wordsFromDictionariesCount++;
                    }
                }
            }

            for (String exchange : dictionaries.getExchanges()) {
                if (Metrics.nGrams(word.toLowerCase(), exchange) >= ngramTolerance) {
                    if (exchanges.containsKey(exchange)){
                        exchanges.put(exchange, exchanges.get(exchange) + 1);
                        wordsFromDictionariesCount++;
                    } else {
                        exchanges.put(exchange, 1);
                        wordsFromDictionariesCount++;
                    }
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

    public void decide(List<Text> texts, String metric, int k, boolean[] options, boolean reccurent) {
        Map<Text, Double> distances = new HashMap<>();

        for (Text text : texts) {
            double distance = switch (metric) {
                case "euclidean" -> Metrics.euclidean(this, text, options);
                case "manhattan" -> Metrics.manhattan(this, text, options);
                case "czebyszew" -> Metrics.czebyszew(this, text, options);
                default -> throw new IllegalArgumentException("Nieznana metryka: " + metric);
            };
            distances.put(text, distance);
        }

        List<Map.Entry<Text, Double>> sortedDistances = distances.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());

        double kthDistance = sortedDistances.get(k - 1).getValue();

        List<Map.Entry<Text, Double>> nearestNeighbors = sortedDistances.stream()
                .filter(entry -> entry.getValue() <= kthDistance)
                .collect(Collectors.toList());

        Map<String, Integer> votes = new HashMap<>();
        Map<String, Double> distancesSum = new HashMap<>();

        for (Map.Entry<Text, Double> entry : nearestNeighbors) {
            String place = entry.getKey().getPlaces();
            votes.put(place, votes.getOrDefault(place, 0) + 1);
            distancesSum.put(place, distancesSum.getOrDefault(place, 0.0) + entry.getValue());
        }

        System.out.println("Głosy: " + votes);
        System.out.println("Sumy odległości: " + distancesSum);

        int maxVotes = Collections.max(votes.values());

        List<String> topPlaces = votes.entrySet().stream()
                .filter(entry -> entry.getValue() == maxVotes)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (topPlaces.size() == 1) {
            prediction = topPlaces.get(0);
            return;
        }

        double minDistance = Collections.min(distancesSum.values());

        List<String> closestPlaces = distancesSum.entrySet().stream()
                .filter(entry -> entry.getValue() == minDistance)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (closestPlaces.size() == 1) {
            prediction = closestPlaces.get(0);
            return;
        }

        if(reccurent == true){
            decide(texts, metric, k + 1, options, true);
            return;
        }

        if(k>2) {
            decide(texts, metric, k - 1, options, false);
        } else {
            decide(texts, metric, k + 1, options, true);
        }

    }

    public String getPrediction() {
        return prediction;
    }
}