package com.zoftino.roomrxjava;


import android.app.Application;
import android.content.Context;

import com.zoftino.roomrxjava.di.AppComponent;
import com.zoftino.roomrxjava.di.DaggerAppComponent;

public class CouponApplication extends Application {

    private AppComponent component;

    @Override public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                                              .build();
    }

    public static AppComponent getComponent(Context context) {
        return ((CouponApplication)context.getApplicationContext()).component;
    }
}