package org.clothing.scraper;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Gap {
    public static void handleCrawling(String category) {
        WebDriver chromeDrive = null;
        try {
            ScrapperMain drive = new ScrapperMain();
            chromeDrive = drive.getDrive();

            chromeDrive.get("https://www.gapcanada.ca/");

            // Retrieve recurring element from Doc
            WebDriverWait wait = new WebDriverWait(chromeDrive, Duration.ofSeconds(10));
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/header/div[5]/div/div/div/div/div/div[3]/div/form/input")));
            searchBox.sendKeys(category);
            searchBox.sendKeys(Keys.ENTER);

            String cssPathForProductCard = "section > div > div > div.product-card";
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
                System.out.println("Gap: Products not found");
            } else {
                for (WebElement element : productItems) {
                    // Extract property details such as title, address, price, link, and image source.
                    String productTitle = element.findElement(By.cssSelector("a > div")).getText();
                    String productPrice = "";
                    WebElement productPrice1 = null;
                    WebElement productPrice2 = null;
                    try {
                        productPrice1 = element.findElement(By.cssSelector("div.product-card-price.css-0 > div > div > span > span"));
                    } catch (Exception e) {
                        productPrice2 = element.findElement(By.xpath("//div[@class=\"product-price__highlight\"]"));
                    }
                    
                    if(productPrice2 != null) {
                        productPrice = productPrice2.getText();
                    } else if (productPrice1 != null) {
                        productPrice = productPrice1.getText();
                    }

                    String productBrand = "H&M";

                    // Print property details to the console.
                    System.out.printf("%s, %s %n%s %n%n", productTitle, productPrice, productBrand);
                    StoreDataInFile storeObject = new StoreDataInFile(productTitle, productPrice, productBrand, category);
                    storeObject.saveDataToCsv("Gap.csv");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert chromeDrive != null;
            chromeDrive.quit();
        }
    }
    // MAIN FUNCTION
    public static void main(String[] args) {
        handleCrawling("Shirt");
    }
}
