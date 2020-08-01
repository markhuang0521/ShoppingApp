package com.ming.onlineshoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ming.onlineshoppingapp.fragment.FirstCartFragment;

public class ShoppingCartActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        initBottomNav();
        setSupportActionBar(toolbar);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new FirstCartFragment());
        fragmentTransaction.commit();
    }


    private void initBottomNav() {
        toolbar = findViewById(R.id.toolbar_cart);
        bottomNavigationView = findViewById(R.id.bottom_nav_cart);
        bottomNavigationView.setSelectedItemId(R.id.menu_bottom_cart);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_bottom_cart:
                        break;
                    case R.id.menu_home:
                        startActivity(new Intent(ShoppingCartActivity.this, MainActivity.class));
                        break;
                    case R.id.menu_search:
                        startActivity(new Intent(ShoppingCartActivity.this, SearchActivity.class));
                        break;


                }
                return false;
            }
        });
    }
}
