package com.beverlycastillo.blaggodemo.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionModel {

    @SerializedName("build")
    @Expose
    public int build;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("required")
    @Expose
    public boolean required;

}
