package com.ming.onlineshoppingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ming.onlineshoppingapp.models.Category;
import com.ming.onlineshoppingapp.models.Review;
import com.ming.onlineshoppingapp.models.ShopItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DbUtils {
    private static final String CART_KEY = "shopping cart";
    private static final String CATEGORY_KEY = "category";
    private static DbUtils instance;
    private SharedPreferences sharedPreferences;
    private static int ORDER_ID = 1;

    private static final String DB_NAME = "database";
    private static final String ALL_ITEM_KEY = "all items";
    private static Gson gson = new Gson();
    private static SharedPreferences.Editor editor;

    private static Type ShopItemType = new TypeToken<ArrayList<ShopItem>>() {
    }.getType();

    private DbUtils(Context context) {

        sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (getAllItem() == null) {
            initAllItem();
        }

    }

    public static DbUtils getInstance(Context context) {
        if (instance == null) {
            instance = new DbUtils(context);
        }
        return instance;

    }

    private void initAllItem() {
        ArrayList<ShopItem> shopItems = new ArrayList<>();
        ShopItem milk = new ShopItem("Milk",
                "Lactaid 100% Lactose Free 2% Reduced Fat Milk",
                "https://www.kroger.com/product/images/large/front/0004138309010",
                Category.DRINK_KEY, 5.99,
                100);
        shopItems.add(milk);
        ShopItem iceCream = new ShopItem("Ice Cream",
                "Haagen-Dazs Vanilla Ice Cream",
                "https://target.scene7.com/is/image/Target/GUEST_e2578ce3-f265-451f-ae8e-55eae1f1dc25?wid=488&hei=488&fmt=pjpeg",
                Category.FOOD_KEY, 4.99,
                100);
        shopItems.add(iceCream);
        ShopItem soda = new ShopItem("Soda",
                "The cool, bubbly taste of Coca-Cola Classic is the perfect drink with friends.",
                "https://media.officedepot.com/images/t_large,f_auto/products/208206/Coca-Cola-Classic-Soda-12-Oz",
                Category.DRINK_KEY, 1.99,
                100);
        shopItems.add(soda);
        ShopItem phone = new ShopItem(
                "Google Pixel 4",
                "The Pixel 4 64GB Smartphone (Unlocked, Just Black) from Google is designed to provide a more intelligent and intuitive mobile experience.",
                "https://static.bhphoto.com/images/images2500x2500/1571097351_1507474.jpg",
                Category.ELECTRONIC_KEY, 638.95,
                999);
        shopItems.add(phone);
        ShopItem jacket = new ShopItem("Man's lightweight jacket",
                "This jacket is a sleek, modern essential for your wardrobe. Designed with a high stand collar and side zip pockets.",
                "https://belk.scene7.com/is/image/Belk?layer=0&src=3202272_40ZO200_A_010_T10L00&layer=comp&$DWP_PRODUCT_PDP_MOBILE_L$",
                Category.CLOTHING_KEY, 98.01,
                10);
        shopItems.add(jacket);


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ALL_ITEM_KEY, gson.toJson(shopItems));
        editor.putString(CART_KEY, gson.toJson(new ArrayList<ShopItem>()));
        editor.putString(CATEGORY_KEY, gson.toJson(Category.getAllCategory()));
        editor.apply();


    }

    public ArrayList<ShopItem> getAllItem() {
        return gson.fromJson(sharedPreferences.getString(ALL_ITEM_KEY, null), ShopItemType);

    }

    public ArrayList<ShopItem> getShoppingCart() {
        return gson.fromJson(sharedPreferences.getString(CART_KEY, null), ShopItemType);

    }


    public void addItemToCart(ShopItem item) {
        ArrayList<ShopItem> carts = getShoppingCart();
        Set<ShopItem> sets = new HashSet<>(carts);
        sets.add(item);
        ArrayList<ShopItem> newCart = new ArrayList<>(sets);
        editor.putString(CART_KEY, gson.toJson(newCart));
        editor.apply();

    }

    public boolean deleteItemtoCart(ShopItem item) {
        ArrayList<ShopItem> carts = getShoppingCart();
        for (ShopItem cart : carts) {
            if (cart.getId() == item.getId()) {

                carts.remove(cart);
                editor.putString(CART_KEY, gson.toJson(carts));
                editor.apply();
                return true;
            }
        }

        return false;


    }

    public void emptyShoppingCart() {
        editor.putString(CART_KEY, gson.toJson(new ArrayList<ShopItem>()));
        editor.apply();

    }

    public void updatePopularityPoint() {
        ArrayList<ShopItem> carts = getShoppingCart();
        ArrayList<ShopItem> allItems = getAllItem();

        for (ShopItem cart : carts) {
            for (ShopItem item : allItems) {
                if (item.equals(cart)) {
                    item.setPopularityPoint(item.getPopularityPoint() + 1);
                }

            }
        }
        editor.putString(ALL_ITEM_KEY, gson.toJson(allItems));
        editor.apply();

    }

    public void updateUserPoint(ShopItem currentItem, int point) {
        ArrayList<ShopItem> allItems = getAllItem();


        for (ShopItem item : allItems) {
            if (item.equals(currentItem)) {
                item.setUserPoint(item.getUserPoint() + point);
            }


        }
        editor.putString(ALL_ITEM_KEY, gson.toJson(allItems));
        editor.apply();

    }


    public ArrayList<String> getAllCategory() {
        return gson.fromJson(sharedPreferences.getString(CATEGORY_KEY, null), new TypeToken<ArrayList<String>>() {
        }.getType());

    }


    public void addReview(Review review) {
        ArrayList<ShopItem> allItems = getAllItem();
        for (ShopItem item : allItems) {
            if (item.getId() == review.getShopItemId()) {
                ArrayList<Review> reviewArrayList = item.getReviewList();
                reviewArrayList.add(review);
                item.setReviewList(reviewArrayList);


            }
        }
        updateItemList(allItems);


    }

    public ArrayList<Review> getReviewsFromItemId(int itemId) {
        ArrayList<ShopItem> allItems = getAllItem();
        ArrayList<Review> reviewArrayList = new ArrayList<>();
        for (ShopItem item : allItems) {
            if (item.getId() == itemId) {
                reviewArrayList = item.getReviewList();

            }
        }
        return reviewArrayList;
    }


    public void updateItemList(ArrayList<ShopItem> shopItems) {
        editor.putString(ALL_ITEM_KEY, gson.toJson(shopItems));
        editor.apply();
    }

    public void changeRating(int itemId, int rating) {
        ArrayList<ShopItem> shopItems = getAllItem();
        for (ShopItem item : shopItems) {
            if (item.getId() == itemId) {
                item.setRating(rating);
            }

        }
        editor.putString(ALL_ITEM_KEY, gson.toJson(shopItems));
        editor.apply();


    }

    public double getItemTotal() {
        double total = 0;
        ArrayList<ShopItem> cart = getShoppingCart();
        if (cart.size() > 0) {
            for (ShopItem item : cart) {
                total += item.getPrice();

            }
        }
        return total;
    }

    public ShopItem getItemById(int id) {
        ArrayList<ShopItem> shopItems = getAllItem();
        for (ShopItem item : shopItems) {
            if (item.getId() == id) {
                return item;
            }

        }
        return null;

    }

    public Set<ShopItem> getItemsByName(String itemName) {
        ArrayList<ShopItem> shopItems = getAllItem();

        Set<ShopItem> results = new HashSet<>();
        for (ShopItem item : shopItems) {
            if (item.getName().equalsIgnoreCase(itemName.trim())) {
                results.add(item);
            }
            String[] names = item.getName().split(" ");

            for (String name : names) {
                if (name.equalsIgnoreCase(itemName.trim())) {
                    results.add(item);
                }
            }

        }
        return results;

    }

    public ArrayList<ShopItem> getItemsByCategory(String category) {
        ArrayList<ShopItem> shopItems = getAllItem();
        ArrayList<ShopItem> result = new ArrayList<>();
        for (ShopItem item : shopItems) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                result.add(item);
            }

        }
        return result;

    }


    public void updateAll() {
        initAllItem();
    }

    public int getOrderId() {
        return ORDER_ID++;
    }


}
