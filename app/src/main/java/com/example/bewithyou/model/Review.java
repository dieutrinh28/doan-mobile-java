package com.example.bewithyou.model;

public class Review {
    private String comment;
    private String date;
    private String rating;
    private String userId;

    public Review() {}

    public Review(String comment, String date, String rating, String userId) {
        this.comment = comment;
        this.date = date;
        this.rating = rating;
        this.userId = userId;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "Review{" +
                ", comment='" + comment + '\'' +
                ", date='" + date + '\'' +
                ", rating='" + rating + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
