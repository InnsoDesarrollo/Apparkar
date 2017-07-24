package com.innso.apparkar.ui.viewModels;

import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.util.Pair;

import com.innso.apparkar.di.component.DaggerViewModelComponent;
import com.innso.apparkar.di.component.ViewModelComponent;
import com.innso.apparkar.provider.ResourceProvider;
import com.innso.apparkar.ui.InnsoApplication;
import com.innso.apparkar.ui.events.SnackBarEvent;
import com.innso.apparkar.ui.factories.SnackBarFactory;
import com.innso.apparkar.util.ErrorUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class BaseViewModel {

    protected ArrayList<Disposable> disposables = new ArrayList<>();

    private BehaviorSubject<Boolean> showLoading = BehaviorSubject.create();

    private PublishSubject<Boolean> showKeyboard = PublishSubject.create();

    private PublishSubject<SnackBarEvent> snackBarSubject = PublishSubject.create();

    private BehaviorSubject<Pair<Boolean, Integer>> showProgressDialog = BehaviorSubject.createDefault(new Pair<>(false, 0));

    @Inject
    ResourceProvider resourceProvider;

    protected ViewModelComponent getComponent() {
        return DaggerViewModelComponent.builder()
                .appComponent(InnsoApplication.get().getAppComponent())
                .build();
    }

    public ArrayList<Disposable> getDisposablesArray() {
        return disposables;
    }

    public Disposable[] getDisposables() {
        int size = disposables.size();
        Disposable[] vectorDisposable = new Disposable[size];
        for (int i = 0; i < size; i++) {
            vectorDisposable[i] = disposables.get(i);
        }
        return vectorDisposable;
    }

    public void clearDisposables() {

        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
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

    public void showServiceError(Throwable throwable) {
        showSnackBarMessage(SnackBarFactory.TYPE_ERROR, ErrorUtil.getMessageError(throwable), Snackbar.LENGTH_LONG);
        hideLoading();
        hideProgressDialog();
    }

    protected void showSnackBarError(String message) {
        showSnackBarMessage(SnackBarFactory.TYPE_ERROR, message, Snackbar.LENGTH_LONG);
    }

    protected void showSnackBarError(int messageId) {
        showSnackBarMessage(SnackBarFactory.TYPE_ERROR, messageId, Snackbar.LENGTH_LONG);
    }

    protected void eventSnackBar(SnackBarEvent event) {
        snackBarSubject.onNext(event);
    }

    protected void showSnackBarMessage(@SnackBarFactory.SnackBarType String typeSnackBar, int stringResId, int duration) {
        String message = resourceProvider.getString(stringResId);
        showSnackBarMessage(typeSnackBar, message, duration);
    }

    protected void showSnackBarMessage(@SnackBarFactory.SnackBarType String typeSnackBar, String message, int duration) {
        snackBarSubject.onNext(new SnackBarEvent(typeSnackBar, message, duration));
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
