package com.beverlycastillo.blaggodemo.data.api;

import com.beverlycastillo.blaggodemo.data.model.VersionModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VersionApi {

    @GET("/arvo-project-android-res/arvo_version.json")
    Call<VersionModel> getVersionApi();
}
