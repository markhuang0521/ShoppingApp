package com.ming.onlineshoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.ming.onlineshoppingapp.fragment.MainFragment;
import com.ming.onlineshoppingapp.ui.AllCateogryDialog;

import static com.ming.onlineshoppingapp.ui.AllCateogryDialog.CALLING_ACTIVITY_KEY;
import static com.ming.onlineshoppingapp.ui.AllCateogryDialog.CATEGORY_KEY;
import static com.ming.onlineshoppingapp.ui.AllCateogryDialog.MAIN_ACTIIVTY_KEY;

public class MainActivity extends AppCompatActivity implements AllCateogryDialog.GetCategoryListner {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //testing updated data from sp
//        DbUtils.getInstance(this).updateAll();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_cart:
                        startActivity(new Intent(MainActivity.this, ShoppingCartActivity.class));
                        break;
                    case R.id.menu_about_us:
                        break;
                    case R.id.menu_category:
                        AllCateogryDialog dialog = new AllCateogryDialog();
                        Bundle bundle = new Bundle();
                        bundle.putString(CALLING_ACTIVITY_KEY, MAIN_ACTIIVTY_KEY);
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(), "all category");
                        break;
                    case R.id.menu_licences:
                        break;
                    case R.id.menu_terms:
                        break;
                }
                return false;
            }
        });
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new MainFragment());
        fragmentTransaction.commit();
    }

    private void initView() {
        drawerLayout = findViewById(R.id.layout_drawer);
        toolbar = findViewById(R.id.toolbar_main);
        navigationView = findViewById(R.id.nav_main);


    }

    @Override
    public void getCategoryResult(String category) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(CATEGORY_KEY, category);
        startActivity(intent);
    }
}
