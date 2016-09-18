package com.hg.ksubakatest;

import android.app.Application;

import com.hg.ksubakatest.di.component.ApiComponent;
import com.hg.ksubakatest.di.component.DaggerApiComponent;
import com.hg.ksubakatest.di.component.DaggerNetworkComponent;
import com.hg.ksubakatest.di.component.NetworkComponent;
import com.hg.ksubakatest.di.module.ApiModule;
import com.hg.ksubakatest.di.module.NetworkModule;
import com.hg.ksubakatest.utils.Utils;

/**
 * Created by Hurman on 17/09/2016.
 */
public class MyApplication extends Application {

    private ApiComponent mApiComponent;
    private NetworkComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule(Utils.BASE_URL))
                .build();

        mApiComponent = DaggerApiComponent.builder()
                .apiModule(new ApiModule())
                .networkComponent(mNetComponent)
                .build();

    }

    public ApiComponent getmApiComponent() {
        return mApiComponent;
    }

    public NetworkComponent getmNetComponent() {
        return mNetComponent;
    }
}
