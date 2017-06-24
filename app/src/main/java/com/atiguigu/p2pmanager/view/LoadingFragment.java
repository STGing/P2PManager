package com.atiguigu.p2pmanager.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.utils.UIUtils;

/**
 * Created by PC on 2017/6/23.
 */

public class LoadingFragment extends FrameLayout {

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

    public LoadingFragment(@NonNull Context context) {
        super(context);
        this.mContext = getContext();
        initView();
    }

    public LoadingFragment(@NonNull Context context, @Nullable AttributeSet attrs) {
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

            successedView = UIUtils.inflate(R.layout.fragment_home);
        }
        successedView.setVisibility(currentPage == SUCCESSPAGE ? VISIBLE:GONE);
    }
}
