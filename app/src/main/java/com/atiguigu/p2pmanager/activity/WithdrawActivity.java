package com.atiguigu.p2pmanager.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 提现界面
 */
public class WithdrawActivity extends BaseActivity {


    @BindView(R.id.base_title)
    TextView baseTitle;
    @BindView(R.id.base_back)
    ImageView baseBack;
    @BindView(R.id.base_setting)
    ImageView baseSetting;
    @BindView(R.id.account_zhifubao)
    TextView accountZhifubao;
    @BindView(R.id.select_bank)
    RelativeLayout selectBank;
    @BindView(R.id.chongzhi_text)
    TextView chongzhiText;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.et_input_money)
    EditText etInputMoney;
    @BindView(R.id.chongzhi_text2)
    TextView chongzhiText2;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.btn_tixian)
    Button btnTixian;

    @Override
    public int getLayoutID() {
        return R.layout.activity_withdraw;
    }

    @Override
    public void initTitle() {
        super.initTitle();
        baseTitle.setText("提现");
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
