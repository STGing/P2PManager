package com.atiguigu.p2pmanager.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.atiguigu.p2pmanager.bean.LoginBean;
import com.atiguigu.p2pmanager.common.AppManager;
import com.atiguigu.p2pmanager.utils.SPUtils;

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

    public void saveData(LoginBean bean){
        SPUtils.put(this,"name",bean.getName());
        SPUtils.put(this,"imageurl",bean.getImageurl());
        SPUtils.put(this,"iscredit",bean.getIscredit());
        SPUtils.put(this,"phone",bean.getPhone());
    }

    /*
    * 获取用户信息
    * */
    public LoginBean getUser(){

        LoginBean bean = new LoginBean();
        bean.setName((String) SPUtils.get(this,"name",""));
        bean.setImageurl((String) SPUtils.get(this,"imageurl",""));
        bean.setIscredit((String) SPUtils.get(this,"iscredit",""));
        bean.setPhone((String) SPUtils.get(this,"phone",""));
        return bean;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
