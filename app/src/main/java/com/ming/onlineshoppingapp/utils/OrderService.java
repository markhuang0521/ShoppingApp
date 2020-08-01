package com.ming.onlineshoppingapp.utils;

import com.ming.onlineshoppingapp.models.Order;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderService {

    @POST("posts")
    Call<Order> postOrder(@Body Order order);
}
