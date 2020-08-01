package com.ming.onlineshoppingapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.ming.onlineshoppingapp.R;
import com.ming.onlineshoppingapp.models.ShopItem;
import com.ming.onlineshoppingapp.utils.DbUtils;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {
    public interface CartItemListener {
        void onDelete(ShopItem item);

        void onCLick(ShopItem item);
    }

    private ArrayList<ShopItem> shopItemArrayList;
    private CartItemListener listener;

    public ShoppingCartAdapter(CartItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShopItem item = shopItemArrayList.get(position);
        holder.name.setText(item.getName());
        holder.price.setText(String.valueOf(item.getPrice()));

    }

    @Override
    public int getItemCount() {
        return shopItemArrayList != null ? shopItemArrayList.size() : 0;
    }

    public void setShopItemArrayList(ArrayList<ShopItem> shopItemArrayList) {
        this.shopItemArrayList = shopItemArrayList;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView price, name;
        Button btnDelete;
        MaterialCardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.tv_cart_price);
            name = itemView.findViewById(R.id.tv_cart_name);
            btnDelete = itemView.findViewById(R.id.btn_cart_delete);
            cardView = itemView.findViewById(R.id.card_cart);
            btnDelete.setOnClickListener(this);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ShopItem item = shopItemArrayList.get(getAdapterPosition());
            switch (view.getId()) {
                case R.id.btn_cart_delete:
                    listener.onDelete(item);
                    break;
                case R.id.card_cart:
                    listener.onCLick(item);
                    break;
            }
        }
    }
}
