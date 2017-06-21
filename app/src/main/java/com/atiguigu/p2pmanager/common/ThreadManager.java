package com.atiguigu.p2pmanager.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by PC on 2017/6/21.
 */

public class ThreadManager {

    /**
     * 使用单例模式
     */
    private static ThreadManager instance;

    private ThreadManager() {}

    public static ThreadManager getInstance(){
        if(instance == null) {
            synchronized (ThreadManager.class){
                if(instance == null) {
                    instance = new ThreadManager();
                }
            }
        }

        return instance;
    }

    //这里使用缓存的线程池
    private ExecutorService service = Executors.newCachedThreadPool();
    //获取的方法
    public ExecutorService getThreadPool(){
        return service;
    }
}
