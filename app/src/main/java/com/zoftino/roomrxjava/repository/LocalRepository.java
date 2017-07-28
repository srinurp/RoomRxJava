package com.zoftino.roomrxjava.repository;

import com.zoftino.roomrxjava.local.CouponEntity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface LocalRepository {
    public Flowable<List<CouponEntity>> getCoupons();
    public Maybe<CouponEntity> getCouponByStore(String storeIn);
    public Single<CouponEntity> getOneCoupon();
    public void insertCoupon(CouponEntity coupon);
    public void deleteAllCoupons();
}
