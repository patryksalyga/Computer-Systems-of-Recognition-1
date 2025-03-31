package org.example;

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
