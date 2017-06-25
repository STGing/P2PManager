package com.atiguigu.p2pmanager.adapter.base;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by PC on 2017/6/25.
 */

public abstract class ProdectBaseViewHolder<T> {

    private final View rootView;
    private T t;

    public ProdectBaseViewHolder() {
        rootView = initView();
        ButterKnife.bind(this,rootView);
        rootView.setTag(this);
    }

    //用来初始化视图的方法
    public abstract View initView();

    //设置数据
    public abstract void setData(T t);

    //返回视图
    public View getConvertView() {
        return rootView;
    }
}
