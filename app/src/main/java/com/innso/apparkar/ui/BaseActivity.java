package com.innso.apparkar.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.innso.apparkar.R;
import com.innso.apparkar.di.component.ActivityComponent;
import com.innso.apparkar.di.component.DaggerActivityComponent;

public class BaseActivity extends FragmentActivity {

    protected ActivityComponent getComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(InnsoApplication.get().getAppComponent())
                .build();
    }

    protected void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
