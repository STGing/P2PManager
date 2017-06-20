package com.atiguigu.p2pmanager.common;

import android.app.Application;
import android.content.Context;

/**
 * Created by PC on 2017/6/20.
 */

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

    }


    public static Context getContext(){
        return mContext;
    }
}
