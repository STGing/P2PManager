package com.atiguigu.p2pmanager.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.atiguigu.p2pmanager.base.BaseFragment;

/**
 * Created by PC on 2017/6/20.
 */

public class MainMoreFragment extends BaseFragment {

    TextView textView;

    @Override
    public void initTitle() {

    }

    @Override
    public View initView() {

        textView = new TextView(mContext);
        textView.setTextColor(Color.RED);
        textView.setTextSize(15);
        return textView;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        textView.setText("这里是更多页面");
    }
}
