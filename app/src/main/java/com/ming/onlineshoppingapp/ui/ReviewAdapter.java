package com.ming.onlineshoppingapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ming.onlineshoppingapp.R;
import com.ming.onlineshoppingapp.models.Review;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private ArrayList<Review> reviewArrayList;
    private Context context;

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviewArrayList.get(position);
        holder.date.setText(review.getDate());
        holder.username.setText(review.getUserName());
        holder.review.setText(review.getReviewText());

    }

    @Override
    public int getItemCount() {
        return reviewArrayList != null ? reviewArrayList.size() : 0;
    }

    public void setReviewArrayList(ArrayList<Review> reviewArrayList) {
        this.reviewArrayList = reviewArrayList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView username, review, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.tv_review_username);
            review = itemView.findViewById(R.id.tv_review);
            date = itemView.findViewById(R.id.tv_review_date);
        }
    }
}
