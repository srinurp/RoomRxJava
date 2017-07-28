package com.zoftino.roomrxjava.repository;


import com.zoftino.roomrxjava.remote.CouponAPI;
import com.zoftino.roomrxjava.remote.CouponsList;

import io.reactivex.Observable;

public class RemoteRepositoryImpl implements RemoteRepository{

    private CouponAPI couponClient;

    public RemoteRepositoryImpl(CouponAPI cpnClient){
        couponClient = cpnClient;
    }

    public Observable<CouponsList> getCoupons(){
        return couponClient.getCoupons();
    }

    public void getStoreDetails(){

    }
}
