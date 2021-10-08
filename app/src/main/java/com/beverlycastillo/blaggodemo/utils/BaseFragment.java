package com.beverlycastillo.blaggodemo.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.beverlycastillo.blaggodemo.R;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import icepick.Icepick;

public class BaseFragment extends Fragment {

    private Unbinder unbinder;
    private Context context;
    private Class baseActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(onLayoutSet(), container, false);
        bindView(view);
        context = getActivity();
        onViewReady();
        onViewReady(savedInstanceState);
        return view;
    }

    public Context getContext(){
        return context;
    }

    public int onLayoutSet(){
        return R.layout.fragment_default;
    }

    public void setParentActivity(Class baseActivity){
        this.baseActivity = baseActivity;
    }

    public Method[] getParentActivity(){
        return baseActivity.getMethods();
    }

    public int onLayoutSet(@LayoutRes int layout){
        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Icepick.saveInstanceState(this, outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        unbindView();
        super.onDestroyView();
    }

    public void onViewReady(){

    }

    public void onViewReady(Bundle savedInstanceState){

    }

    private void bindView(View view){
        unbinder = ButterKnife.bind(this, view);
    }

    private void unbindView(){
        unbinder.unbind();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    public void dd(String message){
        dd("Default", message);
    }

    public void dd(String tag, String message){
        Log.d(tag, message);
    }
}
