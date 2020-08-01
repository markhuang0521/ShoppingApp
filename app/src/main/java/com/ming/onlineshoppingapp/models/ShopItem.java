package com.ming.onlineshoppingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ShopItem implements Parcelable {
    private int id;
    private String name;
    private String desc;
    private String imageUrl;
    private String category;
    private double price;
    private int storage;
    private int rating;
    private int userPoint;
    private int popularityPoint;
    private ArrayList<Review> reviewList;
    private static int count = 0;

    public ShopItem(String name, String desc, String imageUrl, String category, double price, int storage) {
        id = ++count;


        this.name = name;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.category = category;
        this.price = price;
        this.storage = storage;
        rating = 0;
        userPoint = 0;
        popularityPoint = 0;
        reviewList = new ArrayList<>();
    }

    public static Comparator<ShopItem> itemIdComparator = new Comparator<ShopItem>() {

        public int compare(ShopItem item1, ShopItem item2) {
            int id1 = item1.getId();
            int id2 = item2.getId();

            //descending order
            return id2 - id1;

        }
    };
    public static Comparator<ShopItem> itemPopularityComparator = new Comparator<ShopItem>() {

        public int compare(ShopItem item1, ShopItem item2) {
            int id1 = item1.getPopularityPoint();
            int id2 = item2.getPopularityPoint();

            //descending order
            return id2 - id1;

        }
    };
    public static Comparator<ShopItem> itemUserPointComparator = new Comparator<ShopItem>() {

        public int compare(ShopItem item1, ShopItem item2) {
            int id1 = item1.getUserPoint();
            int id2 = item2.getUserPoint();

            //descending order
            return id2 - id1;

        }
    };

    protected ShopItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        desc = in.readString();
        imageUrl = in.readString();
        category = in.readString();
        price = in.readDouble();
        storage = in.readInt();
        rating = in.readInt();
        userPoint = in.readInt();
        popularityPoint = in.readInt();
    }

    public static final Creator<ShopItem> CREATOR = new Creator<ShopItem>() {
        @Override
        public ShopItem createFromParcel(Parcel in) {
            return new ShopItem(in);
        }

        @Override
        public ShopItem[] newArray(int size) {
            return new ShopItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(int userPoint) {
        this.userPoint = userPoint;
    }

    public int getPopularityPoint() {
        return popularityPoint;
    }

    public void setPopularityPoint(int popularityPoint) {
        this.popularityPoint = popularityPoint;
    }

    public ArrayList<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(ArrayList<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopItem shopItem = (ShopItem) o;
        return Double.compare(shopItem.price, price) == 0 &&
                name.equals(shopItem.name) &&
                category.equals(shopItem.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(desc);
        parcel.writeString(imageUrl);
        parcel.writeString(category);
        parcel.writeDouble(price);
        parcel.writeInt(storage);
        parcel.writeInt(rating);
        parcel.writeInt(userPoint);
        parcel.writeInt(popularityPoint);
    }
}
