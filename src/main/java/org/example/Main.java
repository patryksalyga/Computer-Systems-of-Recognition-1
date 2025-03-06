package org.example;

public class Main {
    public static void main(String[] args) {
        Texts texts = new Texts();

        ReutersProcessor rp = new ReutersProcessor(texts);

        System.out.println("Liczba przetworzonych artykułów: " + texts.getTexts().size());

        System.out.println(texts.getTexts().get(0).getBody());
    }
}