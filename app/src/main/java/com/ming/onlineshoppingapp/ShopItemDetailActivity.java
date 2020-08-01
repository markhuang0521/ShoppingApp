package com.ming.onlineshoppingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.ming.onlineshoppingapp.models.Review;
import com.ming.onlineshoppingapp.models.ShopItem;
import com.ming.onlineshoppingapp.ui.AddReviewDialog;
import com.ming.onlineshoppingapp.ui.ReviewAdapter;
import com.ming.onlineshoppingapp.utils.DbUtils;
import com.ming.onlineshoppingapp.utils.TrackUserTime;

import java.util.ArrayList;

public class ShopItemDetailActivity extends AppCompatActivity implements AddReviewDialog.AddReviewOnclickLIstner {
    private static final String TAG = "ShopItemDetailActivity";
    public static final String ITEM_KEY = "shop item";
    private RecyclerView recyclerReviews;
    private ReviewAdapter reviewAdapter;
    private TextView tvName, tvPrice, teDesc, tvAddReview;
    private ImageView ivImage,
            firstEmpStar, secondEmpStar, thirdEmpStar,
            firstFillStar, secondFillStar, thirdFillStar;
    private Button btnAddToCart;
    private RelativeLayout firstStarLayout, secondStarLayout, thirdStarLayout;
    private ShopItem incomingItem;
    private MaterialToolbar toolbar;
    private ArrayList<Review> reviewArrayList;
    private boolean isBound;
    private TrackUserTime userService;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TrackUserTime.LocalBinder binder = (TrackUserTime.LocalBinder) iBinder;
            userService = binder.getService();
            isBound = true;
            userService.setShopItem(incomingItem);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_item_detail);
        initViews();
        setSupportActionBar(toolbar);
        incomingItem = getIntent().getParcelableExtra(ITEM_KEY);
        if (incomingItem != null) {
            DbUtils.getInstance(this).updateUserPoint(incomingItem, 1);
            tvName.setText(incomingItem.getName());
            teDesc.setText(incomingItem.getDesc());
            tvPrice.setText(String.valueOf(incomingItem.getPrice()));
            Glide.with(this).asBitmap().load(incomingItem.getImageUrl()).into(ivImage);
            ArrayList<Review> reviewArrayList = DbUtils.getInstance(this).getReviewsFromItemId(incomingItem.getId());

            reviewAdapter = new ReviewAdapter(this);
            reviewAdapter.setReviewArrayList(reviewArrayList);
            recyclerReviews.setAdapter(reviewAdapter);

            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DbUtils.getInstance(ShopItemDetailActivity.this).addItemToCart(incomingItem);
                    Toast.makeText(ShopItemDetailActivity.this, "item added ", Toast.LENGTH_SHORT).show();
                }
            });
            tvAddReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //todo add new view to the cart

                    AddReviewDialog reviewDialog = new AddReviewDialog();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(ITEM_KEY, incomingItem);
                    reviewDialog.setArguments(bundle);
                    reviewDialog.show(getSupportFragmentManager(), "add review");

                }
            });
            handleRating();

        } else {
            Toast.makeText(this, "no item available", Toast.LENGTH_SHORT).show();
        }

    }

    private void handleRating() {
        switch (incomingItem.getRating()) {
            case 0:
                firstEmpStar.setVisibility(View.VISIBLE);
                secondEmpStar.setVisibility(View.VISIBLE);
                thirdEmpStar.setVisibility(View.VISIBLE);
                firstFillStar.setVisibility(View.GONE);
                secondFillStar.setVisibility(View.GONE);
                thirdFillStar.setVisibility(View.GONE);

            case 1:
                firstEmpStar.setVisibility(View.GONE);
                secondEmpStar.setVisibility(View.VISIBLE);
                thirdEmpStar.setVisibility(View.VISIBLE);
                firstFillStar.setVisibility(View.VISIBLE);
                secondFillStar.setVisibility(View.GONE);
                thirdFillStar.setVisibility(View.GONE);

                break;
            case 2:
                firstEmpStar.setVisibility(View.GONE);
                secondEmpStar.setVisibility(View.GONE);
                thirdEmpStar.setVisibility(View.VISIBLE);
                firstFillStar.setVisibility(View.VISIBLE);
                secondFillStar.setVisibility(View.VISIBLE);
                thirdFillStar.setVisibility(View.GONE);
                break;
            case 3:
                firstEmpStar.setVisibility(View.GONE);
                secondEmpStar.setVisibility(View.GONE);
                thirdEmpStar.setVisibility(View.GONE);
                firstFillStar.setVisibility(View.VISIBLE);
                secondFillStar.setVisibility(View.VISIBLE);
                thirdFillStar.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        firstStarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (incomingItem.getRating() != 1) {
                    incomingItem.setRating(1);
                    DbUtils.getInstance(ShopItemDetailActivity.this).changeRating(incomingItem.getId(), 1);
                    handleRating();
                }
            }
        });
        secondStarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (incomingItem.getRating() != 2) {
                    incomingItem.setRating(2);

                    DbUtils.getInstance(ShopItemDetailActivity.this).changeRating(incomingItem.getId(), 2);
                    handleRating();
                }
            }
        });
        thirdStarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (incomingItem.getRating() != 3) {
                    incomingItem.setRating(3);

                    DbUtils.getInstance(ShopItemDetailActivity.this).changeRating(incomingItem.getId(), 3);
                    handleRating();
                }
            }
        });
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar_detail);
        tvAddReview = findViewById(R.id.tv_detail_add_review);
        tvName = findViewById(R.id.tv_detail_name);
        tvPrice = findViewById(R.id.tv_detail_price);
        teDesc = findViewById(R.id.tv_detail_desc);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        ivImage = findViewById(R.id.iv_detail_image);
        firstEmpStar = findViewById(R.id.iv_1st_empty_star);
        secondEmpStar = findViewById(R.id.iv_2nd_empty_star);
        thirdEmpStar = findViewById(R.id.iv_3rd_empty_star);
        firstFillStar = findViewById(R.id.iv_1st_fill_star);
        secondFillStar = findViewById(R.id.iv_2nd_fill_star);
        thirdFillStar = findViewById(R.id.iv_3rd_fill_star);
        firstStarLayout = findViewById(R.id.layout_first_star);
        secondStarLayout = findViewById(R.id.layout_2nd_star);
        thirdStarLayout = findViewById(R.id.layout_3rd_star);
        recyclerReviews = findViewById(R.id.recycler_review);
        recyclerReviews.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void addReviewResult(Review review) {
        DbUtils.getInstance(this).addReview(review);
        reviewArrayList = DbUtils.getInstance(this).getReviewsFromItemId(review.getShopItemId());
        reviewAdapter.setReviewArrayList(reviewArrayList);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ShopItemDetailActivity.this, MainActivity.class));
    }
}
