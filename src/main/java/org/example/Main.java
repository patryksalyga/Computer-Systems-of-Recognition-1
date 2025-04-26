package org.example;

import java.util.*;

public class Main {

    public static List<Results> testAccuracyForDifferentK(TestTexts testTexts, TrainTexts trainTexts, String metric, boolean[] options, int[] kValues) {
        List<Results> results = new LinkedList<>();

        for (int k : kValues) {
            results.add(new Results(trainTexts, metric, k, options, false, testTexts));
        }

        return results;
    }

    public static void main(String[] args) {
        Dictionaries dictionaries = new Dictionaries();
        boolean [] options = {true, true, true, true, true, true, true, true, true, true}; //pmax, pavg, pmin, smax, savg, smin, lmax, lavg, lmin, s7
        TestTexts testTexts = new TestTexts(dictionaries);
        Double ngramTolerance = 1.0; // 0.0 - 1.0
        String metric = "euclidean"; //euclidean, manhattan, czebyszew
        int trainingSetProportion = 40; // 0 - 100
        int[] kValues = {17}; 


        ReutersProcessor rp = new ReutersProcessor(testTexts);

        HashMap<String, Integer> countryList = new HashMap<>();
        for (Text text : testTexts.getTexts()) {
            countryList.merge(text.getPlaces(), 1, Integer::sum);
        }

        testTexts.createVectors(ngramTolerance);
        TrainTexts trainTexts = new TrainTexts(trainingSetProportion, countryList, testTexts);

        // Testuj accuracy dla różnych k
        List<Results> results = testAccuracyForDifferentK(testTexts, trainTexts, metric, options, kValues);

        System.out.println("\nAccuracy dla różnych wartości k:");
        for (Results result : results) {
            System.out.println("k = " + result.getK() + " -> Accuracy = " + result.getAccuracy());
            System.out.println();
            System.out.printf("%-15s %-10s %-10s %-10s %-10s %-10s%n", "Country", "Precision", "Recall", "F1-Score", "Correct", "Incorrect");
            System.out.println("---------------------------------------------------------------");
            System.out.printf("%-15s %-10.2f %-10.2f %-10.2f %-10d %-10d%n", "USA", result.getUsaPrecision(), result.getUsaRecall(), result.getF1Score(), result.getUsaCorrect(), result.getUsaIncorrect());
            System.out.printf("%-15s %-10.2f %-10.2f %-10.2f %-10d %-10d%n", "Japan", result.getJapanPrecision(), result.getJapanRecall(), result.getJapanF1Score(), result.getJapanCorrect(), result.getJapanIncorrect());
            System.out.printf("%-15s %-10.2f %-10.2f %-10.2f %-10d %-10d%n", "France", result.getFrancePrecision(), result.getFranceRecall(), result.getFranceF1Score(), result.getFranceCorrect(), result.getFranceIncorrect());
            System.out.printf("%-15s %-10.2f %-10.2f %-10.2f %-10d %-10d%n", "UK", result.getUkPrecision(), result.getUkRecall(), result.getUkF1Score(), result.getUkCorrect(), result.getUkIncorrect());
            System.out.printf("%-15s %-10.2f %-10.2f %-10.2f %-10d %-10d%n", "Canada", result.getCanadaPrecision(), result.getCanadaRecall(), result.getCanadaF1Score(), result.getCanadaCorrect(), result.getCanadaIncorrect());
            System.out.printf("%-15s %-10.2f %-10.2f %-10.2f %-10d %-10d%n", "West-Germany", result.getWgPrecision(), result.getWgRecall(), result.getWgF1Score(), result.getWgCorrect(), result.getWgIncorrect());
            System.out.println();
            System.out.println("Total Correct: " + result.getTotalCorrect());
            System.out.println("Total Incorrect: " + result.getTotalIncorrect());
            System.out.println();
            System.out.println("---------------------------------------------------------------");
            System.out.println("---------------------------------------------------------------");
            System.out.println();
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
