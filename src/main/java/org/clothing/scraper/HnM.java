package org.clothing.scraper;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
public class HnM {
    String webDriverPath = "C:\\selenium webdriver\\ChromeDriver\\chromedriver-win32\\chromedriver-win32\\chromedriver.exe";
    String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36";

    ChromeOptions options = setChromeDrivers();
    WebDriver driver = new ChromeDriver(options);

    // Scrap H&M products based on url provided
    public List<HnMproduct> fetchHmProducts(String url){


        // Get DOM Document
        Document document = getHTMLDocForHm(url);
//        System.out.println(document);

        // Retrieve recurring element from Doc
        Elements elements = document.select(".product-item");

        // Created list of products to store the fetched Products
        List<HnMproduct> productList = new ArrayList<>();
        for(Element e : elements){
            HnMproduct p = new HnMproduct();
            String productImage = e.select("article > div > a > img").attr("data-altimage");
            String productName = e.select("article > div > h3 > a").text();
            String productUrl  = "https://www2.hm.com" + e.select("article > div > h3 > a ").attr("href");
            String productPrice = e.select("article >div > strong > span").text().split(" ")[0];

            p.setProductName(productName);
            p.setProductImage(productImage);
            p.setProductUrl(productUrl);
            p.setProductPrice(productPrice);
            productList.add(p);
        }
        for(HnMproduct product: productList){
            System.out.println("Name: "+product.productName);
            System.out.println("Image: "+product.productImage);
            System.out.println("Url: "+product.productUrl);
            System.out.println("Price: "+product.productPrice);
        }
        System.out.println(productList.size());

        // generate file name based on url
        String[] fileNameArray = url.split("/");
        String fileName = fileNameArray[fileNameArray.length-1].split("\\.")[0];
//        System.out.println(fileName);
        createExcelSheet(productList,fileName);

        System.out.println("Scrapping for " + url +"  Ended");
        return productList;
    }

//    public void exploreDifferentElements(){
//        String url = "";
//    }

    // Create excel sheet and store it into resource folder
    private void createExcelSheet(List<HnMproduct> productList, String fileName) {
        try
        {
            String filename = "results\\"+fileName+".xlsx";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("H&M");

            // create first row to write title
            HSSFRow rowhead = sheet.createRow((short)0);
            rowhead.createCell(0).setCellValue("Name");
            rowhead.createCell(1).setCellValue("Image");
            rowhead.createCell(2).setCellValue("URL");
            rowhead.createCell(3).setCellValue("Price");

            // Remaining rows to write data
            for(int i=0; i < productList.size();i++){
                HSSFRow row = sheet.createRow((short)(i+1));
                row.createCell(0).setCellValue(productList.get(i).productName);
                row.createCell(1).setCellValue(productList.get(i).productImage);
                row.createCell(2).setCellValue(productList.get(i).productUrl);
                row.createCell(3).setCellValue(productList.get(i).productPrice);
            }

            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);

            // Closing the objects
            fileOut.close();
            workbook.close();

            System.out.println("Excel file has been generated successfully.");
        }
        catch (Exception e)
        {
            System.out.println("Exception occurred during excel creation");
        }
    }

    // Get HTML Dom Document for provided URL
    private Document getHTMLDocForHm(String url) {

        driver.get(url);
        try{
            driver.findElement(By.id("onetrust-reject-all-handler")).click();
        }catch (Exception e){
            System.out.println("Element is not present in the DOM!");
        }

        try {
            while(driver.findElement(By.className("js-load-more")) != null) {
                driver.findElement(By.className("js-load-more")).click();
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            System.out.println("No such element in DOM!");;
        }

        String doc = driver.getPageSource();
        Document document = null;
        document = Jsoup.parse(doc);
//        driver.close();
        return document;
    }

    // Set Chrome Drivers
    private ChromeOptions setChromeDrivers() {
        System.setProperty("webdriver.chrome.driver", webDriverPath);
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        options.addArguments("user-agent="+userAgent);
        return options;
    }

    public static void main(String[] args) {
        HnM inst = new HnM();

        inst.fetchHmProducts("https://www2.hm.com/en_ca/men/shop-by-product/shirts.html");
        inst.fetchHmProducts("https://www2.hm.com/en_ca/men/shop-by-product/jeans.html");
        System.out.printf("Scrapping for Started %n");
    }
}
