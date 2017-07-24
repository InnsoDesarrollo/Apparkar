package com.innso.apparkar.ui;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.innso.apparkar.R;
import com.innso.apparkar.di.component.ActivityComponent;
import com.innso.apparkar.di.component.DaggerActivityComponent;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class BaseActivity extends AppCompatActivity {

    protected ActivityComponent getComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(InnsoApplication.get().getAppComponent())
                .build();
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

    protected void replaceFragment(BaseFragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
