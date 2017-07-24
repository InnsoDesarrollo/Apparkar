package com.innso.apparkar.di.component;

import com.innso.apparkar.di.scope.ActivityScope;
import com.innso.apparkar.ui.fragments.ParkingListFragment;
import com.innso.apparkar.ui.fragments.PetrolStationsListFragment;
import com.innso.apparkar.ui.viewModels.BaseViewModel;
import com.innso.apparkar.ui.viewModels.RegisterViewModel;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface ViewModelComponent extends AppComponent {

    void inject(BaseViewModel baseViewModel);

    void inject(RegisterViewModel baseViewModel);
}