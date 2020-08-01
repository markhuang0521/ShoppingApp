package com.ming.onlineshoppingapp.models;

public class Review {
    private int ShopItemId;
    private String userName;
    private String reviewText;
    private String date;

    public Review(int shopItemId, String userName, String reviewText, String date) {
        ShopItemId = shopItemId;
        this.userName = userName;
        this.reviewText = reviewText;
        this.date = date;
    }

    public int getShopItemId() {
        return ShopItemId;
    }

    public void setShopItemId(int shopItemId) {
        ShopItemId = shopItemId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
