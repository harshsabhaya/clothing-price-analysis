package org.clothing.scraper;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Meolaa {

    public static void handleCrawling(String category) {
        WebDriver chromeDrive = null;
        try {
            ScrapperMain drive = new ScrapperMain();
            chromeDrive = drive.getDrive();

            // Open the website (target URL)
            chromeDrive.get("https://meolaa.com");

            WebDriverWait wait = new WebDriverWait(chromeDrive, Duration.ofSeconds(10));
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.searchBoxCls")));
            searchBox.sendKeys(category);
            searchBox.sendKeys(Keys.ENTER);


            // Click on the "Men" link (assuming you want to scrape men's clothing)
//            driver.findElement(By.linkText("Men")).click();

            // Define a JavascriptExecutor for scrolling down the page
//            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Scroll down the page to trigger lazy loading of products (might need adjustment)
//            for (int i = 0; i < 10; i++) {
//                js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
//                try {
//                    Thread.sleep(2000); // Wait for content to load after scrolling (adjust wait time if needed)
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }

            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".product_item")));

            // Define a JavascriptExecutor for further lazy loading
            JavascriptExecutor js1 = (JavascriptExecutor) chromeDrive;

            // Scroll down the page to trigger lazy loading
            for (int i = 0; i < 10; i++) { // You may need to adjust the loop count based on the amount of content
                js1.executeScript("window.scrollBy(0, document.body.scrollHeight)");
                Thread.sleep(2000); // Wait for the content to load
            }

            // get all item elements
            List<WebElement> productItem = chromeDrive.findElements(By.cssSelector(".product_item"));

            if(productItem.isEmpty()) {
                System.out.println("Meolaa: Products not found");
            } else {
                for (WebElement element : productItem) {
                    // Extract property details such as title, address, price, link, and image source.
                    String productTitle = element.findElement(By.cssSelector(".itemName")).getText();
                    String productPrice = element.findElement(By.cssSelector(".product_price")).getText();
                    String productBrand = element.findElement(By.cssSelector(".itemProductCategory")).getText();
                    String productCategory = element.findElement(By.cssSelector(".itemProductCategory")).getText();

                    // Print property details to the console.
                    System.out.printf("%s, %s %n%s %n%n", productTitle, productPrice, productBrand);
                    StoreDataInFile storeObject = new StoreDataInFile(productTitle, productPrice, productBrand, productCategory);
                    storeObject.saveDataToCsv("meolaa.csv");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert chromeDrive != null;
            chromeDrive.quit();
        }
    }

    public static void main(String[] args) {
        handleCrawling("Shirts");
    }
}