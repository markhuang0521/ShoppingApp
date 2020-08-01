package com.ming.onlineshoppingapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ming.onlineshoppingapp.R;
import com.ming.onlineshoppingapp.SearchActivity;
import com.ming.onlineshoppingapp.ShopItemDetailActivity;
import com.ming.onlineshoppingapp.ShoppingCartActivity;
import com.ming.onlineshoppingapp.models.ShopItem;
import com.ming.onlineshoppingapp.ui.ShopItemAdapter;
import com.ming.onlineshoppingapp.utils.DbUtils;

import java.util.ArrayList;
import java.util.Collections;

import static com.ming.onlineshoppingapp.ShopItemDetailActivity.ITEM_KEY;

public class MainFragment extends Fragment implements ShopItemAdapter.ShopItemOnClickListener {
    private BottomNavigationView bottomNavigationView;
    private RecyclerView newItemsRecyclerView, popularItemsRecyclerView, suggestItemsRecyclerView;
    private ShopItemAdapter newItemsAdapter, popularItemsAdapter, suggestItemsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        initBottomNav();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<ShopItem> allItems = DbUtils.getInstance(getActivity()).getAllItem();
        Collections.sort(allItems, ShopItem.itemIdComparator);

        ArrayList<ShopItem> popularItemList = new ArrayList<>(allItems);
        Collections.sort(popularItemList, ShopItem.itemPopularityComparator);
        ArrayList<ShopItem> suggestItemList = new ArrayList<>(allItems);
        Collections.sort(suggestItemList, ShopItem.itemUserPointComparator);
        newItemsAdapter.setShopItemArrayList(allItems);
        popularItemsAdapter.setShopItemArrayList(popularItemList);
        suggestItemsAdapter.setShopItemArrayList(suggestItemList);

    }

    private void initBottomNav() {
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_bottom_cart:
                        startActivity(new Intent(getActivity(), ShoppingCartActivity.class));
                        break;
                    case R.id.menu_home:
                        break;
                    case R.id.menu_search:
                        startActivity(new Intent(getActivity(), SearchActivity.class));
                        break;


                }
                return false;
            }
        });
    }

    private void initView(View view) {
        bottomNavigationView = view.findViewById(R.id.nav_main_bottom);
        //recycler views
        newItemsRecyclerView = view.findViewById(R.id.recycler_new_items);
        popularItemsRecyclerView = view.findViewById(R.id.recycler_popular_items);
        suggestItemsRecyclerView = view.findViewById(R.id.recycler_suggest_items);

        //adapters
        newItemsAdapter = new ShopItemAdapter(getActivity(), this);
        popularItemsAdapter = new ShopItemAdapter(getActivity(), this);
        suggestItemsAdapter = new ShopItemAdapter(getActivity(), this);

        //recycler setup with adapter
        newItemsRecyclerView.setAdapter(newItemsAdapter);
        newItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularItemsRecyclerView.setAdapter(popularItemsAdapter);
        popularItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        suggestItemsRecyclerView.setAdapter(suggestItemsAdapter);
        suggestItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        //setup item list for each adapter
        ArrayList<ShopItem> allItems = DbUtils.getInstance(getActivity()).getAllItem();
        Collections.sort(allItems, ShopItem.itemIdComparator);

        ArrayList<ShopItem> popularItemList = new ArrayList<>(allItems);
        Collections.sort(popularItemList, ShopItem.itemPopularityComparator);
        ArrayList<ShopItem> suggestItemList = new ArrayList<>(allItems);
        Collections.sort(suggestItemList, ShopItem.itemUserPointComparator);
        newItemsAdapter.setShopItemArrayList(allItems);
        popularItemsAdapter.setShopItemArrayList(popularItemList);
        suggestItemsAdapter.setShopItemArrayList(suggestItemList);


    }

    @Override
    public void onClick(ShopItem item) {
        Intent intent = new Intent(getActivity(), ShopItemDetailActivity.class);
        intent.putExtra(ITEM_KEY, item);
        startActivity(intent);


    }
}
