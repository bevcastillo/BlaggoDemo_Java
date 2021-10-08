package com.beverlycastillo.blaggodemo.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class RouteManager {
    public static final String ACTIVITY_TAG = "activity_tag";
    public static final String ACTIVITY_BUNDLE = "activity_bundle";
    public static final String FRAGMENT_TAG = "fragment_name";
    public static final String FRAGMENT_BUNDLE = "fragment_bundle";

    public static class Route{

        private static BaseActivity baseActivity;
        private static Context context;

        private String  fragmentTag;
        private Bundle  fragmentBundle;

        private String  activityTag;
        private Bundle  activityBundle;
        private Class   activityClass;

        private static volatile Route singleton;

        public static Route with(BaseActivity baseActivity){
            singleton = new Route();
            singleton.baseActivity = baseActivity;
            return singleton;
        }

        public static Route with(Context context){
            singleton = new Route();
            singleton.context = context;
            return singleton;
        }

        public Route addFragmentTag(String tag){
            singleton.fragmentTag = tag;
            return singleton;
        }

        public Route addFragmentBundle(Bundle bundle){
            singleton.fragmentBundle = bundle;
            return singleton;
        }

        public Route addActivityClass(Class c){
            singleton.activityClass = c;
            return singleton;
        }

        public Route addActivityTag(String tag){
            singleton.activityTag = tag;
            return singleton;
        }

        public Route addActivityBundle(Bundle bundle){
            singleton.activityBundle = bundle;
            return singleton;
        }

        public void startActivity(int ... flags){
            Intent intent = new Intent(baseActivity, singleton.activityClass);
            for (int flag : flags)
            {
                intent.addFlags(flag);
            }

            if(fragmentTag != null){
                intent.putExtra(FRAGMENT_TAG, fragmentTag);
            }

            if(fragmentBundle != null){
                intent.putExtra(FRAGMENT_BUNDLE, fragmentBundle);
            }

            if(activityTag != null){
                intent.putExtra(ACTIVITY_TAG, activityTag);
            }

            if(activityBundle != null){
                intent.putExtra(ACTIVITY_BUNDLE, activityBundle);
            }

            singleton.baseActivity.startActivity(intent);
        }

        public void startActivityResult(int code, int ... flags){
            Intent intent = new Intent(baseActivity, singleton.activityClass);
            for (int flag : flags)
            {
                intent.addFlags(flag);
            }

            if(fragmentTag != null){
                intent.putExtra(FRAGMENT_TAG, fragmentTag);
            }

            if(fragmentBundle != null){
                intent.putExtra(FRAGMENT_BUNDLE, fragmentBundle);
            }

            if(activityTag != null){
                intent.putExtra(ACTIVITY_TAG, activityTag);
            }

            if(activityBundle != null){
                intent.putExtra(ACTIVITY_BUNDLE, activityBundle);
            }

            singleton.baseActivity.startActivityForResult(intent, code);
        }

        public Intent getIntent(int ... flags){
            Intent intent = new Intent(context, singleton.activityClass);
            for (int flag : flags)
            {
                intent.addFlags(flag);
            }

            if(fragmentTag != null){
                intent.putExtra(FRAGMENT_TAG, fragmentTag);
            }

            if(fragmentBundle != null){
                intent.putExtra(FRAGMENT_BUNDLE, fragmentBundle);
            }

            if(activityTag != null){
                intent.putExtra(ACTIVITY_TAG, activityTag);
            }

            if(activityBundle != null){
                intent.putExtra(ACTIVITY_BUNDLE, activityBundle);
            }

            return intent;
        }
    }


}
