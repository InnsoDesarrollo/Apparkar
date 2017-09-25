package com.innso.apparkar.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;

import com.innso.apparkar.R;
import com.innso.apparkar.di.component.ActivityComponent;
import com.innso.apparkar.di.component.DaggerActivityComponent;
import com.innso.apparkar.ui.factories.SnackBarFactory;

import io.reactivex.disposables.CompositeDisposable;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class BaseActivity extends AppCompatActivity {

    protected CompositeDisposable disposable;

    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disposable = new CompositeDisposable();
    }

    protected ActivityComponent getComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(InnsoApplication.get().getAppComponent())
                .build();
    }

    @Override
    public void onPause() {
        disposable.clear();
        super.onPause();
    }

    public void requestLocationPermissions() {
        BaseActivityPermissionsDispatcher.successLocationPermissionWithCheck(this);
    }

    @NeedsPermission({Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION})
    protected void successLocationPermission() {
    }

    @OnNeverAskAgain({Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION})
    public void locationPermissionOnNeverAskAgain() {
        showDialogDeniedPermission(getString(R.string.permission_location));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BaseActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void showDialogDeniedPermission(String permissionDenied) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.permission_title_permission_denied)
                .setMessage(getString(R.string.permission_message_permission_denied, permissionDenied))
                .setPositiveButton(R.string.permission_enable, (dialog, which) -> {
                    dialog.dismiss();
                    startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse(getString(R.string.package_app))));
                })
                .setCancelable(false)
                .show();
    }

    public void showError(@NonNull View view, String message) {
        showMessage(SnackBarFactory.TYPE_ERROR, view, message, Snackbar.LENGTH_LONG);
    }

    public void showMessage(@SnackBarFactory.SnackBarType String type, @NonNull View view, String message, int duration) {
        SnackBarFactory.getSnackBar(type, view, message, duration).show();
    }

    private void initProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }
    }

    public void showProgressDialog(Pair<Boolean, Integer> progressData) {
        initProgressDialog();
        if (progressData.first) {
            progressDialog.setMessage(getString(progressData.second));
            progressDialog.show();
        } else {
            progressDialog.hide();
            progressDialog.dismiss();
        }
    }

    protected void replaceFragment(BaseFragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
