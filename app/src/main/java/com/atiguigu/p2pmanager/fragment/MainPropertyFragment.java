package com.atiguigu.p2pmanager.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.activity.InvestActivity;
import com.atiguigu.p2pmanager.activity.InvestManagerActivity;
import com.atiguigu.p2pmanager.activity.PayActivity;
import com.atiguigu.p2pmanager.activity.PropertyActivity;
import com.atiguigu.p2pmanager.activity.SettingActivity;
import com.atiguigu.p2pmanager.activity.WithdrawActivity;
import com.atiguigu.p2pmanager.base.BaseFragment;
import com.atiguigu.p2pmanager.common.AppNetConfig;
import com.atiguigu.p2pmanager.utils.SPUtils;
import com.squareup.picasso.Picasso;

import java.io.File;

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
                //启动到设置页面
                startActivity(new Intent(mContext,SettingActivity.class));
            }
        });

        //点击充值的事件处理
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到充值界面
                startActivity(new Intent(mContext,PayActivity.class));
            }
        });


        //点击提现
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到提现界面
                startActivity(new Intent(mContext,WithdrawActivity.class));
            }
        });


        //点击投资管理
        llTouzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到投资管理界面
                startActivity(new Intent(mContext,InvestActivity.class));
            }
        });

        //点击投资管理（直观）
        llTouziZhiguan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到投资管理界面
                startActivity(new Intent(mContext,InvestManagerActivity.class));
            }
        });

        //点击资产管理
        llZichan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到投资管理界面
                startActivity(new Intent(mContext,PropertyActivity.class));
            }
        });
    }

    @Override
    public void initData() {

        //设置头像
        //根据之前有无设置过头像，如果设置过就用设置的，没有就用系统的
        boolean isSwitchAvatar = (boolean) SPUtils.get(mContext, "isSwitchAvatar", false);

        if(isSwitchAvatar) {
            //如果有，获取图片路径
            String avatarPath = (String) SPUtils.get(mContext, "avatarPath", "");
            if(!TextUtils.isEmpty(avatarPath)) {
                //不为空
                Picasso.with(getActivity())
                        .load(new File(avatarPath))
                        .transform(new CropCircleTransformation())
                        .into(ivMeIcon);
            }

        } else {
            //如果没有
            Picasso.with(getActivity())
                    .load(AppNetConfig.BASE_URL+"images/tx.png")
                    .transform(new CropCircleTransformation())
                    .into(ivMeIcon);
        }


        //设置用户名
        //从保存的获取数据
        String name = (String) SPUtils.get(mContext, "name", "");
        if(!TextUtils.isEmpty(name)) {
            tvMeName.setText(name);
        }

    }

    /**
     * 在每次获取焦点的时候，设置头像
     */
    @Override
    public void onResume() {
        super.onResume();

        initData();
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
