package com.ming.onlineshoppingapp.models;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ming.onlineshoppingapp.fragment.FirstCartFragment;
import com.ming.onlineshoppingapp.fragment.PaymentCartFragment;
import com.ming.onlineshoppingapp.R;
import com.ming.onlineshoppingapp.utils.DbUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class InfoCartFragment extends Fragment {
    private EditText etAddress, etEmail, etPhone, etZipCode;
    private Button btnBack, btnNext;
    private Gson gson = new Gson();
    public static final String ORDER_KEY = "order";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_cart, container, false);
        etAddress = view.findViewById(R.id.et_info_address);
        etEmail = view.findViewById(R.id.et_info_email);
        etPhone = view.findViewById(R.id.et_info_phone);
        etZipCode = view.findViewById(R.id.et_info_zip);
        btnBack = view.findViewById(R.id.btn_cart_back);
        btnNext = view.findViewById(R.id.btn_cart_next);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            String jsonOrder = bundle.getString(ORDER_KEY);
            if (jsonOrder != null) {
                Type orderType = new TypeToken<Order>() {
                }.getType();
                Order order = gson.fromJson(jsonOrder, orderType);
                etAddress.setText(order.getAddress());
            }
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new FirstCartFragment());
                fragmentTransaction.commit();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    ArrayList<ShopItem> cartItems = DbUtils.getInstance(getActivity()).getShoppingCart();
                    Order order = new Order(cartItems, etAddress.getText().toString().trim(),
                            etPhone.getText().toString(),
                            etZipCode.getText().toString(),
                            etEmail.getText().toString(),
                            DbUtils.getInstance(getActivity()).getItemTotal());
                    String gsonOrder = gson.toJson(order);
                    Bundle bundle = new Bundle();
                    bundle.putString(ORDER_KEY, gsonOrder);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    PaymentCartFragment paymentCartFragment = new PaymentCartFragment();
                    paymentCartFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragment_container, paymentCartFragment);
                    fragmentTransaction.commit();


                }
            }
        });

        return view;


    }

    private boolean validateInput() {
        if (etAddress.getText().toString().isEmpty()) {
            etAddress.setError("empty value not allow");
            return false;
        }
        if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("empty value not allow");
            return false;
        }
        if (etPhone.getText().toString().isEmpty()) {
            etPhone.setError("empty value not allow");
            return false;
        }
        if (etZipCode.getText().toString().isEmpty()) {
            etZipCode.setError("empty value not allow");
            return false;
        }
        return true;
    }
}
