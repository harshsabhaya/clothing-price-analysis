package org.clothing.scraper;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
public class HnM {
    // Scrap H&M products based on url provided
    public static void handleCrawling(String category){
        WebDriver chromeDrive = null;
        try {
            ScrapperMain drive = new ScrapperMain();
            chromeDrive = drive.getDrive();

            chromeDrive.get("https://www2.hm.com/en_ca/");

            // Retrieve recurring element from Doc
            WebDriverWait wait = new WebDriverWait(chromeDrive, Duration.ofSeconds(10));
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/header/nav/div[3]/div/div[2]/div[1]/div/div[1]/div[2]/div/input")));
            searchBox.sendKeys(category);
            searchBox.sendKeys(Keys.ENTER);

            String cssPathForProductCard = "ul > li > section > article";
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssPathForProductCard)));

            // Define a JavascriptExecutor for further lazy loading
            JavascriptExecutor js1 = (JavascriptExecutor) chromeDrive;

            // Scroll down the page to trigger lazy loading
            for (int i = 0; i < 10; i++) { // You may need to adjust the loop count based on the amount of content
                js1.executeScript("window.scrollBy(0, document.body.scrollHeight)");
                Thread.sleep(2000); // Wait for the content to load
            }

            // get all item elements
            List<WebElement> productItems = chromeDrive.findElements(By.cssSelector(cssPathForProductCard));
            if(productItems.isEmpty()) {
                System.out.println("H&M: Products not found");
            } else {
                for (WebElement element : productItems) {
                    // Extract property details such as title, address, price, link, and image source.
                    String productTitle = element.findElement(By.cssSelector("div > div > a > h2")).getText();
                    System.out.println("productTitle: " + productTitle);
                    String productPrice = element.findElement(By.cssSelector("div > div > p > span")).getText();
                    String productBrand = "H&M";

                    // Print property details to the console.
                    System.out.printf("%s, %s %n%s %n%n", productTitle, productPrice, productBrand);
                    StoreDataInFile storeObject = new StoreDataInFile(productTitle, productPrice, productBrand, category);
                    storeObject.saveDataToCsv("HnM.csv");
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