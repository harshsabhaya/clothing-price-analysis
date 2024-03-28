package org.clothing.scraper;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StoreDataInFile {

    private String productName;
    private String productPrice;
    private String productBrand;
    private String productCategory;

    StoreDataInFile( String pName, String pPrice, String pBrand, String pCategory){
        productName=pName;
        productPrice=pPrice;
        productBrand=pBrand;
        productCategory=pCategory;
    }
    // Getter and Setter for productName
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Getter and Setter for productPrice
    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    // Getter and Setter for productBrand
    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    // Getter and Setter for productCategory
    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getDataInCsvFormat() {
        return "\"" + productName.replace("\"", "'") +"\"" + "," +"\"" +
                productPrice  +"\"" + "," +"\"" +
                productBrand.replace("\"", "'")  +"\"" + "," +"\"" +
                productCategory.replace("\"", "'")  + "\"\n";
    }

    public void saveDataToCsv(String filename) {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path dirpath = Paths.get(currentPath.toString(),"assets");

        filename = dirpath +"/"+filename;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            // Write the content to the file
            writer.write(this.getDataInCsvFormat());
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }
}
