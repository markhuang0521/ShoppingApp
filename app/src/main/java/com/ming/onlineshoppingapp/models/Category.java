package com.ming.onlineshoppingapp.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Category {
    public static final String FOOD_KEY = "Food";
    public static final String DRINK_KEY = "Drink";
    public static final String SNACK_KEY = "Snack";
    public static final String ELECTRONIC_KEY = "Electronic";
    public static final String CLOTHING_KEY = "Clothing";
    public static List<String> allCategory = new ArrayList<>(Arrays.asList(FOOD_KEY, DRINK_KEY, SNACK_KEY, ELECTRONIC_KEY, CLOTHING_KEY));

    public static List<String> getAllCategory() {

        return allCategory;

    }


}
