package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Dictionaries {
    List<String> countries;
    List<String> currencies;
    List<String> persons;
    List<String> organizations;
    List<String> exchanges;

    public Dictionaries() {
        try {
            countries = Files.readAllLines(Path.of("src/main/resources/reuters21578/all-places-strings.lc.txt"))
                    .stream()
                    .map(line -> line.replaceAll("\\s+", ""))  // Remove all spaces
                    .collect(Collectors.toList());

            persons = Files.readAllLines(Path.of("src/main/resources/reuters21578/all-people-strings.lc.txt"))
                    .stream()
                    .map(line -> line.replaceAll("\\s+", ""))  // Remove all spaces
                    .collect(Collectors.toList());

            organizations = Files.readAllLines(Path.of("src/main/resources/reuters21578/all-orgs-strings.lc.txt"))
                    .stream()
                    .map(line -> line.replaceAll("\\s+", "").toUpperCase())  // Remove all spaces
                    .collect(Collectors.toList());

            exchanges = Files.readAllLines(Path.of("src/main/resources/reuters21578/all-exchanges-strings.lc.txt"))
                    .stream()
                    .map(line -> line.replaceAll("\\s+", ""))  // Remove all spaces
                    .collect(Collectors.toList());

            currencies = extractCurrencies("src/main/resources/reuters21578/cat-descriptions_120396.txt");
            currencies.removeLast();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        System.out.println(countries);
//        System.out.println(persons);
//        System.out.println(organizations);
//        System.out.println(exchanges);
//        System.out.println(currencies);
    }

    private List<String> extractCurrencies(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filePath));

        return lines.stream()
                .dropWhile(line -> !line.contains("**Currency Codes (27)"))  // Skip lines until the currency codes section
                .skip(2)  // Skip the header line
                .takeWhile(line -> !line.startsWith("**"))  // Take lines until the next section
                .map(line -> {
                    int start = line.indexOf('(');

                    int end = line.indexOf(')', start);

                    return start != -1 && end != -1 ? line.substring(start + 1, end).toLowerCase() : "";
                })
                .collect(Collectors.toList());
    }

    public List<String> getCountries() {
        return countries;
    }

    public List<String> getCurrencies() {
        return currencies;
    }

    public List<String> getPersons() {
        return persons;
    }

    public List<String> getOrganizations() {
        return organizations;
    }

    public List<String> getExchanges() {
        return exchanges;
    }
}