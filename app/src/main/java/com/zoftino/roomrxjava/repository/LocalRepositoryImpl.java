package com.zoftino.roomrxjava.repository;

import com.zoftino.roomrxjava.local.CouponDAO;
import com.zoftino.roomrxjava.local.CouponEntity;

import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class LocalRepositoryImpl implements LocalRepository {
    private CouponDAO couponDAO;
    private Executor executor;

    public LocalRepositoryImpl(CouponDAO cpnDAO, Executor exec) {
        couponDAO = cpnDAO;
        executor = exec;
    }
    public Flowable<List<CouponEntity>> getCoupons() {
        return couponDAO.getCoupons();
    }
    public Maybe<CouponEntity> getCouponByStore(String storeIn) {
        return couponDAO.getCouponByStore(storeIn);
    }
    public Single<CouponEntity> getOneCoupon() {
        return couponDAO.getOneCoupon();
    }
    public void insertCoupon(CouponEntity coupon) {
        executor.execute(() -> {
            couponDAO.insertCoupon(coupon);
        });
    }
    public void deleteAllCoupons() {
        executor.execute(() -> {
            couponDAO.deleteAllCoupons();
        });
    }
}
