package org.example;

import java.util.LinkedList;
import java.util.List;

public class Metrics {
    public static double euclidean(Text text1, Text text2){
        double sum = 0;

        sum += Math.pow(1 - nGrams(text1.getPmax(), text2.getPmax()), 2);
        sum += Math.pow(1 - nGrams(text1.getWmax(), text2.getWmax()), 2);
        sum += Math.pow(1 - nGrams(text1.getOmax(), text2.getOmax()), 2);
        sum += Math.pow(1 - nGrams(text1.getImax(), text2.getImax()), 2);
        sum += Math.pow(1 - nGrams(text1.getGmax(), text2.getGmax()), 2);
        sum += Math.pow(text1.isFkraj() == text2.isFkraj() ? 0 : 1, 2);
        sum += Math.pow(text1.getLmax() - text2.getLmax(), 2);
        sum += Math.pow(text1.getLavg() - text2.getLavg(), 2);
        sum += Math.pow(text1.getSdict() - text2.getSdict(), 2);
        sum += Math.pow(text1.getS7() - text2.getS7(), 2);


        return Math.sqrt(sum);
    }

    public static double manhattan(Text text1, Text text2){
        double sum = 0;

        sum += nGrams(text1.getPmax(), text2.getPmax());
        sum += nGrams(text1.getWmax(), text2.getWmax());
        sum += nGrams(text1.getOmax(), text2.getOmax());
        sum += nGrams(text1.getImax(), text2.getImax());
        sum += nGrams(text1.getGmax(), text2.getGmax());
        sum += text1.isFkraj() == text2.isFkraj() ? 0 : 1;
        sum += Math.abs(text1.getLmax() - text2.getLmax());
        sum += Math.abs(text1.getLavg() - text2.getLavg());
        sum += Math.abs(text1.getSdict() - text2.getSdict());
        sum += Math.abs(text1.getS7() - text2.getS7());


        return sum;
    }

    public static double czebyszew(Text text1, Text text2){
        List<Double> values = new LinkedList<>();

        values.add(nGrams(text1.getPmax(), text2.getPmax()));
        values.add(nGrams(text1.getWmax(), text2.getWmax()));
        values.add(nGrams(text1.getOmax(), text2.getOmax()));
        values.add(nGrams(text1.getImax(), text2.getImax()));
        values.add(nGrams(text1.getGmax(), text2.getGmax()));
        values.add((double) (text1.isFkraj() == text2.isFkraj() ? 0 : 1));
        values.add(Math.abs(text1.getLmax() - text2.getLmax()));
        values.add(Math.abs(text1.getLavg() - text2.getLavg()));
        values.add(Math.abs(text1.getSdict() - text2.getSdict()));
        values.add(Math.abs(text1.getS7() - text2.getS7()));

        double max = values.get(0);
        for (Double value : values) {
            if(value > max){
                max = value;
            }
        }
        return max;
    }

    public static double nGrams(String text1, String text2){
        if(text1 == null  && text2 == null){
            return 1;
        }
        else if(text1 == null || text2 == null){
            return 0;
        } else {
            int n = 3;
            int sum = 0;
            int count = 0;
            for (int i = 2; i <= n; i++) {
                for (int j = 0; j < text1.length() - i + 1; j++) {
                    String ngram = text1.substring(j, j + i);
                    if (text2.contains(ngram)) {
                        sum++;
                    }
                    count++;
                }
            }
            return (double) sum / count;
        }
    }
}
