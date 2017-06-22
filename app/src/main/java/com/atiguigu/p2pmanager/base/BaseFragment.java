package com.atiguigu.p2pmanager.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by PC on 2017/6/20.
 */

public abstract class BaseFragment extends Fragment {

    public Context mContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        return initView();
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
}
