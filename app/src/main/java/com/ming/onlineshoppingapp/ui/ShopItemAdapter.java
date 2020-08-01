package com.ming.onlineshoppingapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ming.onlineshoppingapp.R;
import com.ming.onlineshoppingapp.models.ShopItem;

import java.util.ArrayList;

public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.ViewHolder> {
    private ArrayList<ShopItem> shopItemArrayList;
    private ShopItemOnClickListener onClickListener;
    private Context context;

    public interface ShopItemOnClickListener {
        void onClick(ShopItem item);
    }

    public ShopItemAdapter(Context context, ShopItemOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShopItem shopItem = shopItemArrayList.get(position);
        holder.price.setText("$" + String.valueOf(shopItem.getPrice()));
        holder.name.setText(shopItem.getName());
        Glide.with(context).asBitmap().load(shopItem.getImageUrl()).centerCrop().into(holder.image);


    }

    @Override
    public int getItemCount() {
        return shopItemArrayList != null ? shopItemArrayList.size() : 0;
    }

    public void setShopItemArrayList(ArrayList<ShopItem> shopItemArrayList) {
        this.shopItemArrayList = shopItemArrayList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView price, name;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.tv_item_price);
            name = itemView.findViewById(R.id.tv_item_name);
            image = itemView.findViewById(R.id.iv_item_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ShopItem shopItem = shopItemArrayList.get(getAdapterPosition());
            switch (view.getId()) {
                case R.id.card_shop_item:
                    onClickListener.onClick(shopItem);
            }

        }
    }
}
