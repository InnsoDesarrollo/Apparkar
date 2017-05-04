package com.innso.apparkar.ui;

import android.support.v4.app.FragmentActivity;

import com.innso.apparkar.di.component.ActivityComponent;
import com.innso.apparkar.di.component.DaggerActivityComponent;

public class BaseActivity extends FragmentActivity {

    protected ActivityComponent getComponent(){
        return DaggerActivityComponent.builder()
                .appComponent(InnsoApplication.get().getAppComponent())
                .build();
    }
}
