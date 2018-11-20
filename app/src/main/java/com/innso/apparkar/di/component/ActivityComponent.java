package com.innso.apparkar.di.component;

import com.innso.apparkar.di.scope.ActivityScope;
import com.innso.apparkar.ui.activities.MainActivity;
import com.innso.apparkar.ui.activities.RegisterLocationActivity;
import com.innso.apparkar.ui.activities.SplashActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface ActivityComponent extends AppComponent {

    void inject(RegisterLocationActivity registerLocationActivity);

    void inject(SplashActivity splashActivity);

}