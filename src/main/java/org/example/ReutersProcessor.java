package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReutersProcessor {
    public ReutersProcessor(Texts texts) {
        List<File> files = new ArrayList<>();

        String basePath = "src/main/resources/reuters21578/";

        for (int i = 0; i <= 21; i++) {
            String fileName = String.format("reut2-%03d.sgm", i);
            File file = new File(basePath + fileName);
            files.add(file);
        }

        for (File file : files) {
            if (file.exists()) {
                processFile(file, texts);
            } else {
                System.out.println("Plik nie istnieje: " + file.getAbsolutePath());
            }
        }
    }


    private static void processFile(File file, Texts texts) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder rawString = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                rawString.append(line).append("\n");
            }

            extractArticles(rawString.toString(), texts);
        } catch (IOException e) {
            System.err.println("Błąd podczas przetwarzania pliku: " + file.getName());
            e.printStackTrace();
        }
    }

    private static void extractArticles(String content, Texts texts) {
        String[] articles = content.split("<REUTERS");
        for (String article : articles) {
            if (!article.contains("</REUTERS>")) continue;

            String title = extractTag(article, "TITLE");
            String body = extractTag(article, "BODY");
            String places = extractTag(article, "PLACES");
            String dateline = extractTag(article, "DATELINE");

            texts.addTexts(new Text(places, title, dateline, body));
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
}
