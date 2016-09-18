package com.hg.ksubakatest.di.component;

import com.hg.ksubakatest.di.module.ApiModule;
import com.hg.ksubakatest.di.scope.CustomScope;
import com.hg.ksubakatest.ui.ActivityFilmDetail;
import com.hg.ksubakatest.ui.MainActivity;

import dagger.Component;

/**
 * Created by Hurman on 17/09/2016.
 */
@CustomScope
@Component(modules = ApiModule.class, dependencies = NetworkComponent.class)
public interface ApiComponent {

    void inject(MainActivity activity);
    void inject(ActivityFilmDetail activityFilmDetail);
}
