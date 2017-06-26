package com.atiguigu.p2pmanager.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.base.BaseActivity;
import com.atiguigu.p2pmanager.common.AppNetConfig;
import com.atiguigu.p2pmanager.utils.NetConnect;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.base_title)
    TextView baseTitle;
    @BindView(R.id.base_back)
    ImageView baseBack;
    @BindView(R.id.base_setting)
    ImageView baseSetting;
    @BindView(R.id.et_register_number)
    EditText etRegisterNumber;
    @BindView(R.id.et_register_name)
    EditText etRegisterName;
    @BindView(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @BindView(R.id.et_register_pwdagain)
    EditText etRegisterPwdagain;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    public int getLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    public void initTitle() {
        super.initTitle();
        baseTitle.setText("注册");
        baseBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

        baseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        /**
         * 点击注册
         */
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.获取数据
                String name = etRegisterName.getText().toString().trim();
                String number = etRegisterNumber.getText().toString().trim();
                String pwd = etRegisterPwd.getText().toString().trim();
                String pwd2 = etRegisterPwdagain.getText().toString().trim();

                //2.对数据判断
                if (TextUtils.isEmpty(number)){
                    showToast("手机号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(name)){
                    showToast("用户名不能为空");
                    return;
                }
                if (!TextUtils.isEmpty(pwd)){
                    if (!pwd.equals(pwd2)){
                        showToast("两次密码不一致");
                        return;
                    }
                }else{
                    showToast("密码不能为空");
                    return;
                }


                //3.联网注册
                Map<String, String> map = new HashMap<String, String>();
                map.put("name",name);
                map.put("phone",number);
                map.put("password",pwd);

                NetConnect.post(RegisterActivity.this, AppNetConfig.REGISTER, "register", map, new NetConnect.NetListener() {
                    @Override
                    public void onSuccess(String json) {
                        //Log.e("TAG","注册之后:"+json);
                        //解析请求到的数据
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(json);
                            boolean isExist = obj.getBoolean("isExist");

                            //判断
                            if(!isExist) {
                                //如果是false，说明没有注册过，注册成功

                                showToast("注册成功");
                                finish();

                            } else {
                                showToast("该用户已存在");
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        Log.e("TAG","注册之后失败:"+message);
                    }
                });

            }
        });

    }

}
