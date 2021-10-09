package com.beverlycastillo.blaggodemo.data.repository;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.beverlycastillo.blaggodemo.data.Url;
import com.beverlycastillo.blaggodemo.data.api.VersionApi;
import com.beverlycastillo.blaggodemo.data.model.VersionModel;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VersionRepository {
    private VersionApi versionApi;
    private MutableLiveData<VersionModel> versionModelMutableLiveData;
    ProgressDialog progressDialog;

    public VersionRepository() {
        versionModelMutableLiveData = new MutableLiveData<>();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        versionApi = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(VersionApi.class);
    }

    public void getVersionData(ProgressBar progressBar) {
        versionApi.getVersionApi()
                .enqueue(new Callback<VersionModel>() {
                    @Override
                    public void onResponse(Call<VersionModel> call, Response<VersionModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            versionModelMutableLiveData.postValue(response.body());
                            if (progressBar!=null) {
                                progressBar.setVisibility(View.GONE);
                            }
                        }else {
                            if (progressBar!=null) {
                                progressBar.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<VersionModel> call, Throwable t) {
                        versionModelMutableLiveData.postValue(null);
                    }
                });
    }

    public LiveData<VersionModel> getVersionModelLiveData() {
        return versionModelMutableLiveData;
    }

}
