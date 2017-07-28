package com.zoftino.roomrxjava.di;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class})
public interface AppComponent {
 CouponComponent getCouponComponent(CouponModule couponModule);
}
