package com.beverlycastillo.blaggodemo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.beverlycastillo.blaggodemo.R;
import com.beverlycastillo.blaggodemo.data.local.Data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import icepick.Icepick;
import icepick.State;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private Context context;
    private Fragment currentFragment;

    @State String fragmentName;
    @State int fragmentFrame = R.id.content_frame;

    @Nullable @BindView(R.id.activityBackBTN)     ImageView activityBackBTN;
    @Nullable @BindView(R.id.activityIconBTN)     ImageView activityIconBTN;
    @Nullable @BindView(R.id.activityTitleTXT)    TextView activityTitleTXT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(onLayoutSet());
        bindView();
        context = this;
        Data.setSharedPreferences(context);
        initialFragment(getActivityTag(), getFragmentTag());
        onViewReady();
        setupTitle();
    }

    private void setupTitle(){
        if(activityBackBTN != null){
            activityBackBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    public void initialFragment(String activityName, String fragmentName){

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Icepick.saveInstanceState(this, outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();

        if(backStackCount > 1){
            int index = backStackCount - 2;
            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
            String tag = backEntry.getName();
            currentFragment = getSupportFragmentManager().findFragmentByTag(tag);
        }

        if ( backStackCount <= 1 ) {
            finish();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onDestroy() {
        unbindView();
        super.onDestroy();
    }


    public int onLayoutSet(){
        return R.layout.activity_default;
    }

    public void onViewReady(){

    }

    private void bindView(){
        unbinder = ButterKnife.bind(this);
    }

    private void unbindView(){
        unbinder.unbind();
    }

    public Context getContext(){
        return context;
    }

    public void setFragmentFrame(int fragmentFrame){
        this.fragmentFrame = fragmentFrame;
    }

    public void switchFragment(Fragment fragment) {
        switchFragment(fragment,true);
    }

    public void switchFragment(Fragment fragment, boolean addBackStack) {
        switchFragment(fragment, addBackStack, true);
    }

    public void switchFragment(Fragment fragment, boolean addBackStack, boolean poppedStack) {

        String backStateName =  fragment.getClass().getName();
        fragmentName = backStateName;
        String fragmentTag = backStateName;

        if(poppedStack){
            FragmentManager manager = getSupportFragmentManager();
            boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);
            if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null){
                changeFragment(fragment, addBackStack, backStateName, fragmentTag);
            }else{
                currentFragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
            }
        }else {
            changeFragment(fragment, addBackStack, backStateName, fragmentTag);
        }
    }

    public String getCurrentFragmentName(){
        return fragmentName;
    }

    private void changeFragment(Fragment fragment, boolean addBackStack, String backStateName, String fragmentTag){

        currentFragment = fragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(R.id.content_frame, fragment, fragmentTag);
        if(addBackStack){
            fragmentTransaction.addToBackStack(backStateName);
        }
        fragmentTransaction.commit();
    }

    public void switchFragmentPopped(Fragment fragment) {
        String backStateName =  fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.content_frame, fragment, fragmentTag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    public Fragment getCurrentFragment(){
        return currentFragment;
    }

    public String getActivityTag(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            return bundle.getString(RouteManager.ACTIVITY_TAG, "");
        }
        return "";
    }

    public String getFragmentTag(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            return bundle.getString(RouteManager.FRAGMENT_TAG, "");
        }
        return "";
    }

    public Bundle getActivityBundle(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            return bundle.getBundle(RouteManager.ACTIVITY_BUNDLE);
        }
        return new Bundle();
    }

    public Bundle getFragmentBundle(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            return bundle.getBundle(RouteManager.FRAGMENT_BUNDLE);
        }
        return new Bundle();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(context));
    }

    public String getSHA(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.DEFAULT);
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        return "";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    public boolean isAllPermissionResultGranted(int [] grantResults){
        boolean granted = true;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                granted = false;
                break;
            }
        }
        return granted;
    }

    public int getScreenWidth(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public int getScreenHeight(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public void setTitle(String title){
        if(activityTitleTXT != null){
            activityTitleTXT.setText(title);
        }
    }

    @Nullable
    public ImageView getActivityIconBTN() {
        return activityIconBTN;
    }

    @Nullable
    public ImageView getActivityBackBTN() {
        return activityBackBTN;
    }

    @Nullable
    public TextView getActivityTitleTXT() {
        return activityTitleTXT;
    }

    public void showBackButton(boolean b){
        if(activityBackBTN != null){
            activityBackBTN.setVisibility(b ? View.VISIBLE : View.GONE);
        }
    }

    public void dd(String message){
        dd("Default", message);
    }

    public void dd(String tag, String message){
        Log.d(tag, message);
    }
}
