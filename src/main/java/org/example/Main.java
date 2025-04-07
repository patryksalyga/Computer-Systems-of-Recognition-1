package org.example;

import java.util.*;

public class Main {

    public static HashMap<Integer, Double> testAccuracyForDifferentK(TestTexts testTexts, TrainTexts trainTexts, String metric) {
        int[] kValues = {17};
        HashMap<Integer, Double> accuracyResults = new HashMap<>();

        for (int k : kValues) {
            // Czyść predykcje
            for (Text text : testTexts.getTexts()) {
                text.setPrediction(null);
            }

            // Przeprowadź klasyfikację
            for (Text text : testTexts.getTexts()) {
                text.decide(trainTexts.getTexts(), metric, k, false);
            }

            // Oblicz accuracy
            double accuracy = testTexts.rateAccuracy();
            accuracyResults.put(k, accuracy);
        }

        return accuracyResults;
    }

    public static void main(String[] args) {
        Dictionaries dictionaries = new Dictionaries();
        TestTexts testTexts = new TestTexts(dictionaries);

        ReutersProcessor rp = new ReutersProcessor(testTexts);

        HashMap<String, Integer> countryList = new HashMap<>();
        for (Text text : testTexts.getTexts()) {
            countryList.merge(text.getPlaces(), 1, Integer::sum);
        }

        testTexts.createVectors();
        TrainTexts trainTexts = new TrainTexts(60, countryList, testTexts);

        // Testuj accuracy dla różnych k
        HashMap<Integer, Double> results = testAccuracyForDifferentK(testTexts, trainTexts, "czebyszew");

        System.out.println("\nAccuracy dla różnych wartości k (metryka: euklidesowa):");
        results.keySet().stream().sorted().forEach(k ->
                System.out.println("k = " + k + " -> Accuracy = " + results.get(k))
        );

        // Dodatkowe podsumowania (opcjonalnie)
        HashMap<String, Integer> testTextCount = new HashMap<>();
        HashMap<String, Integer> trainTextCount = new HashMap<>();

        for (Text text : testTexts.getTexts()) {
            testTextCount.merge(text.getPlaces(), 1, Integer::sum);
        }
        for (Text text : trainTexts.getTexts()) {
            trainTextCount.merge(text.getPlaces(), 1, Integer::sum);
        }

        System.out.println("\nTrain set distribution: " + trainTextCount);
        System.out.println("Test set distribution: " + testTextCount);
    }
}
