package com.zoftino.roomrxjava.repository;


import com.zoftino.roomrxjava.remote.CouponsList;

import io.reactivex.Observable;

public interface RemoteRepository {

    public Observable<CouponsList> getCoupons();

    public void getStoreDetails();
}
