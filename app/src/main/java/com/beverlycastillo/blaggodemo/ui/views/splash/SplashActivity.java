package com.beverlycastillo.blaggodemo.ui.views.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beverlycastillo.blaggodemo.R;
import com.beverlycastillo.blaggodemo.data.api.VersionApi;
import com.beverlycastillo.blaggodemo.data.model.VersionModel;
import com.beverlycastillo.blaggodemo.data.repository.VersionRepository;
import com.beverlycastillo.blaggodemo.ui.views.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    Boolean isRequired;
    Call<VersionModel> versionModelCall;
    VersionApi versionApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        versionApi = VersionRepository.getVersionApiInstance();

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getVersionData();
    }

    void getVersionData() {
        versionModelCall = versionApi.getVersionApi();
        versionModelCall.enqueue(new Callback<VersionModel>() {
            @Override
            public void onResponse(Call<VersionModel> call, Response<VersionModel> response) {
                if (response.isSuccessful()) {
                    isRequired = response.body().required;
                    if (!isRequired) {
                        toMainScreen();
                    }else {
                        Toast.makeText(SplashActivity.this, "Goodbye", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<VersionModel> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "Error"+t.getMessage(), Toast.LENGTH_SHORT).show();
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
}