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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beverlycastillo.blaggodemo.R;
import com.beverlycastillo.blaggodemo.data.api.VersionApi;
import com.beverlycastillo.blaggodemo.data.model.VersionModel;
import com.beverlycastillo.blaggodemo.data.repository.VersionRepository;
import com.beverlycastillo.blaggodemo.ui.base.BaseActivity;
import com.beverlycastillo.blaggodemo.ui.viewmodel.VersionViewModel;
import com.beverlycastillo.blaggodemo.ui.views.splash.SplashActivity;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        getVersionDataV2();
    }

    @Override
    public int onLayoutSet() {
        return R.layout.activity_main;
    }

    private void getVersionDataV2() {
        versionViewModel.getVersion(loadingPB);
        versionViewModel.getVersionModelLiveData().observe(this,response -> {
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
