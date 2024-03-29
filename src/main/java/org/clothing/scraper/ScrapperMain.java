package org.clothing.scraper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ScrapperMain {
    
    public WebDriver getDrive() {
        WebDriver driver = null;
        String chromeDrivePath =  "C:\\Users\\Harsh Sabhaya\\eclipse-workspace\\chromedriver-win64\\chromedriver.exe";
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36";

        try {

            System.setProperty("webdriver.chrome.driver", chromeDrivePath);

            // Initialize ChromeDriver
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("user-agent="+userAgent);

            driver = new ChromeDriver(options);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }
}
