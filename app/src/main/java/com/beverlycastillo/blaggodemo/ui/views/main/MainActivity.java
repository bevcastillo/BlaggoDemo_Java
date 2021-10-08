package com.beverlycastillo.blaggodemo.ui.views.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Paint;
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
import com.beverlycastillo.blaggodemo.ui.viewmodel.VersionViewModel;
import com.beverlycastillo.blaggodemo.ui.views.splash.SplashActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView buildTV, urlTV;
    Button downloadBTN;
    Integer buildInt;
    String urlStr;
    VersionViewModel versionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        versionViewModel = ViewModelProviders.of(this).get(VersionViewModel.class);
        versionViewModel.init();
        
        buildTV = findViewById(R.id.buildTV);
        urlTV = findViewById(R.id.urlTV);
        downloadBTN = findViewById(R.id.downloadBTN);
        downloadBTN.setOnClickListener(this);
        urlTV.setOnClickListener(this);

        getVersionDataV2();
    }

    private void getVersionDataV2() {
        versionViewModel.getVersion();
        versionViewModel.getVersionModelLiveData().observe(this,response -> {
            buildInt = response.build;
            urlStr = response.url;

            buildTV.setText("Build Number: "+buildInt);
            urlTV.setPaintFlags(urlTV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            urlTV.setText("Url: "+urlStr);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.urlTV:
            case R.id.downloadBTN:
                if (urlStr!=null) {
                    downloadBTN.setEnabled(true);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlStr));
                    startActivity(intent);
                }else {
                    downloadBTN.setEnabled(false);
                }
                break;
        }
    }
}
