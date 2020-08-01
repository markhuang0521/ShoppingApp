package com.ming.onlineshoppingapp.utils;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ming.onlineshoppingapp.models.ShopItem;

public class TrackUserTime extends Service {
    private IBinder binder = new LocalBinder();
    private int second = 0;
    private boolean shouldFinish;
    private ShopItem shopItem;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        trackItemTime();
        return binder;
    }

    private void trackItemTime() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!shouldFinish) {
                    try {
                        Thread.sleep(1000);
                        second++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        shouldFinish = true;
        int minutes = second / 60;
        if (minutes > 0) {
            if (shopItem != null) {
                DbUtils.getInstance(this).updateUserPoint(shopItem, minutes);

            }
        }
    }

    public void setShopItem(ShopItem shopItem) {
        this.shopItem = shopItem;
    }

    public class LocalBinder extends Binder {

        public TrackUserTime getService() {
            return TrackUserTime.this;
        }

    }
}
