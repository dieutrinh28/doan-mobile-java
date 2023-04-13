package com.example.bewithyou.model;

public class Store {
    private String storeName;
    private String storeDescription;
    private String rating;
    private String storeImg;
    private String storeAddress;

    public Store(){}

    public Store(String storeName, String storeDescription, String rating, String storeImg, String storeAddress) {
        this.storeName = storeName;
        this.storeDescription = storeDescription;
        this.rating = rating;
        this.storeImg = storeImg;
        this.storeAddress = storeAddress;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public void setStoreDescription(String storeDescription) {
        this.storeDescription = storeDescription;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStoreImg() {
        return storeImg;
    }

    public void setStoreImg(String storeImg) {
        this.storeImg = storeImg;
    }

    @Override
    public String toString() {
        return "Store{" +
                "name='" + storeName + '\'' +
                ", description='" + storeDescription + '\'' +
                ", rating='" + rating + '\'' +
                ", img='" + storeImg + '\'' + '}';
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }
}
