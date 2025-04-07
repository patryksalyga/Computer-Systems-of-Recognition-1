package org.example;

import java.util.*;

public class Main {

    public static List<Results> testAccuracyForDifferentK(TestTexts testTexts, TrainTexts trainTexts, String metric, boolean[] options) {
        int[] kValues = {4, 20};
        List<Results> results = new LinkedList<>();

        for (int k : kValues) {
            results.add(new Results(trainTexts, metric, k, options, false, testTexts));
        }

        return results;
    }

    public static void main(String[] args) {
        Dictionaries dictionaries = new Dictionaries();
        boolean [] options = {true, true, true, true, true, false, true, true, true, true};
        TestTexts testTexts = new TestTexts(dictionaries);

        ReutersProcessor rp = new ReutersProcessor(testTexts);

        HashMap<String, Integer> countryList = new HashMap<>();
        for (Text text : testTexts.getTexts()) {
            countryList.merge(text.getPlaces(), 1, Integer::sum);
        }

        testTexts.createVectors();
        TrainTexts trainTexts = new TrainTexts(40, countryList, testTexts);

        // Testuj accuracy dla różnych k
        List<Results> results = testAccuracyForDifferentK(testTexts, trainTexts, "euclidean", options);

        System.out.println("\nAccuracy dla różnych wartości k:");
        for(Results result : results) {
                System.out.println("k = " + result.getK() + " -> Accuracy = " + result.getAccuracy());
        }

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
