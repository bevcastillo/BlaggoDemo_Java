package com.beverlycastillo.blaggodemo.data.repository;

import com.beverlycastillo.blaggodemo.data.Url;
import com.beverlycastillo.blaggodemo.data.api.VersionApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VersionRepository {

    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static VersionApi getVersionApiInstance() {
        return getRetrofitInstance().create(VersionApi.class);
    }
}
