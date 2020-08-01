package com.ming.onlineshoppingapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ming.onlineshoppingapp.R;
import com.ming.onlineshoppingapp.ShopItemDetailActivity;
import com.ming.onlineshoppingapp.models.Review;
import com.ming.onlineshoppingapp.models.ShopItem;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddReviewDialog extends DialogFragment {

    public interface AddReviewOnclickLIstner {
        void addReviewResult(Review review);
    }


    private TextView itemName;
    private EditText etUsername, etReview;
    private Button btnAddReview;
    private AddReviewOnclickLIstner addReviewOnclickLIstner;


    @NonNull
    @Override

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_review, null);
        itemName = view.findViewById(R.id.tv_item_name);
        etUsername = view.findViewById(R.id.et_username);
        etReview = view.findViewById(R.id.et_dialog_review);
        btnAddReview = view.findViewById(R.id.btn_add_review);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            final ShopItem item = bundle.getParcelable(ShopItemDetailActivity.ITEM_KEY);
            if (item != null) {
                btnAddReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (validateInput()) {
                            itemName.setText(item.getName());
                            String userName = etUsername.getText().toString();
                            String review = etReview.getText().toString();
                            String date = getcurrentDate();
                            addReviewOnclickLIstner = (AddReviewOnclickLIstner) getActivity();
                            Review newReview = new Review(item.getId(), userName, review, date);
                            addReviewOnclickLIstner.addReviewResult(newReview);

                            dismiss();
                        }
                    }
                });

            }
        }


        return builder.create();
    }

    private String getcurrentDate() {
        Date date = Calendar.getInstance().getTime();

        return "Review By: " + DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
    }

    private boolean validateInput() {
        if (etUsername.getText().toString().isEmpty()) {
            etUsername.setError("empty user not allow");
            return false;
        }
        if (etReview.getText().toString().isEmpty()) {
            etReview.setError("empty review not allow");
            return false;
        }
        return true;
    }

}
