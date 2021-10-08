package com.beverlycastillo.blaggodemo.ui.views.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.beverlycastillo.blaggodemo.R;
import com.beverlycastillo.blaggodemo.data.api.VersionApi;
import com.beverlycastillo.blaggodemo.data.model.VersionModel;
import com.beverlycastillo.blaggodemo.data.repository.VersionRepository;
import com.beverlycastillo.blaggodemo.ui.views.splash.SplashActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView buildTV, urlTV;
    Button downloadBTN;
    Call<VersionModel> versionModelCall;
    VersionApi versionApi;
    Integer buildInt;
    String urlStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        versionApi = VersionRepository.getVersionApiInstance();
        buildTV = findViewById(R.id.buildTV);
        urlTV = findViewById(R.id.urlTV);
        downloadBTN = findViewById(R.id.downloadBTN);

        downloadBTN.setOnClickListener(this);

        getVersionData();
    }

    void getVersionData() {
        versionModelCall = versionApi.getVersionApi();
        versionModelCall.enqueue(new Callback<VersionModel>() {
            @Override
            public void onResponse(Call<VersionModel> call, Response<VersionModel> response) {
                if (response.isSuccessful()) {
                    buildInt = response.body().build;
                    urlStr = response.body().url;

                    buildTV.setText("Build Number: "+buildInt);
                    urlTV.setText("Url: "+urlStr);
                }
            }

            @Override
            public void onFailure(Call<VersionModel> call, Throwable t) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.downloadBTN:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlStr));
                    startActivity(intent);
                }catch (Exception e) {
                    downloadBTN.setEnabled(false);
                }
                break;
        }
    }
}
