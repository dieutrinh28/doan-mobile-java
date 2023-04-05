package com.example.bewithyou;

public class Store {
    private String storeName;
    private String storeDescription;
    private String rating;
    private String storeImg;

    public Store(){};

    public Store(String storeName, String storeDescription, String rating, String storeImg) {
        this.storeName = storeName;
        this.storeDescription = storeDescription;
        this.rating = rating;
        this.storeImg = storeImg;
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
}
