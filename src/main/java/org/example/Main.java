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

    public static void printResults(Results result, double ngramTolerance, String metric, int trainingSetProportion, TestTexts testTexts, TrainTexts trainTexts) {
        System.out.println("---------------------------------------------------------------");
        System.out.println("k = " + result.getK() + " -> Accuracy = " + result.getAccuracy());
        System.out.println("ngramTolerance = " + ngramTolerance + " -> Metric = " + metric + " -> trainingSetProportion = " + trainingSetProportion + "%");
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

        System.out.println();
        System.out.println("---------------------------------------------------------------");
        System.out.println();

    }

    public static void main(String[] args) {
        boolean [] options = {true, true, true, true, true, true, true, true, true, true}; //pmax, pavg, pmin, smax, savg, smin, lmax, lavg, lmin, s7
        double[] ngramTolerances = {0.1, 0.3, 0.5, 0.7, 1.0}; // 0.0 - 1.0
        String metric = "euclidean"; //euclidean, manhattan, czebyszew
        int[] trainingSetProportions = {20, 30, 40, 50, 60}; // 0 - 100
        int[] kValues = {1, 5, 9, 15, 25, 50, 75, 100}; // k values to test

        Dictionaries dictionaries = new Dictionaries();
        Texts texts = new Texts(dictionaries);
        TestTexts testTexts = new TestTexts();


        ReutersProcessor rp = new ReutersProcessor(texts);

        HashMap<String, Integer> countryList = new HashMap<>();
        for (Text text : texts.getTexts()) {
            countryList.merge(text.getPlaces(), 1, Integer::sum);
        }

        texts.createVectors(1.0);
        TrainTexts trainTexts = new TrainTexts(40, countryList, testTexts, texts);

        // Testuj accuracy dla różnych k
        List<Results> kResults = testAccuracyForDifferentK(testTexts, trainTexts, metric, options, kValues);

        System.out.println("\nAccuracy dla różnych wartości k:");
        for (Results result : kResults) {
            printResults(result, 1.0, metric, 40, testTexts, trainTexts);
        }

        int bestK = kResults.stream().max(Comparator.comparingDouble(Results::getAccuracy)).get().getK();
        System.out.println("Best k: " + bestK + "\n");

        // Step 2: Find the best training set proportion with the best k
        System.out.println("\nTesting different training set proportions:");
        int bestTrainingSetProportion = 40; // Default value
        double bestAccuracy = 0.0;

        for (int proportion : trainingSetProportions) {
            testTexts = new TestTexts();
            trainTexts = new TrainTexts(proportion, countryList, testTexts, texts);
            Results result = new Results(trainTexts, metric, bestK, options, false, testTexts);
            printResults(result, 1.0, metric, proportion, testTexts, trainTexts);
            if (result.getAccuracy() > bestAccuracy) {
                bestAccuracy = result.getAccuracy();
                bestTrainingSetProportion = proportion;
            }
        }
        System.out.println("Best training set proportion: " + bestTrainingSetProportion);

        // Step 3: Find the best ngramTolerance with the best k and best training set proportion
        System.out.println("\nTesting different ngramTolerance values:");
        double bestNgramTolerance = 1.0; // Default value
        bestAccuracy = 0.0;

        for (double tolerance : ngramTolerances) {
            texts.createVectors(tolerance);
            testTexts = new TestTexts();
            trainTexts = new TrainTexts(bestTrainingSetProportion, countryList, testTexts, texts);
            Results result = new Results(trainTexts, metric, bestK, options, false, testTexts);
            printResults(result, tolerance, metric, bestTrainingSetProportion, testTexts, trainTexts);
            if (result.getAccuracy() > bestAccuracy) {
                bestAccuracy = result.getAccuracy();
                bestNgramTolerance = tolerance;
            }
        }
        System.out.println("Best ngramTolerance: " + bestNgramTolerance);

        // Final results
        System.out.println("\nFinal configuration:");
        System.out.println("k = " + bestK);
        System.out.println("Training set proportion = " + bestTrainingSetProportion + "%");
        System.out.println("ngramTolerance = " + bestNgramTolerance);
    }
}
