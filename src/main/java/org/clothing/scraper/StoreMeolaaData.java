package org.clothing.scraper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class StoreMeolaaData {
    // Method to handle data extracted from a list of house elements
    public static void handleData(List<WebElement> productItem) {
        try {
            // Define the file path for the Excel file, including a timestamp in the filename.
            String xlsxFilePath = "results\\meolaa.xlsx";

            XSSFWorkbook workbook;
            XSSFSheet spreadsheet;

            // Check if the Excel file exists
            try (FileInputStream fis = new FileInputStream(xlsxFilePath)) {
                // If exists, open the workbook and get the existing sheet
                workbook = new XSSFWorkbook(fis);
                spreadsheet = workbook.getSheetAt(0); // Assuming data is appended to the first sheet
            } catch (IOException e) {
                // File doesn't exist, create new workbook and sheet
                workbook = new XSSFWorkbook();
                // Create a new sheet named "PropertyData" in the workbook.
                spreadsheet = workbook.createSheet(" Meolaa ");

                // Create a row at index 0 for column headings.
                XSSFRow headingRow = spreadsheet.createRow(0);

                // Set column headings for the spreadsheet.
                headingRow.createCell(0).setCellValue("id");
                headingRow.createCell(1).setCellValue("productTitle");
                headingRow.createCell(2).setCellValue("productPrice");
                headingRow.createCell(3).setCellValue("productBrand");
                headingRow.createCell(3).setCellValue("productCategory");
                headingRow.createCell(4).setCellValue("productLink");
                headingRow.createCell(5).setCellValue("productImg");
            }

            // Append data to the sheet
            int lastRowNum = spreadsheet.getLastRowNum();
            if (lastRowNum == -1 && spreadsheet.getRow(0) == null) {
                lastRowNum = 0;
            }

            int newRowNum = lastRowNum + 1;


            // Iterate through each house element in the list to extract property details.
            for (int x = 0; x < productItem.size(); x++) {
                WebElement element = productItem.get(x);

                // Extract property details such as title, address, price, link, and image source.
                String productTitle = element.findElement(By.cssSelector(".itemName")).getText();
                String productPrice = element.findElement(By.cssSelector(".product_price")).getText();
                String productBrand = element.findElement(By.cssSelector(".itemProductCategory")).getText();
                String productCategory = element.findElement(By.cssSelector(".itemProductCategory")).getText();
                String productLink = element.findElement(By.cssSelector("div.product_image > a")).getAttribute("href");
                String productImg = element.findElement(By.cssSelector("div.product_image > a > span > img")).getAttribute("src");

                // Print property details to the console.
                System.out.printf("%s, %s, %s %n%s %n%s %n%n", productTitle, productPrice, productBrand, productLink, productImg);

                // Create a new row in the spreadsheet and populate it with property data.
                XSSFRow dataRow = spreadsheet.createRow(newRowNum + x);
                dataRow.createCell(0).setCellValue(newRowNum + x);
                dataRow.createCell(1).setCellValue(productTitle);
                dataRow.createCell(2).setCellValue(productPrice);
                dataRow.createCell(3).setCellValue(productBrand);
                dataRow.createCell(3).setCellValue(productCategory);
                dataRow.createCell(4).setCellValue(productLink);
                dataRow.createCell(5).setCellValue(productImg);
            }

            // Write the workbook data to the specified file path.
            FileOutputStream fileObj = new FileOutputStream(xlsxFilePath);
            workbook.write(fileObj);

            // Close the FileOutputStream and workbook objects.
            fileObj.close();
            workbook.close();

            System.out.println("Excel file has been generated successfully.");
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
    }
}
