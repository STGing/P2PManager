package com.atiguigu.p2pmanager.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.base.BaseFragment;
import com.atiguigu.p2pmanager.bean.LoginBean;
import com.atiguigu.p2pmanager.common.AppNetConfig;
import com.atiguigu.p2pmanager.utils.SPUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by PC on 2017/6/20.
 */

public class MainPropertyFragment extends BaseFragment {

    @BindView(R.id.tv_settings)
    TextView tvSettings;
    @BindView(R.id.iv_me_icon)
    ImageView ivMeIcon;
    @BindView(R.id.rl_me_icon)
    RelativeLayout rlMeIcon;
    @BindView(R.id.tv_me_name)
    TextView tvMeName;
    @BindView(R.id.rl_me)
    RelativeLayout rlMe;
    @BindView(R.id.recharge)
    ImageView recharge;
    @BindView(R.id.withdraw)
    ImageView withdraw;
    @BindView(R.id.ll_touzi)
    TextView llTouzi;
    @BindView(R.id.ll_touzi_zhiguan)
    TextView llTouziZhiguan;
    @BindView(R.id.ll_zichan)
    TextView llZichan;

    @Override
    public void initTitle() {

    }

    @Override
    public View initView() {

        return null;
    }

    @Override
    public void initListener() {

        //点击“设置按钮”的事件
        tvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void initData() {

        //设置头像
        Picasso.with(getActivity())
                .load(AppNetConfig.BASE_URL+"images/tx.png")
                .transform(new CropCircleTransformation())
                .into(ivMeIcon);

        //设置用户名
        //从保存的获取数据
        String json = (String) SPUtils.get(mContext, "loginJson", "");
        //解析
        LoginBean loginBean = new Gson().fromJson(json, LoginBean.class);
        if(loginBean != null) {
            String name = loginBean.getName();
            tvMeName.setText(name);
        }

    }

    @Override
    public int getSelfLayoutID() {
        return R.layout.fragment_property;
    }

    @Override
    public String getSelfUrl() {
        return null;
    }


}
