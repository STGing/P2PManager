package com.atiguigu.p2pmanager.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.common.AppManager;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        AppManager.getInstance().addActivity(this);//加入任务栈中管理
    }
}
