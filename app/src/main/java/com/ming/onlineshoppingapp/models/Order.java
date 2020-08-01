package com.ming.onlineshoppingapp.models;

import com.ming.onlineshoppingapp.utils.DbUtils;

import java.util.ArrayList;

public class Order {
    private int uid;

    private ArrayList<ShopItem> items;
    private String address;
    private String phone;
    private String zipcode;
    private String email;
    private double priceTotal;
    private String paymentMethod;
    private boolean paymentSuccess;
    private static int count = 1;

    public Order() {
    }

    public Order(ArrayList<ShopItem> items, String address, String phone, String zipcode, String email, double priceTotal) {
        uid = count++;
        this.items = items;
        this.address = address;
        this.phone = phone;
        this.zipcode = zipcode;
        this.email = email;
        this.priceTotal = priceTotal;
        this.paymentMethod = "";
        this.paymentSuccess = false;
    }

    public double getOrderTotal() {
        double total = 0;
        if (items.size() > 0) {
            for (ShopItem item : items) {
                total += item.getPrice();
            }

        }
        return total;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public ArrayList<ShopItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ShopItem> items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isPaymentSuccess() {
        return paymentSuccess;
    }

    public void setPaymentSuccess(boolean paymentSuccess) {
        this.paymentSuccess = paymentSuccess;
    }
}
