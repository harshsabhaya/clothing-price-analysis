package org.clothing;

import org.clothing.scraper.Gap;
import org.clothing.scraper.HnM;
import org.clothing.scraper.Meolaa;

import java.util.List;
import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void displayMessage() {
        System.out.println("Enter the category you want search: :");
        System.out.println("Some popular categories user frequently search are listed below");
        System.out.println("""
                ---------------------
                1) Casual Wear
                2) Formal Wear
                3) Sports Wear
                ---------------------""");

        Scanner scanner = new Scanner(System.in);
        int category = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter a brand name you want search:");
        String brandName = scanner.nextLine();
        SpellChecker obj = new SpellChecker();
        List<String> result = obj.checkSpelling(brandName);

        if(!result.isEmpty()) {
            System.out.println("Do you want to search?");
            for(String ele: result) {
                System.out.println(ele);
            }
        }


        switch(category) {
            case 1:
                Meolaa.handleCrawling("Casual Wear");
                Gap.handleCrawling("Casual Wear");
                HnM.handleCrawling("Casual Wear");
                break;
            case 2:
                Meolaa.handleCrawling("Formal Wear");
                Gap.handleCrawling("Formal Wear");
                HnM.handleCrawling("Formal Wear");
                break;
            case 3:
                Meolaa.handleCrawling("Sports Wear");
                Gap.handleCrawling("Sports Wear");
                HnM.handleCrawling("Sports Wear");
                break;
            default:
                // code block
        }
        System.out.println("category: "+ category);

    }

    public static void main(String[] args) {
        displayMessage();
    }
}