package org.clothing.scraper;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Myntra {

    public static void handleCrawling(String category) {
        WebDriver chromeDrive = null;
        try {
            ScrapperMain drive = new ScrapperMain();
            chromeDrive = drive.getDrive();

            // Open the website (target URL)
            chromeDrive.get("https://www.myntra.com/");

            WebDriverWait wait = new WebDriverWait(chromeDrive, Duration.ofSeconds(10));
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.desktop-searchBar")));
            searchBox.sendKeys(category);
            searchBox.sendKeys(Keys.ENTER);

            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".results-base")));

            // get all item elements
            List<WebElement> productItem = chromeDrive.findElements(By.cssSelector(".product-base"));
            String productTitle = "";
            String productPrice = "";
            String productBrand = "";
            String productCategory = chromeDrive.findElement(By.cssSelector("ul.breadcrumbs-list li:last-child > span")).getText();

            if (productItem.isEmpty()) {
                System.out.println("Products not found");
            } else {
                String filePath = StoreDataInFile.getFilePath("Myntra.csv");
                StoreDataInFile.deleteIfFileExist(filePath);

                for (WebElement element : productItem) {
                    try {
                        // Extract property details such as title, address, price, link, and image source.
                        productTitle = element.findElement(By.cssSelector(".product-product")).getText();
                        productBrand = element.findElement(By.cssSelector(".product-brand")).getText();

                        try {
                            productPrice = element.findElement(By.xpath("div.product-price > span > span.product-discountedPrice")).getText();
                        } catch (Exception e) {
                            productPrice = element.findElement(By.cssSelector("div.product-price > span")).getText();
                        }

                        System.out.println("productCategory " + productCategory);
                        // Print property details to the console.
                        StoreDataInFile storeObject = new StoreDataInFile(productTitle, productPrice, productBrand, productCategory);

                        storeObject.saveDataToCsv(filePath);
                    } catch (NoSuchElementException e) {
                        continue;
                    }
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
        handleCrawling("Jeans");
    }
}