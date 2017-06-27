package com.atiguigu.p2pmanager.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import okhttp3.OkHttpClient;

/**
 * Created by PC on 2017/6/20.
 */

public class MyApplication extends Application {

    private static Context mContext;
    private static Handler handler;


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        //初始化handler
        handler = new Handler();

        //初始化：联网请求的OK-GO
        initOkGo();

        //初始化：GalleryFinal
        initGalleryFinal();

        //初始化：异常捕获的工具类
        //先不使用，程序OK后使用。
//        CrashHandler.getInstance().init(mContext);

    }


    private void initGalleryFinal() {
        //设置主题
        //ThemeConfig.CYAN
        ThemeConfig theme = new ThemeConfig.Builder()
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();

        //配置imageloader
        ImageLoader imageloader = new PicassoImageLoader();
        CoreConfig coreConfig =
                new CoreConfig.Builder(this, imageloader, theme)
                        .setFunctionConfig(functionConfig).build();
        GalleryFinal.init(coreConfig);
    }

    private void initOkGo() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        OkGo.getInstance().init(this)                       //必须调用初始化
                .setOkHttpClient(builder.build())               //必须设置OkHttpClient
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                //.addCommonHeaders(headers)                      //全局公共头
                //.addCommonParams(params);                       //全局公共参数
    }

    /**
     * 获得上下文
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 获得主线程ID
     * @return
     */
    public static int getMainThreadId() {
        return android.os.Process.myPid();
    }

    /**
     * 获得handler
     * @return
     */
    public static Handler getHandler() {
        return handler;
    }
}
