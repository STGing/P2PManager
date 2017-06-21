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

        //1.初始化：异常捕获的工具类
        //先不使用，程序OK后使用。
        //CrashHandler.getInstance().init(mContext);

    }


    public static Context getContext(){
        return mContext;
    }
}
