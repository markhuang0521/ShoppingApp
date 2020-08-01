package com.ming.onlineshoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ming.onlineshoppingapp.models.Category;
import com.ming.onlineshoppingapp.models.ShopItem;
import com.ming.onlineshoppingapp.ui.AllCateogryDialog;
import com.ming.onlineshoppingapp.ui.ShopItemAdapter;
import com.ming.onlineshoppingapp.utils.DbUtils;

import java.util.ArrayList;
import java.util.Set;

import static com.ming.onlineshoppingapp.ui.AllCateogryDialog.*;
import static com.ming.onlineshoppingapp.ShopItemDetailActivity.ITEM_KEY;

public class SearchActivity extends AppCompatActivity implements ShopItemAdapter.ShopItemOnClickListener, GetCategoryListner {
    private TextView firstCateory, secondCateory, thirdCateory, allCateogry;
    private MaterialToolbar toolbar;
    private EditText etSearch;
    private RecyclerView recyclerSearch;
    private BottomNavigationView bottomNavigationView;
    private ImageView btnSearch;
    private ShopItemAdapter shopItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initBottomNav();
        initViews();
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(CATEGORY_KEY)) {
            etSearch.setText(intent.getStringExtra(CATEGORY_KEY));
            initSearchByCategory();
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSearchByName();
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                initSearchByName();

            }
        });
        firstCateory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<ShopItem> items = DbUtils.getInstance(SearchActivity.this).getItemsByCategory(firstCateory.getText().toString());
                shopItemAdapter.setShopItemArrayList(items);
            }
        });
        secondCateory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<ShopItem> items = DbUtils.getInstance(SearchActivity.this).getItemsByCategory(secondCateory.getText().toString());
                shopItemAdapter.setShopItemArrayList(items);
            }
        });
        thirdCateory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<ShopItem> items = DbUtils.getInstance(SearchActivity.this).getItemsByCategory(thirdCateory.getText().toString());
                shopItemAdapter.setShopItemArrayList(items);
            }
        });
        allCateogry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllCateogryDialog dialog = new AllCateogryDialog();
                Bundle bundle = new Bundle();
                bundle.putString(CALLING_ACTIVITY_KEY, SEARCH_ACTIIVTY_KEY);
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "all category");
            }
        });

    }

    private void initSearchByName() {
        String itemName = etSearch.getText().toString();
        if (!itemName.isEmpty()) {
            Set<ShopItem> result = DbUtils.getInstance(this).getItemsByName(itemName);
            ArrayList<ShopItem> resultArr = new ArrayList<>(result);
            shopItemAdapter.setShopItemArrayList(resultArr);
            shopItemAdapter.notifyDataSetChanged();

        }
    }

    private void initSearchByCategory() {
        String category = etSearch.getText().toString();
        if (!category.isEmpty()) {
            ArrayList<ShopItem> result = DbUtils.getInstance(this).getItemsByCategory(category);
            ArrayList<ShopItem> resultArr = new ArrayList<>(result);
            shopItemAdapter.setShopItemArrayList(resultArr);
            shopItemAdapter.notifyDataSetChanged();

        }
    }

    private void initViews() {
        firstCateory = findViewById(R.id.tv_1st_category);
        secondCateory = findViewById(R.id.tv_2nd_category);
        thirdCateory = findViewById(R.id.tv_3rd_category);
        firstCateory.setText(Category.FOOD_KEY);
        secondCateory.setText(Category.ELECTRONIC_KEY);
        thirdCateory.setText(Category.CLOTHING_KEY);
        allCateogry = findViewById(R.id.tv_all_category);
        toolbar = findViewById(R.id.search_toolbar);
        etSearch = findViewById(R.id.et_searchbox);
        recyclerSearch = findViewById(R.id.recycler_search_item);
        shopItemAdapter = new ShopItemAdapter(this, this);
        recyclerSearch.setAdapter(shopItemAdapter);
        recyclerSearch.setLayoutManager(new LinearLayoutManager(this));

        btnSearch = findViewById(R.id.btn_search);
    }

    private void initBottomNav() {
        bottomNavigationView = findViewById(R.id.nav_search_bottom);
        bottomNavigationView.setSelectedItemId(R.id.menu_search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_bottom_cart:
                        break;
                    case R.id.menu_home:
                        startActivity(new Intent(SearchActivity.this, MainActivity.class));

                        break;
                    case R.id.menu_search:
                        break;


                }
                return false;
            }
        });
    }

    @Override
    public void onClick(ShopItem item) {
        Intent intent = new Intent(SearchActivity.this, ShopItemDetailActivity.class);
        intent.putExtra(ITEM_KEY, item);
        startActivity(intent);

    }

    @Override
    public void getCategoryResult(String category) {
        shopItemAdapter.setShopItemArrayList(DbUtils.getInstance(this).getItemsByCategory(category));


    }
}
