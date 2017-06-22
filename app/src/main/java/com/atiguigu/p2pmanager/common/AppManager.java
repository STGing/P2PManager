package com.atiguigu.p2pmanager.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by PC on 2017/6/21.
 */

public class AppManager {

    /**
     * 该类是单例模式
     */

    //1.私有单例
    private AppManager() {

    }

    //2.建立实例
    private static AppManager instance = new AppManager();

    //3.创建获取方法
    public static AppManager getInstance(){
        return instance;
    }


    /**
     * 任务栈的方法
     */
    //1.建立任务栈
    private Stack<Activity> stack = new Stack<>();

    /**
     * 2.增加Activity
     */
    public void addActivity(Activity activity){

        if(activity != null) {
            stack.add(activity);
        }
    }

    /**
     * 3.删除Activity
     */
    public void removeActivity(Activity activity){

        if(activity != null) {

//            //循环遍历找到要删除的Activity
//            for(int i = stack.size(); i >= 0; i--) {
//                Activity currentActivity = stack.get(i);
//
//                if(currentActivity == activity) {//如果找到以后
//
//                    activity.finish();//结束要移除的Activity
//                    stack.remove(i);//stack中移除
//                }
//            }
            activity.finish();
            stack.remove(activity);

        }

    }

    /**
     * 4.删除所以的Activity
     */
    public void removeAllActivity() {
        for (Activity activity : stack) {
            if (activity != null) {
                activity.finish();
            }
        }
        stack.clear();
    }

}
