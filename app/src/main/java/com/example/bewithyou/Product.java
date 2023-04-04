package com.example.bewithyou;

public class Product {
    private String productName;
    private String price;
    private String productDescription;
    private String productImg;


    public Product() {
    }

    public Product(String productName, String price, String productDescription, String productImg) {
        this.productName = productName;
        this.price = price;
        this.productDescription = productDescription;
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + productName + '\'' +
                ", price='" + price + '\'' +
                ", description='" + productDescription + '\'' +
                ", img='" + productImg + '\'' + '}';
    }
}
