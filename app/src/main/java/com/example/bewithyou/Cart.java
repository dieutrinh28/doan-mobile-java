package com.example.bewithyou;

public class Cart {
    private String productName;
    private String price;
    private String quantity;
    private String imgLink;

    public Cart() {
    }

    public Cart(String productName, String price, String quantity, String imgLink) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.imgLink = imgLink;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "productName='" + productName + '\'' +
                ", price='" + price + '\'' +
                ", quantity='" + quantity + '\'' +
                ", imgLink='" + imgLink + '\'' +
                '}';
    }
}
