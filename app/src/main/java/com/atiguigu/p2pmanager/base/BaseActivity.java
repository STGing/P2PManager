package com.atiguigu.p2pmanager.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.atiguigu.p2pmanager.common.AppManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by PC on 2017/6/26.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        unbinder = ButterKnife.bind(this);
        AppManager.getInstance().addActivity(this);

        initTitle();
        initView();
        initListener();
        initData();
    }

    public abstract int getLayoutID();

    public void initTitle() {}

    public abstract void initView();

    public abstract void initData();

    public abstract void initListener();

    public void showToast(String message){
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
