package com.beverlycastillo.blaggodemo.ui.viewmodel;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.beverlycastillo.blaggodemo.data.model.VersionModel;
import com.beverlycastillo.blaggodemo.data.repository.VersionRepository;

public class VersionViewModel extends AndroidViewModel {
    private VersionRepository versionRepository;
    private LiveData<VersionModel> versionModelLiveData;

    public VersionViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        versionRepository = new VersionRepository();
        versionModelLiveData = versionRepository.getVersionModelLiveData();
    }

    public void getVersion(ProgressBar progressBar) {
        versionRepository.getVersionData(progressBar);
    }

    public LiveData<VersionModel> getVersionModelLiveData() {
        return versionModelLiveData;
    }
}
