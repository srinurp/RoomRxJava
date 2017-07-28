package com.zoftino.roomrxjava.remote;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CouponAPI {
    @GET("coupons/")
    Observable<CouponsList> getCoupons();
}
