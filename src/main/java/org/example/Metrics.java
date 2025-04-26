package org.example;

import java.util.LinkedList;
import java.util.List;

public class Metrics {
    public static double euclidean(Text text1, Text text2, boolean[] options){
        double sum = 0;

        if(options[0])sum += Math.pow(1 - nGrams(text1.getPmax(), text2.getPmax()), 2);
        if(options[1])sum += Math.pow(1 - nGrams(text1.getWmax(), text2.getWmax()), 2);
        if(options[2])sum += Math.pow(1 - nGrams(text1.getOmax(), text2.getOmax()), 2);
        if(options[3])sum += Math.pow(1 - nGrams(text1.getImax(), text2.getImax()), 2);
        if(options[4])sum += Math.pow(1 - nGrams(text1.getGmax(), text2.getGmax()), 2);
        if(options[5])sum += Math.pow(text1.isFkraj() == text2.isFkraj() ? 0 : 1, 2);
        if(options[6])sum += Math.pow(text1.getLmax() - text2.getLmax(), 2);
        if(options[7])sum += Math.pow(text1.getLavg() - text2.getLavg(), 2);
        if(options[8])sum += Math.pow(text1.getSdict() - text2.getSdict(), 2);
        if(options[9])sum += Math.pow(text1.getS7() - text2.getS7(), 2);


        return Math.sqrt(sum);
    }

    public static double manhattan(Text text1, Text text2, boolean[] options){
        double sum = 0;

        if(options[0])sum += 1 - nGrams(text1.getPmax(), text2.getPmax());
        if(options[1])sum += 1 - nGrams(text1.getWmax(), text2.getWmax());
        if(options[2])sum += 1 - nGrams(text1.getOmax(), text2.getOmax());
        if(options[3])sum += 1 - nGrams(text1.getImax(), text2.getImax());
        if(options[4])sum += 1 - nGrams(text1.getGmax(), text2.getGmax());
        if(options[5])sum += text1.isFkraj() == text2.isFkraj() ? 0 : 1;
        if(options[6])sum += Math.abs(text1.getLmax() - text2.getLmax());
        if(options[7])sum += Math.abs(text1.getLavg() - text2.getLavg());
        if(options[8])sum += Math.abs(text1.getSdict() - text2.getSdict());
        if(options[9])sum += Math.abs(text1.getS7() - text2.getS7());


        return sum;
    }

    public static double czebyszew(Text text1, Text text2, boolean[] options){
        List<Double> values = new LinkedList<>();

        if(options[0])values.add(1 - nGrams(text1.getPmax(), text2.getPmax()));
        if(options[1])values.add(1 - nGrams(text1.getWmax(), text2.getWmax()));
        if(options[2])values.add(1 - nGrams(text1.getOmax(), text2.getOmax()));
        if(options[3])values.add(1 - nGrams(text1.getImax(), text2.getImax()));
        if(options[4])values.add(1 - nGrams(text1.getGmax(), text2.getGmax()));
        if(options[5])values.add((double) (text1.isFkraj() == text2.isFkraj() ? 0 : 1));
        if(options[6])values.add(Math.abs(text1.getLmax() - text2.getLmax()));
        if(options[7])values.add(Math.abs(text1.getLavg() - text2.getLavg()));
        if(options[8])values.add(Math.abs(text1.getSdict() - text2.getSdict()));
        if(options[9])values.add(Math.abs(text1.getS7() - text2.getS7()));

        double max = values.get(0);
        for (Double value : values) {
            if(value > max){
                max = value;
            }
        }
        return max;
    }

    public static double nGrams(String text1, String text2) {
        if (text1 == null && text2 == null) {
            return 1.0;
        } else if (text1 == null || text2 == null) {
            return 0.0;
        } else if (text1.equals(text2)) {
            return 1.0;
        } else {
            int n = 3;
            int sum1 = 0;
            int count1 = 0;
            int sum2 = 0;
            int count2 = 0;

            for (int i = 2; i <= n; i++) {
                for (int j = 0; j < text1.length() - i + 1; j++) {
                    String ngram = text1.substring(j, j + i);
                    if (text2.contains(ngram)) {
                        sum1++;
                    }
                    count1++;
                }
            }

            for (int i = 2; i <= n; i++) {
                for (int j = 0; j < text2.length() - i + 1; j++) {
                    String ngram = text2.substring(j, j + i);
                    if (text1.contains(ngram)) {
                        sum2++;
                    }
                    count2++;
                }
            }

            double similarity1 = (double) sum1 / count1;
            double similarity2 = (double) sum2 / count2;

            return Math.min(similarity1, similarity2);
        }
    }
}
