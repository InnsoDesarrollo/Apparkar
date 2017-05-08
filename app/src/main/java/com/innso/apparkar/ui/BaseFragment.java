package com.innso.apparkar.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.innso.apparkar.di.component.DaggerFragmentComponent;
import com.innso.apparkar.di.component.FragmentComponent;

public class BaseFragment extends Fragment {

    protected FragmentComponent getComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(InnsoApplication.get().getAppComponent())
                .build();
    }

}
