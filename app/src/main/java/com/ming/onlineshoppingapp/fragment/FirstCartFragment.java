package com.ming.onlineshoppingapp.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ming.onlineshoppingapp.R;
import com.ming.onlineshoppingapp.ShopItemDetailActivity;
import com.ming.onlineshoppingapp.models.InfoCartFragment;
import com.ming.onlineshoppingapp.models.ShopItem;
import com.ming.onlineshoppingapp.ui.ShopItemAdapter;
import com.ming.onlineshoppingapp.ui.ShoppingCartAdapter;
import com.ming.onlineshoppingapp.utils.DbUtils;

import java.util.ArrayList;

import static com.ming.onlineshoppingapp.ShopItemDetailActivity.ITEM_KEY;

public class FirstCartFragment extends Fragment implements ShoppingCartAdapter.CartItemListener {
    private TextView tvPriceTotal, tvNoItem;
    private RecyclerView recyclerCart;
    private Button btnOrder;
    private RelativeLayout layoutCartItem;
    private ShoppingCartAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1st_cart, container, false);
        tvPriceTotal = view.findViewById(R.id.tv_cart_total);
        tvNoItem = view.findViewById(R.id.tv_cart_no_item);
        layoutCartItem = view.findViewById(R.id.layout_cart_item);
        btnOrder = view.findViewById(R.id.btn_cart_order);
        recyclerCart = view.findViewById(R.id.recycler_cart);
        adapter = new ShoppingCartAdapter(this);
        adapter.setShopItemArrayList(DbUtils.getInstance(getActivity()).getShoppingCart());

        tvPriceTotal.setText(String.valueOf(DbUtils.getInstance(getActivity()).getItemTotal()));
        recyclerCart.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerCart.setAdapter(adapter);
        showCartItem();

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new InfoCartFragment());
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onDelete(final ShopItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setMessage("are you sure to delete this item")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (DbUtils.getInstance(getActivity()).deleteItemtoCart(item)) {
                            Toast.makeText(getActivity(), "delete complete", Toast.LENGTH_SHORT).show();
                            adapter.setShopItemArrayList(DbUtils.getInstance(getActivity()).getShoppingCart());
                            adapter.notifyDataSetChanged();
                            showCartItem();
                            tvPriceTotal.setText(String.valueOf(DbUtils.getInstance(getActivity()).getItemTotal()));


                        } else {
                            Toast.makeText(getActivity(), "fail to delete this item", Toast.LENGTH_SHORT).show();
                        }


                    }
                }).setNegativeButton("No", null);
        builder.create().show();

    }


    @Override
    public void onCLick(ShopItem item) {
        Intent intent = new Intent(getActivity(), ShopItemDetailActivity.class);
        intent.putExtra(ITEM_KEY, item);
        startActivity(intent);
    }

    private void showCartItem() {

        if (adapter.getItemCount() > 0) {
            layoutCartItem.setVisibility(View.VISIBLE);
            tvNoItem.setVisibility(View.GONE);
        } else {
            layoutCartItem.setVisibility(View.GONE);
            tvNoItem.setVisibility(View.VISIBLE);
        }
    }
}
