package com.atiguigu.p2pmanager.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.utils.NetConnect;
import com.atiguigu.p2pmanager.utils.UIUtils;

/**
 * Created by PC on 2017/6/23.
 */

public abstract class LoadingFrameLayout extends FrameLayout {

    private Context mContext;
    private View loadingView;//加载中的页面
    private View errorView;//加载错误的页面
    private View successedView;//加载成功的页面

    /**
     * 3个标志，分别表示位于错误页面0，成功页面1，加载页面2
     */
    private int ERRORPAGE = 0;
    private int SUCCESSPAGE = 1;
    private int LOADINGPAGE = 2;

    private int currentPage = LOADINGPAGE;//默认加载中

    public LoadingFrameLayout(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public LoadingFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = getContext();
        initView();
    }

    /**
     * 1.初始化View
     */
    private void initView() {
        //添加Error的页面和正在加载的页面
        loadingView = UIUtils.inflate(R.layout.page_loading);
        this.addView(loadingView);
        errorView = UIUtils.inflate(R.layout.page_error);
        this.addView(errorView);

        showPageOnMainThread();
    }

    /**
     * 在主线程运行:显示当前页面的方法
     */
    private void showPageOnMainThread() {
        //UI操作，所以必须在主线程
        UIUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showPages();
            }
        });
    }

    /**
     * 显示当前页面
     */
    private void showPages() {
        //根据当前的页面状态，显示不同的页面
        loadingView.setVisibility(currentPage == LOADINGPAGE ? VISIBLE:GONE);
        errorView.setVisibility(currentPage == ERRORPAGE ? VISIBLE:GONE);

        //成功的页面
        if(successedView == null) {
            //成功的页面是不一样的，所以这里布局就需要动态的获取(通过方法)
//            successedView = UIUtils.inflate(getLayoutID());
            successedView = getView();
            this.addView(successedView);
        }
        successedView.setVisibility(currentPage == SUCCESSPAGE ? VISIBLE:GONE);

    }



    /**
     * 联网请求
     */
    public void ConectionNet(){
        //1.获取地址(也是动态的，每一个页面的都不一样)
        String url = getUrl();

        //需要对url进行判断
        if(TextUtils.isEmpty(url)) {
            //如果是空的话,说明该页面不需要网络请求，直接显示成功页面
            currentPage = SUCCESSPAGE;
            showPageOnMainThread();
            return;
        }

        //2.联网
        NetConnect.get(mContext, url, "p2p", new NetConnect.NetListener() {
            @Override
            public void onSuccess(String json) {
                
                //需要对json数据进行判断
                if(json.contains("404")) {
                    //如果json数据中包含404字样，说明联网页面不存在
                    currentPage = ERRORPAGE;
                    showPageOnMainThread();
                } else {
                    //联网成功的时候：显示成功页面
                    currentPage = SUCCESSPAGE;//设置页面状态为成功
                    showPageOnMainThread();//切换

                    //同时还有将数据设置回去
                    getResult(successedView,json);

                }

            }

            @Override
            public void onFailure(String message) {
                //联网失败的时候：显示失败的页面
                currentPage = ERRORPAGE;//设置页面状态为失败
                showPageOnMainThread();//切换
            }
        });
    }


    //根据各自的页面获取各自的资源
    public abstract View getView();

    //根据各自页面获取各自的URl
    public abstract String getUrl();

    //联网成功，数据正常的时候，将结果返回：View 和  json 数据。
    protected abstract void getResult(View successedView, String json);
}
