package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReutersProcessor {
    private static final Set<String> allowedPlaces = Set.of("usa", "japan", "france", "uk", "canada", "west-germany");

    public ReutersProcessor(TestTexts testTexts) {
        List<File> files = new ArrayList<>();

        String basePath = "src/main/resources/reuters21578/";

        for (int i = 0; i <= 21; i++) { // i: min - > 0 max -> 21
            String fileName = String.format("reut2-%03d.sgm", i);
            File file = new File(basePath + fileName);
            files.add(file);
        }

        for (File file : files) {
            if (file.exists()) {
                processFile(file, testTexts);
            } else {
                System.out.println("Plik nie istnieje: " + file.getAbsolutePath());
            }
        }
    }


    private static void processFile(File file, TestTexts testTexts) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder rawString = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                rawString.append(line).append("\n");
            }

            extractArticles(rawString.toString(), testTexts);
        } catch (IOException e) {
            System.err.println("Błąd podczas przetwarzania pliku: " + file.getName());
            e.printStackTrace();
        }
    }

    private static void extractArticles(String content, TestTexts testTexts) {
        String[] articles = content.split("<REUTERS");
        for (String article : articles) {
            if (!article.contains("</REUTERS>")) continue;

            String body = extractTag(article, "BODY");

            if (body.isEmpty()) {
                body = extractTag(article, "TEXT");
            }

            String places = extractTag(extractTag(article, "PLACES"));

            if (allowedPlaces.contains(places)) {
                testTexts.addTexts(new Text(places, body, testTexts.getDictionaries()));
            }

        }
    }

    private static String extractTag(String rawString, String tag) {
        String startTag = "<" + tag;
        String endTag = "</" + tag + ">";
        int startPos = rawString.indexOf(startTag);
        int endPos = rawString.indexOf(endTag);

        if (startPos == -1 || endPos == -1) {
            return "";
        }

        startPos = rawString.indexOf(">", startPos) + 1;
        return rawString.substring(startPos, endPos).trim();
    }

    private static String extractTag(String rawString) {
        String startTag = "<" + "D";
        String endTag = "</" + "D" + ">";
        int startPos = rawString.indexOf(startTag);
        int endPos = rawString.indexOf(endTag);

        int secondPos = rawString.indexOf(startTag, endPos);

        if (startPos == -1 || endPos == -1 || secondPos != -1) {
            return "";
        }

        startPos = rawString.indexOf(">", startPos) + 1;
        return rawString.substring(startPos, endPos).trim();
    }
}
