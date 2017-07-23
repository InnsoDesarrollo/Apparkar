package com.innso.apparkar.di.component;

import com.innso.apparkar.di.scope.ActivityScope;
import com.innso.apparkar.ui.activities.MainActivity;
import com.innso.apparkar.ui.activities.RegisterLocationActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface ActivityComponent extends AppComponent {

    void inject(MainActivity baseActivity);

    void inject(RegisterLocationActivity registerLocationActivity);

}