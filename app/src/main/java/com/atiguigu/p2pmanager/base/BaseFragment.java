package com.atiguigu.p2pmanager.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atiguigu.p2pmanager.view.LoadingFrameLayout;

import butterknife.ButterKnife;

/**
 * Created by PC on 2017/6/20.
 */

public abstract class BaseFragment extends Fragment {

    public Context mContext;
    private LoadingFrameLayout loadingFrameLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        
        //首先要对资源进行判断，如果资源文件不存在，提醒
        if(getSelfLayoutID() == 0) {
            //说明没有资源文件
            TextView textView = new TextView(mContext);
            textView.setTextSize(20);
            textView.setText("没有发现资源文件，请重试！");
            return textView;
        }
        
        //BaseFragment中的视图，使用了自定义的LoadingFrameLayout
        //这样BaseFragment的子类也继承了

        //需要给视图中传递参数，来判断显示什么视图
        loadingFrameLayout = new LoadingFrameLayout(mContext) {
            @Override
            public View getView() {
                View view = View.inflate(mContext,getSelfLayoutID(),null);
                ButterKnife.bind(BaseFragment.this,view);
                return view;
            }

            @Override
            public String getUrl() {
                return getSelfUrl();
            }

            @Override
            protected void getResult(View successedView, String json) {
                //结果
                getNetResult(json);
            }
        };


        initView();
        return loadingFrameLayout;
    }


    /**
     * 初始化title
     */
    public abstract void initTitle();

    /**
     * 初始化View
     * @return
     */
    public abstract View initView();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getSelfLayoutID() != 0) {
            //如果有资源文件，联网
            loadingFrameLayout.ConectionNet();
        }


        initTitle();
        initData();
        initListener();
    }

    /**
     * 用于事件的监听器
     */
    public abstract void initListener();

    /**
     * 用于绑定数据
     */
    public abstract void initData();

    /**
     * 用户子类返回自身的资源
     * @return
     */
    protected abstract int getSelfLayoutID();

    /**
     * 子类使用自己的Url
     */
    protected abstract String getSelfUrl();

    /**
     * 子类获取联网结果
     * @param json
     */
    protected void getNetResult(String json){};
}
