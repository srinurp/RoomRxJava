package com.zoftino.roomrxjava.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.zoftino.roomrxjava.local.CouponDAO;
import com.zoftino.roomrxjava.local.CouponDatabase;
import com.zoftino.roomrxjava.remote.CouponAPI;
import com.zoftino.roomrxjava.repository.LocalRepository;
import com.zoftino.roomrxjava.repository.LocalRepositoryImpl;
import com.zoftino.roomrxjava.repository.RemoteRepository;
import com.zoftino.roomrxjava.repository.RemoteRepositoryImpl;

import java.util.concurrent.Executor;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class CouponModule {

    private Context context;

    public CouponModule(Context ctx){
        context = ctx;
    }
    @CouponScope
    @Provides
    public CouponDAO getCouponDAO(CouponDatabase couponDatabase){
       return couponDatabase.couponDao();
    }

    @CouponScope
    @Provides
    public CouponDatabase getCouponDatabase(){
        return Room.databaseBuilder(context.getApplicationContext(),
                CouponDatabase.class, "coupons.db")
                .build();
    }
    @CouponScope
    @Provides
    public LocalRepository getLocalRepo(CouponDAO couponDAO, Executor exec){
        return new LocalRepositoryImpl(couponDAO, exec);
    }
    @CouponScope
    @Provides @Named("activity")
    public CompositeDisposable getCompositeDisposable(){
        return new CompositeDisposable();
    }
    @CouponScope
    @Provides @Named("vm")
    public CompositeDisposable getVMCompositeDisposable(){
        return new CompositeDisposable();
    }
    @CouponScope
    @Provides
    public RemoteRepository getRemoteRepo(CouponAPI cpnClient){
        return new RemoteRepositoryImpl(cpnClient);
    }
}
