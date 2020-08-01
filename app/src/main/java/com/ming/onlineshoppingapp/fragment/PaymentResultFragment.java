package com.ming.onlineshoppingapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ming.onlineshoppingapp.MainActivity;
import com.ming.onlineshoppingapp.R;
import com.ming.onlineshoppingapp.models.Order;
import com.ming.onlineshoppingapp.utils.DbUtils;

import java.lang.reflect.Type;

import static com.ming.onlineshoppingapp.models.InfoCartFragment.ORDER_KEY;

public class PaymentResultFragment extends Fragment {
    private TextView tvPaymentresult;
    private Button btnHome;
    private Gson gson = new Gson();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_payment, container, false);
        tvPaymentresult = view.findViewById(R.id.tv_result_status);
        btnHome = view.findViewById(R.id.btn_result_home);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String jsonOrder = bundle.getString(ORDER_KEY);
            Type orderType = new TypeToken<Order>() {
            }.getType();
            Order order = gson.fromJson(jsonOrder, orderType);
            if (order != null) {
                if (order.isPaymentSuccess()) {
                    tvPaymentresult.setText("payment complete your items will arrive soon");
                    DbUtils.getInstance(getActivity()).updatePopularityPoint();
                    DbUtils.getInstance(getActivity()).emptyShoppingCart();

                } else {
                    tvPaymentresult.setText("payment failed please try again");


                }
            } else {
                //todo
            }
        }
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        return view;
    }
}
