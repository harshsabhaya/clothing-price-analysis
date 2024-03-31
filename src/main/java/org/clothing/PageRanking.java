package org.clothing;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.*;

public class PageRanking {

    private static boolean isHeaderDisplayed = false;
    private static String fileWithMaxOccurrences = "";

    public static void main(String[] args) {
        try {
            String[] csvFiles = {
                    "C:\\Users\\ASUS\\Desktop\\10\\clothing-price-analysis\\assets\\Flipkart.csv",
                    "C:\\Users\\ASUS\\Desktop\\10\\clothing-price-analysis\\assets\\Myntra.csv",
                    "C:\\Users\\ASUS\\Desktop\\10\\clothing-price-analysis\\assets\\Ajio.csv"
            };

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String keyword;
            do {
                System.out.println("Enter the keyword to search for: ");
                keyword = reader.readLine().trim();
            } while (!isValidKeyword(keyword));

            Map<String, Integer> maxOccurrencesMap = new HashMap<>();

            for (String csvFile : csvFiles) {
                Map<String, Integer> pageRanking = getPageRanking(csvFile, keyword);
                displayFilteredPageRanking(pageRanking, csvFile);

                // Update max occurrences
                for (Map.Entry<String, Integer> entry : pageRanking.entrySet()) {
                    int occurrences = entry.getValue();
                    maxOccurrencesMap.put(entry.getKey(), Math.max(maxOccurrencesMap.getOrDefault(entry.getKey(), 0), occurrences));
                }
            }

            // Find file with max occurrences
            int maxOccurrences = 0;
            for (Map.Entry<String, Integer> entry : maxOccurrencesMap.entrySet()) {
                if (entry.getValue() > maxOccurrences) {
                    maxOccurrences = entry.getValue();
                    fileWithMaxOccurrences = getFileName(entry.getKey());
                }
            }

            if (!fileWithMaxOccurrences.isEmpty()) {
                System.out.println("The word '" + keyword + "' has the maximum occurrences of " + maxOccurrences + " in the file: " + fileWithMaxOccurrences);
            } else {
                System.out.println("No data found for the specified keyword.");
            }

        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }

    public static Map<String, Integer> getPageRanking(String filePath, String keyword) throws IOException {
        Map<String, Integer> keywordOccurrences = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                for (String cellValue : nextLine) {
                    if (cellValue.toLowerCase().contains(keyword.toLowerCase())) {
                        keywordOccurrences.put(filePath, keywordOccurrences.getOrDefault(filePath, 0) + 1);
                        break;
                    }
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return keywordOccurrences;
    }

    public static void displayFilteredPageRanking(Map<String, Integer> pageRanking, String fileName) {
        if (pageRanking.isEmpty()) {
            System.out.println("No data found for the specified keyword in file: " + getFileName(fileName));
        } else {
            if (!isHeaderDisplayed) {
                System.out.println("\n-----------------------------------------");
                System.out.println("Page Ranking based on the provided input:");
                System.out.println("-----------------------------------------");
                isHeaderDisplayed = true;
            }
            for (Map.Entry<String, Integer> entry : pageRanking.entrySet()) {
                System.out.println("File: " + getFileName(fileName) + ", Occurrences: " + entry.getValue());
            }
        }
    }

    public static String getFileName(String filePath) {
        // Extracting filename from the full file path
        File file = new File(filePath);
        return file.getName();
    }

    public static boolean isValidKeyword(String keyword) {
        // Check if the keyword contains only alphabets and spaces
        if (!keyword.matches("[a-zA-Z ]+")) {
            System.err.println("Invalid keyword. Please enter alphabets and spaces only.");
            return false;
        }
        return true;
    }
}