package com.atiguigu.p2pmanager.utils;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * 联网请求数据的工具类
 */

public class NetConnect {

    /**
     * GET 网络请求
     * 参数：上下文   地址  缓存key   接口
     */
    public void get(Context context, String url, String key, final NetListener listener){

        OkGo.<String>get(url)
                .tag(context)
                .retryCount(3)//默认超时重连接次数
                .cacheKey(key)//设置缓存
                .cacheMode(CacheMode.DEFAULT)//缓存模式
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if(listener != null) {

                            listener.onSuccess(response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if(listener != null) {

                            listener.onFailure(response.body());
                        }
                    }
                });


    }


    /**
     * 通过接口来将数据传递
     */
    public interface NetListener{
        void onSuccess(String json);
        void onFailure(String message);
    }

}
