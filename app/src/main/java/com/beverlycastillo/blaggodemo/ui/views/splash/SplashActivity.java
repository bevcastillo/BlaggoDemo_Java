package com.beverlycastillo.blaggodemo.ui.views.splash;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.beverlycastillo.blaggodemo.R;
import com.beverlycastillo.blaggodemo.data.model.VersionResponse;
import com.beverlycastillo.blaggodemo.ui.base.BaseActivity;
import com.beverlycastillo.blaggodemo.ui.viewmodel.VersionViewModel;
import com.beverlycastillo.blaggodemo.ui.views.main.MainActivity;

import retrofit2.Call;

/*
    Created by Beverly Castillo on 10/9/2021
*/

public class SplashActivity extends BaseActivity {

    Boolean isRequired;
    Call<VersionResponse> versionModelCall;
    VersionViewModel versionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        versionViewModel = ViewModelProviders.of(this).get(VersionViewModel.class);
        versionViewModel.init();
        getVersionDataV2();
    }

    @Override
    public int onLayoutSet() {
        return R.layout.activity_splash;
    }

    private void getVersionDataV2() {
        versionViewModel.getVersion(null);
        versionViewModel.getVersionModelLiveData().observe(this,response -> {
            isRequired = response.required;
            if (!isRequired) {
                toMainScreen();
            }else {
                toExitApp();
            }
        });
    }

    void toMainScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    void toExitApp() {
        AlertDialog.Builder alert = new AlertDialog.Builder(SplashActivity.this);
        alert.setMessage("Required, closing the app now...");
        alert.setCancelable(false).create().show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }
}