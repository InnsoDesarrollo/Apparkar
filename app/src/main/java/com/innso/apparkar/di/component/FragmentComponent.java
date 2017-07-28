package com.innso.apparkar.di.component;

import com.innso.apparkar.di.scope.ActivityScope;
import com.innso.apparkar.ui.fragments.BasePlacesFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface FragmentComponent extends AppComponent {

    void inject(BasePlacesFragment basePlacesFragment);
}