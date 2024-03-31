package org.clothing;

import org.clothing.scraper.Ajio;
import org.clothing.scraper.Flipkart;
import org.clothing.scraper.Myntra;
import org.openqa.selenium.devtools.v85.page.Page;

import java.io.*;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String CSV_FILE = "search_frequency.csv";

    private static final Map<String, Integer> wordFrequencyMap = new HashMap<>();


    public static void collectAndProcessInput(Scanner scanner) {
        String category;
        do {
            System.out.println("\nEnter a clothing category\nRemember, no numbers or hitting 'Enter' like it owes you money! Just letters, please:");
            category = scanner.nextLine().trim();
        } while (!isValidInput(category));

        SpellChecker.checkSpelling(scanner, category);
        // Increment the count of entered category in word frequency map
        wordFrequencyMap.put(category, wordFrequencyMap.getOrDefault(category, 0) + 1);

//       // Perform web scraping based on the entered category
//        webScrapping(category);

        // Update the CSV file with the new word frequency
        saveWordFrequencyToCSV();

    }

    public static void webScrapping(String category) {
        Myntra.handleCrawling(category);
        Flipkart.handleCrawling(category);
        Ajio.handleCrawling(category);
    }

    private static boolean isValidInput(String input) {
        return input.matches("[a-zA-Z ]+");
    }

    public static void saveWordFrequencyToCSV() {
        // Writing word frequencies to a CSV file
        try (FileWriter writer = new FileWriter(CSV_FILE)) {
            for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
                writer.append(entry.getKey()).append(",").append(String.valueOf(entry.getValue())).append("\n");
            }
            System.out.println("Word frequencies saved to " + CSV_FILE + " successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the CSV file: " + e.getMessage());
        }
    }

    public static void displayTopWords() {
        // Reading word frequencies from the CSV file
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("An error occurred while creating the CSV file: " + e.getMessage());
                return;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String word = parts[0];
                int frequency = Integer.parseInt(parts[1]);
                wordFrequencyMap.put(word, frequency);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the CSV file: " + e.getMessage());
            return;
        }

        System.out.println("\nTop categories searched by users:");
        wordFrequencyMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }

    public static void checkFrequency(Scanner scanner) {
        FrequencyCount.countFrequency(scanner);
    }

    public static void invertedIndexing(Scanner scanner) {
        InvertedIndexing.findInvertedIndexing(scanner);
    }


    public static void pageRanking() {
        PageRanking pageRanking = new PageRanking();
        PageRanking.main(new String[0]);
    }

    public static void wordCompletion(Scanner scanner) {
        WordCompletion.wordCompletion(scanner);
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Displaying the top categories based on word frequencies
        System.out.println("\n" +
                "  _    _                  _    _                       _     _____                        _____   _____    ____   _____    ____  \n" +
                " | |  | |                | |  | |                     | |   |_   _|                      |  __ \\ |  __ \\  / __ \\ |  __ \\  / __ \\ \n" +
                " | |__| |  ___  _   _    | |_ | |__    ___  _ __  ___ | |     | |     __ _  _ __ ___     | |__) || |__) || |  | || |  | || |  | |\n" +
                " |  __  | / _ \\| | | |   | __|| '_ \\  / _ \\| '__|/ _ \\| |     | |    / _` || '_ ` _ \\    |  ___/ |  _  / | |  | || |  | || |  | |\n" +
                " | |  | ||  __/| |_| |   | |_ | | | ||  __/| |  |  __/|_|    _| |_  | (_| || | | | | |   | |     | | \\ \\ | |__| || |__| || |__| |\n" +
                " |_|  |_| \\___| \\__, |    \\__||_| |_| \\___||_|   \\___|(_)   |_____|  \\__,_||_| |_| |_|   |_|     |_|  \\_\\ \\____/ |_____/  \\____/ \n" +
                "                 __/ |                                                                                                           \n" +
                "                |___/                                                                                                            \n");
        System.out.println("YOUR OWN PRODUCT ANALYZER.");
        System.out.println("I BRING YOU THE BEST INFO IN BEST POSSIBLE WAY FROM MYNTRA.COM, FLIPKART.COM & AJIO.COM");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
        // Features
        displayTopWords();
        collectAndProcessInput(scanner);
        wordCompletion(scanner);
        checkFrequency(scanner);
        invertedIndexing(scanner);
        pageRanking();
        scanner.close();

    }

}
