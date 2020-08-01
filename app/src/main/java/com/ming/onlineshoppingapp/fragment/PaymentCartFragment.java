package com.ming.onlineshoppingapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ming.onlineshoppingapp.R;
import com.ming.onlineshoppingapp.models.InfoCartFragment;
import com.ming.onlineshoppingapp.models.Order;
import com.ming.onlineshoppingapp.models.ShopItem;
import com.ming.onlineshoppingapp.utils.OrderService;

import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ming.onlineshoppingapp.models.InfoCartFragment.ORDER_KEY;

public class PaymentCartFragment extends Fragment {
    private TextView tvItemName, tvTotalPrice, tvAddress;
    private Button btnBack, btnCheckout;
    private RadioGroup radioPayment;
    private Gson gson = new Gson();

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_payment, container, false);
        tvItemName = view.findViewById(R.id.tv_payment_item_name);
        tvTotalPrice = view.findViewById(R.id.tv_payment_total_price);
        tvAddress = view.findViewById(R.id.tv_payment_address);
        btnBack = view.findViewById(R.id.btn_cart_back);
        btnCheckout = view.findViewById(R.id.btn_cart_check_out);
        radioPayment = view.findViewById(R.id.rg_payment_method);
        final Bundle bundle = getArguments();
        if (bundle != null) {
            String jsonOrder = bundle.getString(ORDER_KEY);
            if (jsonOrder != null) {
                Type orderType = new TypeToken<Order>() {
                }.getType();
                final Order order = gson.fromJson(jsonOrder, orderType);
                if (order != null) {
                    String itemName = "";
                    for (ShopItem item : order.getItems()) {
                        itemName += "\n\t" + item.getName();
                    }
                    tvItemName.setText(itemName);
                    tvTotalPrice.setText(String.valueOf(order.getOrderTotal()));
                    tvAddress.setText(order.getAddress());

                    btnCheckout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (radioPayment.getCheckedRadioButtonId()) {
                                case R.id.radio_credit_card:
                                    order.setPaymentMethod("credit card");
                                    break;
                                case R.id.radio_paypal:
                                    order.setPaymentMethod("paypal");

                                    break;
                                default:
                                    order.setPaymentMethod("unknown");
                                    break;

                            }
                            order.setPaymentSuccess(true);
                            Toast.makeText(getActivity(), "order is " + order.isPaymentSuccess(), Toast.LENGTH_SHORT).show();
                            //todo retrofit
                            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
                            OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

                            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .client(httpClient)
                                    .build();
                            OrderService orderService = retrofit.create(OrderService.class);
                            Call<Order> postOrderCall = orderService.postOrder(order);

                            postOrderCall.enqueue(new Callback<Order>() {
                                @Override
                                public void onResponse(Call<Order> call, Response<Order> response) {
                                    Toast.makeText(getActivity(), "code " + response.code(), Toast.LENGTH_SHORT).show();
                                    if (response.isSuccessful()) {
                                        Bundle resultBundle = new Bundle();
                                        resultBundle.putString(ORDER_KEY, gson.toJson(response.body()));
                                        PaymentResultFragment resultFragment = new PaymentResultFragment();
                                        resultFragment.setArguments(resultBundle);
                                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.fragment_container, resultFragment);
                                        fragmentTransaction.commit();


                                    }

                                }

                                @Override
                                public void onFailure(Call<Order> call, Throwable t) {
                                    t.printStackTrace();

                                }
                            });


                        }
                    });
                    btnBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            InfoCartFragment infoCartFragment = new InfoCartFragment();

                            infoCartFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.fragment_container, infoCartFragment);
                            fragmentTransaction.commit();
                        }
                    });
                }

            }
        }
        return view;
    }
}
