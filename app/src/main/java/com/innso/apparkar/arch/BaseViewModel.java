package com.innso.apparkar.arch;

import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.util.Pair;

import com.innso.apparkar.arch.models.StartActivityModel;
import com.innso.apparkar.di.component.DaggerViewModelComponent;
import com.innso.apparkar.di.component.ViewModelComponent;
import com.innso.apparkar.ui.InnsoApplication;
import com.innso.apparkar.ui.events.SnackBarEvent;
import com.innso.apparkar.ui.factories.SnackBarFactory;
import com.innso.apparkar.util.ErrorUtil;
import com.innso.apparkar.arch.models.FinishActivityModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class BaseViewModel {

    protected CompositeDisposable disposables = new CompositeDisposable();

    protected final PublishSubject<StartActivityModel> startActivityEvent = PublishSubject.create();

    protected final PublishSubject<FinishActivityModel> closeView = PublishSubject.create();

    private final BehaviorSubject<Boolean> showLoading = BehaviorSubject.create();

    private final PublishSubject<Boolean> showKeyboard = PublishSubject.create();

    private final PublishSubject<SnackBarEvent> snackBarSubject = PublishSubject.create();

    private final BehaviorSubject<Pair<Boolean, Integer>> showProgressDialog = BehaviorSubject.createDefault(new Pair<>(false, 0));

    protected ViewModelComponent getComponent() {
        return DaggerViewModelComponent.builder()
                .appComponent(InnsoApplication.get().getAppComponent())
                .build();
    }

    public void clearDisposables() {
        disposables.clear();
    }

    protected void showKeyboard() {
        showKeyboard.onNext(true);
    }

    protected void hideKeyBoard() {
        showKeyboard.onNext(false);
    }

    protected void eventKeyboard(boolean event) {
        showKeyboard.onNext(event);
    }

    public void showLoading() {
        showLoading.onNext(true);
    }

    public void hideLoading() {
        showLoading.onNext(false);
    }

    protected void eventLoading(Boolean event) {
        showLoading.onNext(event);
    }

    protected void showProgressDialog(@StringRes int string) {
        showProgressDialog.onNext(new Pair<>(true, string));
    }

    protected void dismissProgressDialog() {
        showProgressDialog.onNext(new Pair<>(false, 0));
    }

    protected void hideProgressDialog() {
        showProgressDialog.onNext(new Pair<>(false, null));
    }

    protected void eventProgressDialog(Pair<Boolean, Integer> event) {
        showProgressDialog.onNext(event);
    }

    protected void showSnackBarError(String message) {
        showSnackBarMessage(SnackBarFactory.TYPE_ERROR, message, Snackbar.LENGTH_LONG);
    }

    public void showServiceError(Throwable throwable) {
        showSnackBarMessage(SnackBarFactory.TYPE_ERROR, ErrorUtil.getMessageError(throwable), Snackbar.LENGTH_LONG);
        hideLoading();
        hideProgressDialog();
    }

    protected void eventSnackBar(SnackBarEvent event) {
        snackBarSubject.onNext(event);
    }


    protected void showSnackBarMessage(@SnackBarFactory.SnackBarType String typeSnackBar, String message, int duration) {
        snackBarSubject.onNext(new SnackBarEvent(typeSnackBar, message, duration));
    }

    /**
     * Observables
     */

    public Observable<StartActivityModel> startActivityEvent() {
        return startActivityEvent.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<FinishActivityModel> closeViewEvent() {
        return closeView.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> observableShowLoading() {
        return showLoading.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Pair<Boolean, Integer>> observableShowProgress() {
        return showProgressDialog.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> observableShowKeyboard() {
        return showKeyboard.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SnackBarEvent> observableSnackBar() {
        return snackBarSubject.observeOn(AndroidSchedulers.mainThread());
    }

}
