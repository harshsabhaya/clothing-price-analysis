package org.clothing;

import org.clothing.scraper.Ajio;
import org.clothing.scraper.Flipkart;
import org.clothing.scraper.Myntra;
import org.clothing.scraper.StoreDataInFile;

import java.util.Scanner;

import static org.clothing.SearchFrequency.wordFrequencyMap;

public class Main {


    public static void collectAndProcessInput(Scanner scanner) {
        String category;
        do {
            System.out.println("\nEnter a clothing category\nRemember, no numbers or hitting 'Enter' like it owes you money! Just letters, please:");
            category = scanner.nextLine().trim();
        } while (!StoreDataInFile.isValidInput(category));

        category = SpellChecker.checkSpelling(scanner, category);

        // Increment the count of entered category in word frequency map
        wordFrequencyMap.put(category, wordFrequencyMap.getOrDefault(category, 0) + 1);

        // Perform web scraping based on the entered category
        webScrapping(category);

        // Update the CSV file with the new word frequency
        SearchFrequency.saveWordFrequencyToCSV();

    }

    public static void webScrapping(String category) {
        Myntra.handleCrawling(category);
        Flipkart.handleCrawling(category);
        Ajio.handleCrawling(category);
    }


    public static void showTopSearchedResult() {
        SearchFrequency.displayTopWords();
    }

    public static void checkFrequency(Scanner scanner) {
        FrequencyCount.countFrequency(scanner);
    }

    public static void invertedIndexing(Scanner scanner) {
        InvertedIndexing.findInvertedIndexing(scanner);
    }


    public static void pageRanking() {
        PageRanking.handlePageRanking();
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
        showTopSearchedResult();
        collectAndProcessInput(scanner);
        wordCompletion(scanner);
        checkFrequency(scanner);
        invertedIndexing(scanner);
        pageRanking();
        scanner.close();

    }

}
