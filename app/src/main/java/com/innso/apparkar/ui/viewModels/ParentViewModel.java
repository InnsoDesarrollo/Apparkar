package com.innso.apparkar.ui.viewModels;


public class ParentViewModel extends BaseViewModel {

    protected void addChild(BaseViewModel baseViewModel) {
        disposables.add(baseViewModel.observableShowKeyboard().subscribe(this::eventKeyboard));
        disposables.add(baseViewModel.observableShowProgress().subscribe(this::eventProgressDialog));
        disposables.add(baseViewModel.observableShowLoading().subscribe(this::eventLoading));
        disposables.add(baseViewModel.observableSnackBar().subscribe(this::eventSnackBar));
        disposables.addAll(baseViewModel.getDisposablesArray());
    }
}
