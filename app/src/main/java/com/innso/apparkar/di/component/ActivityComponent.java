package com.innso.apparkar.di.component;

import com.innso.apparkar.di.scope.ActivityScope;
import com.innso.apparkar.ui.activities.MapsActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface ActivityComponent extends AppComponent {

    void inject(MapsActivity baseActivity);

}