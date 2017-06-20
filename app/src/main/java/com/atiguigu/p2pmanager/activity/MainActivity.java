package com.atiguigu.p2pmanager.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.fragment.MainHomeFragment;
import com.atiguigu.p2pmanager.fragment.MainInvestmentFragment;
import com.atiguigu.p2pmanager.fragment.MainMoreFragment;
import com.atiguigu.p2pmanager.fragment.MainPropertyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_frameLayout)
    FrameLayout mainFrameLayout;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_investment)
    RadioButton rbInvestment;
    @BindView(R.id.rb_property)
    RadioButton rbProperty;
    @BindView(R.id.rb_more)
    RadioButton rbMore;
    @BindView(R.id.main_radioGroup)
    RadioGroup mainRadioGroup;

    /**
     * 4个用于显示不同页面的Fragment
     */
    private MainHomeFragment homeFragment;
    private MainInvestmentFragment investmentFragment;
    private MainPropertyFragment propertyFragment;
    private MainMoreFragment moreFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initData();
        initListener();

        //默认选择第一个页面
        mainRadioGroup.check(R.id.rb_home);
    }

    /**
     * 初始化View
     */
    private void initView() {


    }

    /**
     * 初始化数据
     */
    private void initData() {

    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        mainRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switchRadioButton(checkedId);
            }
        });
    }

    /**
     * 切换RadioButton
     */
    public void switchRadioButton(int checkedId){
        //事物
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //隐藏其他已经显示的fragment
        hindFragments(ft);
        switch (checkedId) {
            case R.id.rb_home ://首页

                if(homeFragment == null) {
                    //如果是空，建立
                    homeFragment = new MainHomeFragment();
                    ft.add(R.id.main_frameLayout,homeFragment);
                } else {
                    //不为空就显示出来
                    ft.show(homeFragment);
                }

                break;
            case R.id.rb_investment ://投资

                if(investmentFragment == null) {
                    //如果是空，建立
                    investmentFragment = new MainInvestmentFragment();
                    ft.add(R.id.main_frameLayout,investmentFragment);
                } else {
                    //不为空就显示出来
                    ft.show(investmentFragment);
                }

                break;
            case R.id.rb_property ://资产

                if(propertyFragment == null) {
                    //如果是空，建立
                    propertyFragment = new MainPropertyFragment();
                    ft.add(R.id.main_frameLayout,propertyFragment);
                } else {
                    //不为空就显示出来
                    ft.show(propertyFragment);
                }

                break;
            case R.id.rb_more ://更多

                if(moreFragment == null) {
                    //如果是空，建立
                    moreFragment = new MainMoreFragment();
                    ft.add(R.id.main_frameLayout,moreFragment);
                } else {
                    //不为空就显示出来
                    ft.show(moreFragment);
                }

                break;
        }

        ft.commit();
    }

    /**
     * 隐藏其他fragments
     * @param ft
     */
    private void hindFragments(FragmentTransaction ft) {
        if (investmentFragment != null){
            ft.hide(investmentFragment);
        }
        if (moreFragment != null){
            ft.hide(moreFragment);
        }
        if (homeFragment != null){
            ft.hide(homeFragment);
        }
        if (propertyFragment != null){
            ft.hide(propertyFragment);
        }
    }
}
