package com.innso.apparkar.di.component;

import com.innso.apparkar.di.scope.ActivityScope;
import com.innso.apparkar.ui.viewModels.BaseViewModel;
import com.innso.apparkar.ui.viewModels.ParkingViewModel;
import com.innso.apparkar.ui.viewModels.RegisterViewModel;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface ViewModelComponent extends AppComponent {

    void inject(BaseViewModel baseViewModel);

    void inject(ParkingViewModel parkingViewModel);

    void inject(RegisterViewModel baseViewModel);
}