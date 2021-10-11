package com.beverlycastillo.blaggodemo.ui.views.main;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beverlycastillo.blaggodemo.R;
import com.beverlycastillo.blaggodemo.ui.base.BaseActivity;
import com.beverlycastillo.blaggodemo.ui.viewmodel.VersionViewModel;

import butterknife.BindView;
import butterknife.OnClick;

/*
    Created by Beverly Castillo on 10/9/2021
*/

public class MainActivity extends BaseActivity {

    @BindView(R.id.buildTV)     TextView buildTV;
    @BindView(R.id.urlTV)       TextView urlTV;
    @BindView(R.id.loadingPB)   ProgressBar loadingPB;
    @BindView(R.id.downloadBTN) Button downloadBTN;

    Integer buildInt;
    String urlStr;
    VersionViewModel versionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        versionViewModel = ViewModelProviders.of(this).get(VersionViewModel.class);
        versionViewModel.init();
        getVersionData();
    }

    @Override
    public int onLayoutSet() {
        return R.layout.activity_main;
    }

    private void getVersionData() {
        versionViewModel.getVersion(loadingPB);
        versionViewModel.getVersionModelLiveData().observe(this,    response -> {
            buildInt = response.build;
            urlStr = response.url;
            buildTV.setText(""+buildInt);
            urlTV.setPaintFlags(urlTV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            urlTV.setText(""+urlStr);
        });
    }

    @OnClick({R.id.downloadBTN, R.id.urlTV})
    void onDownloadClick() {
        if (urlStr!=null) {
            downloadBTN.setEnabled(true);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlStr));
            startActivity(intent);
        }else {
            downloadBTN.setEnabled(false);
        }
    }
}
