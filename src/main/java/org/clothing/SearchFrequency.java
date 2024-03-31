package org.clothing;

import org.clothing.scraper.StoreDataInFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SearchFrequency {
    static final Map<String, Integer> wordFrequencyMap = new HashMap<>();
    private static final String CSV_FILE = StoreDataInFile.getFilePath("SearchFrequency.csv");

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

        System.out.println("\nTop categories searched by users:");
        
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
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("No Frequent Search found");
        }

        wordFrequencyMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }
}
