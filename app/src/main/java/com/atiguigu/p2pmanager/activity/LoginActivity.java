package com.atiguigu.p2pmanager.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.base.BaseActivity;
import com.atiguigu.p2pmanager.bean.LoginBean;
import com.atiguigu.p2pmanager.common.AppNetConfig;
import com.atiguigu.p2pmanager.utils.NetConnect;
import com.atiguigu.p2pmanager.utils.SPUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.base_title)
    TextView baseTitle;
    @BindView(R.id.base_back)
    ImageView baseBack;
    @BindView(R.id.base_setting)
    ImageView baseSetting;
    @BindView(R.id.login_et_phone)
    EditText loginEtPhone;
    @BindView(R.id.login_et_pwd)
    EditText loginEtPwd;
    @BindView(R.id.login_bt_login)
    Button loginBtLogin;
    @BindView(R.id.login_tv_register)
    TextView loginTvRegister;

    @Override
    public int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    public void initTitle() {
        super.initTitle();
        baseTitle.setText("登陆");

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        //标题栏的返回
        baseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //标题栏的设置
        baseSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("设置");
            }
        });

        /**
         * 如果点击了登陆，之后做的操作
         */
        loginBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.获取输入的数据

                String phone = loginEtPhone.getText().toString().trim();
                String pwd = loginEtPwd.getText().toString().trim();

                //2.对数据判断
                if(TextUtils.isEmpty(phone)) {
                    showToast("手机号不能为空");
                    return;
                }
                if(TextUtils.isEmpty(pwd)) {
                    showToast("密码不能为空");
                    return;
                }

                //3.进行网络请求
                Map<String,String> map = new HashMap<String, String>();
                map.put("phone",phone);
                map.put("password",pwd);

                NetConnect.post(LoginActivity.this,AppNetConfig.LOGIN,"login", map, new NetConnect.NetListener() {
                    @Override
                    public void onSuccess(String json) {
                        //Log.e("TAG","登陆网络请求："+json);

                        //对请求的结果进行判断
                        try {
                            //手动解析
                            JSONObject object = new JSONObject(json);
                            boolean isSuccess = object.getBoolean("success");
                            //判断
                            if(isSuccess) {
                                //如果OK，说明登陆成功

                                //解析json数据
                                LoginBean loginBean = new Gson().fromJson(json, LoginBean.class);

                                //保存数据
                                SPUtils.put(LoginActivity.this,"loginJson",json);

                                //跳转页面
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();

                            } else {
                                showToast("账号或者密码不正确");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(String message) {
                        Log.e("TAG","登陆网络请求："+message);
                    }
                });
            }
        });


        /**
         * 点击注册：进行的操作
         */
        loginTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册页面
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }

}
