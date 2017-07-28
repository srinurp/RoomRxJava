package com.zoftino.roomrxjava;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zoftino.roomrxjava.di.CouponModule;
import com.zoftino.roomrxjava.local.CouponEntity;
import com.zoftino.roomrxjava.ui.CouponsAdapter;
import com.zoftino.roomrxjava.viewmodel.CouponViewModel;
import com.zoftino.roomrxjava.viewmodel.CouponViewModelFactory;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    @Inject @Named("activity")
    CompositeDisposable compositeDisposable;

    @Inject
    CouponViewModelFactory couponViewModelFactory;

    private CouponViewModel couponViewModel;

    private RecyclerView couponRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CouponApplication.getComponent(getApplicationContext())
                .getCouponComponent(new CouponModule(getApplicationContext())).inject(this);

        //instantiate view model
        couponViewModel = ViewModelProviders.of(this, couponViewModelFactory).get(CouponViewModel.class);

        //call retrofit service to get latest data and update database
        //runs in the background thread
        couponViewModel.getCouponsFromService();

        //recyclerView to show list of data items from database
        couponRecyclerView = (RecyclerView) findViewById(R.id.coupon_rv);
        RecyclerView.LayoutManager couponLayoutManager = new LinearLayoutManager(this);
        couponRecyclerView.setLayoutManager(couponLayoutManager);
    }

    public void addCoupons(View view){
        CouponEntity ce = new CouponEntity();
        ce.setCoupon("get upto 20% on tennis sports gear");
        ce.setStore("my store.com");
        ce.setCouponCode("er4");
        ce.setExpiryDate("20/12/2019");
        couponViewModel.insertCoupon(ce);
    }
    public void deleteCoupons(View view){
        couponViewModel.deleteAllCoupons();
    }
    public void showCouponByStore(View view){
        couponViewModel.getCouponByStore("etsy")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CouponEntity>() {
                    @Override
                    public void accept(CouponEntity coupon) throws Exception {
                        if(coupon != null) {
                            Toast.makeText(MainActivity.this, "Coupon "+coupon.getCoupon(), Toast.LENGTH_LONG ).show();

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("MainActivity", "exception getCouponByStore");
                    }
                });
    }
    public void showCoupon(View view){
        couponViewModel.getOneCoupon()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CouponEntity>() {
                    @Override
                    public void accept(CouponEntity coupon) throws Exception {
                        if(coupon != null) {
                            Toast.makeText(MainActivity.this, "Coupon "+coupon.getCoupon(), Toast.LENGTH_LONG ).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("MainActivity", "exception getOneCoupon");
                    }
                });
    }
    @Override
    protected void onStart() {
        super.onStart();

        compositeDisposable.add(couponViewModel.getCoupons()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CouponEntity>>() {
                    @Override
                    public void accept(List<CouponEntity> coupons) throws Exception {
                        if(coupons != null) {
                            CouponsAdapter ca = new CouponsAdapter(coupons, MainActivity.this);
                            couponRecyclerView.setAdapter(ca);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("MainActivity", "exception getting coupons");
                    }
                }));
    }
    @Override
    protected void onDestroy() {
        //dispose subscriptions
       if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
        super.onDestroy();
    }
}
