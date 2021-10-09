package com.beverlycastillo.blaggodemo.ui.base;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.beverlycastillo.blaggodemo.R;
import org.jetbrains.annotations.NotNull;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseActivity extends AppCompatActivity {
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(onLayoutSet());
        bindView();
    }

    public int onLayoutSet() {
        return R.layout.activity_default;
    }

    public void bindView() {
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindView();
    }

    public void unbindView() {
        unbinder.unbind();
    }
}
