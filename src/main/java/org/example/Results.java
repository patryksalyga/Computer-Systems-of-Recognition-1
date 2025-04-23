package org.example;

public class Results {
    private double accuracy;
    private int usaCorrect;
    private int usaIncorrect;
    private int japanCorrect;
    private int japanIncorrect;
    private int franceCorrect;
    private int franceIncorrect;
    private int ukCorrect;
    private int ukIncorrect;
    private int canadaCorrect;
    private int canadaIncorrect;
    private int wgCorrect;
    private int wgIncorrect;
    private int totalCorrect;
    private int totalIncorrect;
    private double usaPrecision;
    private  double usaRecall;
    private double f1Score;
    private double japanPrecision;
    private double japanRecall;
    private double japanF1Score;
    private double francePrecision;
    private double franceRecall;
    private double franceF1Score;
    private double ukPrecision;
    private double ukRecall;
    private double ukF1Score;
    private double canadaPrecision;
    private double canadaRecall;
    private double canadaF1Score;
    private double wgPrecision;
    private double wgRecall;
    private double wgF1Score;
    private int k;

    public Results(TrainTexts trainTexts, String metric, int k, boolean[] options, boolean b, TestTexts testTexts) {
        this.k = k;

        // Czyść predykcje
        for (Text text : testTexts.getTexts()) {
            text.setPrediction(null);
        }

        // Przeprowadź klasyfikację
        for (Text text : testTexts.getTexts()) {
            text.decide(trainTexts.getTexts(), metric, k, options, false);
        }

        for(Text text : testTexts.getTexts()){
            if(text.getPlaces().equals("usa")){
                if(text.getPrediction().equals("usa")){
                    usaCorrect++;
                } else {
                    usaIncorrect++;
                }
            } else if(text.getPlaces().equals("japan")){
                if(text.getPrediction().equals("japan")){
                    japanCorrect++;
                } else {
                    japanIncorrect++;
                }
            } else if(text.getPlaces().equals("france")){
                if(text.getPrediction().equals("france")){
                    franceCorrect++;
                } else {
                    franceIncorrect++;
                }
            } else if(text.getPlaces().equals("uk")){
                if(text.getPrediction().equals("uk")){
                    ukCorrect++;
                } else {
                    ukIncorrect++;
                }
            } else if(text.getPlaces().equals("canada")){
                if(text.getPrediction().equals("canada")){
                    canadaCorrect++;
                } else {
                    canadaIncorrect++;
                }
            } else if(text.getPlaces().equals("west-germany")){
                if(text.getPrediction().equals("west-germany")){
                    wgCorrect++;
                } else {
                    wgIncorrect++;
                }
            }
        }
        totalCorrect = usaCorrect + japanCorrect + franceCorrect + ukCorrect + canadaCorrect + wgCorrect;
        totalIncorrect = usaIncorrect + japanIncorrect + franceIncorrect + ukIncorrect + canadaIncorrect + wgIncorrect;

        // Oblicz accuracy
        accuracy = testTexts.rateAccuracy();

        usaPrecision = testTexts.ratePrecision("usa");
        usaRecall = testTexts.rateRecall("usa");
        f1Score = testTexts.rateF1Score(usaPrecision, usaRecall);

        japanPrecision = testTexts.ratePrecision("japan");
        japanRecall = testTexts.rateRecall("japan");
        japanF1Score = testTexts.rateF1Score(japanPrecision, japanRecall);

        francePrecision = testTexts.ratePrecision("france");
        franceRecall = testTexts.rateRecall("france");
        franceF1Score = testTexts.rateF1Score(francePrecision, franceRecall);

        ukPrecision = testTexts.ratePrecision("uk");
        ukRecall = testTexts.rateRecall("uk");
        ukF1Score = testTexts.rateF1Score(ukPrecision, ukRecall);

        canadaPrecision = testTexts.ratePrecision("canada");
        canadaRecall = testTexts.rateRecall("canada");
        canadaF1Score = testTexts.rateF1Score(canadaPrecision, canadaRecall);

        wgPrecision = testTexts.ratePrecision("west-germany");
        wgRecall = testTexts.rateRecall("west-germany");
        wgF1Score = testTexts.rateF1Score(wgPrecision, wgRecall);
    }

    public int getK() {
        return k;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getUsaPrecision() {
        return usaPrecision;
    }

    public double getUsaRecall() {
        return usaRecall;
    }

    public double getF1Score() {
        return f1Score;
    }

    public double getJapanPrecision() {
        return japanPrecision;
    }

    public double getJapanRecall() {
        return japanRecall;
    }

    public double getJapanF1Score() {
        return japanF1Score;
    }

    public double getFrancePrecision() {
        return francePrecision;
    }

    public double getFranceRecall() {
        return franceRecall;
    }

    public double getFranceF1Score() {
        return franceF1Score;
    }

    public double getUkPrecision() {
        return ukPrecision;
    }

    public double getUkRecall() {
        return ukRecall;
    }

    public double getUkF1Score() {
        return ukF1Score;
    }

    public double getCanadaPrecision() {
        return canadaPrecision;
    }

    public double getCanadaRecall() {
        return canadaRecall;
    }

    public double getCanadaF1Score() {
        return canadaF1Score;
    }

    public double getWgPrecision() {
        return wgPrecision;
    }

    public double getWgRecall() {
        return wgRecall;
    }

    public double getWgF1Score() {
        return wgF1Score;
    }

    public int getUsaCorrect() {
        return usaCorrect;
    }

    public int getUsaIncorrect() {
        return usaIncorrect;
    }

    public int getJapanCorrect() {
        return japanCorrect;
    }

    public int getJapanIncorrect() {
        return japanIncorrect;
    }

    public int getFranceCorrect() {
        return franceCorrect;
    }

    public int getFranceIncorrect() {
        return franceIncorrect;
    }

    public int getUkCorrect() {
        return ukCorrect;
    }

    public int getUkIncorrect() {
        return ukIncorrect;
    }

    public int getCanadaCorrect() {
        return canadaCorrect;
    }

    public int getCanadaIncorrect() {
        return canadaIncorrect;
    }

    public int getWgCorrect() {
        return wgCorrect;
    }

    public int getWgIncorrect() {
        return wgIncorrect;
    }

    public int getTotalCorrect() {
        return totalCorrect;
    }

    public int getTotalIncorrect() {
        return totalIncorrect;
    }
}
