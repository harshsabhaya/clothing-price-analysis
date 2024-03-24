package org.clothing.scraper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MeolaaWebsite {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = null;
        try {

            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Harsh Sabhaya\\eclipse-workspace\\chromedriver-win64\\chromedriver.exe");

            // Initialize ChromeDriver
            driver = new ChromeDriver();

            // Open the website (target URL)
            driver.get("https://meolaa.com");

            // Click on the "Men" link (assuming you want to scrape men's clothing)
            driver.findElement(By.linkText("Men")).click();

            // Define a JavascriptExecutor for scrolling down the page
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Scroll down the page to trigger lazy loading of products (might need adjustment)
            for (int i = 0; i < 10; i++) {
                js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
                try {
                    Thread.sleep(2000); // Wait for content to load after scrolling (adjust wait time if needed)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // get all item elements
            List<WebElement> productItem = driver.findElements(By.cssSelector(".product_item"));

            if(productItem.isEmpty()) {
                System.out.println("Meolaa: Products not found");
            } else {
                StoreMeolaaData.handleData(productItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert driver != null;
            driver.quit();
        }
    }
}