package com.zoftino.roomrxjava.remote;

import com.zoftino.roomrxjava.local.CouponEntity;

import java.util.List;

public class CouponsList {
    private List<CouponEntity> coupons;

    public List<CouponEntity> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<CouponEntity> coupons) {
        this.coupons = coupons;
    }
}
