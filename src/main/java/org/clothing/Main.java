package org.clothing;

import org.clothing.scraper.Ajio;
import org.clothing.scraper.Flipkart;
import org.clothing.scraper.Myntra;

import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void webScrapping(String category) {
        Myntra.handleCrawling(category);
        Flipkart.handleCrawling(category);
        Ajio.handleCrawling(category);
    }

    public static void displayMessage() {
        System.out.println("Some popular searched categories");
        System.out.println("""
                ---------------------
                1) Casual Wear
                2) Formal Wear
                3) Sports Wear
                ---------------------""");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a category you want to search: ");
        String category = scanner.nextLine();
        SpellChecker spellChecker = new SpellChecker();
        List<String> results = spellChecker.checkSpelling(category);

        while (!results.isEmpty()) {
            for (String ele : results) {
                System.out.println(ele);
            }

            // Prompt the user to enter a corrected category
            System.out.println("Enter a corrected category: ");
            category = scanner.nextLine();

            // Update the result list with the corrected category
            results = spellChecker.checkSpelling(category);
        }
        System.out.println("Web Scraping...........");
        webScrapping(category);

    }

    public static void main(String[] args) {
        displayMessage();
    }
}