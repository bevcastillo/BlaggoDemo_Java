package com.beverlycastillo.blaggodemo.ui.views.splash;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beverlycastillo.blaggodemo.R;
import com.beverlycastillo.blaggodemo.data.api.VersionApi;
import com.beverlycastillo.blaggodemo.data.model.VersionModel;
import com.beverlycastillo.blaggodemo.data.repository.VersionRepository;
import com.beverlycastillo.blaggodemo.ui.base.BaseActivity;
import com.beverlycastillo.blaggodemo.ui.viewmodel.VersionViewModel;
import com.beverlycastillo.blaggodemo.ui.views.main.MainActivity;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {

    Boolean isRequired;
    Call<VersionModel> versionModelCall;
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