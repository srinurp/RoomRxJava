package com.zoftino.roomrxjava.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {CouponEntity.class}, version = 1)
public abstract class CouponDatabase extends RoomDatabase {
    public abstract CouponDAO couponDao();
}
