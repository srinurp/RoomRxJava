package com.zoftino.roomrxjava.di;

        import com.zoftino.roomrxjava.MainActivity;

        import dagger.Subcomponent;

@CouponScope
@Subcomponent(modules = CouponModule.class)
public interface CouponComponent {
    void inject(MainActivity mainActivity);
}
