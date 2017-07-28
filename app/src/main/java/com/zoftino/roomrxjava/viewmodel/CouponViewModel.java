package com.zoftino.roomrxjava.viewmodel;


import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.zoftino.roomrxjava.local.CouponEntity;
import com.zoftino.roomrxjava.remote.CouponsList;
import com.zoftino.roomrxjava.repository.LocalRepository;
import com.zoftino.roomrxjava.repository.RemoteRepository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CouponViewModel extends ViewModel {

    private LocalRepository localRepository;

    private RemoteRepository remoteRepository;

    private CompositeDisposable compositeDisposable;

    public CouponViewModel(LocalRepository localRepo, RemoteRepository remoteRepo, CompositeDisposable disposable){
        localRepository = localRepo;
        remoteRepository = remoteRepo;
        compositeDisposable = disposable;
    }

    public Flowable<List<CouponEntity>> getCoupons(){
        return localRepository.getCoupons();
    }

    public Maybe<CouponEntity> getCouponByStore(String storeIn){
        return localRepository.getCouponByStore(storeIn);
    }


    public Single<CouponEntity> getOneCoupon(){
        return localRepository.getOneCoupon();
    }

    public void insertCoupon(CouponEntity coupon){
        localRepository.insertCoupon(coupon);
    }

    public void deleteAllCoupons(){
        localRepository.deleteAllCoupons();
    }

    public void getCouponsFromService(){
        //add observable to CompositeDisposable so that it can be dispose when ViewModel is ready to be destroyed
        //Call retrofit client on background thread and update database with response from service using Room
        compositeDisposable.add(io.reactivex.Observable.just(1)
                .subscribeOn(Schedulers.computation())
                .flatMap(i -> { return remoteRepository.getCoupons();}).subscribeOn(Schedulers.io())
                .subscribe(new Consumer<CouponsList>() {
                    @Override
                    public void accept(CouponsList coupons) throws Exception {
                        for(CouponEntity ce : coupons.getCoupons()){
                            //database update
                            localRepository.insertCoupon(ce);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("MainActivity", "exception getting coupons", throwable);
                    }
                }));

    }
    @Override
    public void onCleared(){
        //prevents memory leaks by disposing pending observable objects
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

}
