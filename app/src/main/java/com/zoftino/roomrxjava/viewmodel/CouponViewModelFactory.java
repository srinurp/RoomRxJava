package com.zoftino.roomrxjava.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.zoftino.roomrxjava.di.CouponScope;
import com.zoftino.roomrxjava.repository.LocalRepository;
import com.zoftino.roomrxjava.repository.RemoteRepository;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.CompositeDisposable;

@CouponScope
public class CouponViewModelFactory implements ViewModelProvider.Factory {

    @Inject
    LocalRepository localRepository;
    @Inject
    RemoteRepository remoteRepository;
    @Inject @Named("vm")
    CompositeDisposable compositeDisposable;

    @Inject
    public CouponViewModelFactory() {
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CouponViewModel.class)) {
            return (T) new CouponViewModel(localRepository, remoteRepository, compositeDisposable);
        }
        throw new IllegalArgumentException("Wrong ViewModel class");
    }
}
