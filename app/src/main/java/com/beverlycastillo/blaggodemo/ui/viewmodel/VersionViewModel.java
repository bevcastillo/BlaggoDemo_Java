package com.beverlycastillo.blaggodemo.ui.viewmodel;

import android.app.Application;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.beverlycastillo.blaggodemo.data.model.VersionResponse;
import com.beverlycastillo.blaggodemo.data.repository.VersionRepository;

/*
    Created by Beverly Castillo on 10/9/2021
*/

public class VersionViewModel extends AndroidViewModel {
    private VersionRepository versionRepository;
    private LiveData<VersionResponse> versionModelLiveData;

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

    public LiveData<VersionResponse> getVersionModelLiveData() {
        return versionModelLiveData;
    }
}
