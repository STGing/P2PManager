package com.atiguigu.p2pmanager.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.base.BaseFragment;
import com.atiguigu.p2pmanager.investpager.InvestAllPageFragment;
import com.atiguigu.p2pmanager.investpager.InvestHotPageFragment;
import com.atiguigu.p2pmanager.investpager.InvestRecommondPageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 本页面是由ViewPager构成：滑动切换3个Fragment
 */

public class MainInvestmentFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.invest_tv_all)
    TextView investTvAll;
    @BindView(R.id.invest_tv_recommond)
    TextView investTvRecommond;
    @BindView(R.id.invest_tv_hot)
    TextView investTvHot;
    @BindView(R.id.invest_viewPager)
    ViewPager investViewPager;

    //存放fragments的集合
    private List<BaseFragment> fragments;

    /**
     * 初始化标题
     */
    @Override
    public void initTitle() {

        investTvAll.setSelected(true);
    }



    @Override
    public View initView() {

        return null;
    }

    @Override
    public void initListener() {
        //顶部textView点击事件
        investTvAll.setOnClickListener(this);
        investTvRecommond.setOnClickListener(this);
        investTvHot.setOnClickListener(this);
    }

    @Override
    public void initData() {


        //初始化fragment集合数据
        fragments = new ArrayList<>();
        fragments.add(new InvestAllPageFragment());
        fragments.add(new InvestRecommondPageFragment());
        fragments.add(new InvestHotPageFragment());
        //设置ViewPager
        investViewPager.setAdapter(new MyPagerAdapter(getFragmentManager()));

        //设置ViewPager的事件监听
        investViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //当Viewpager切换选中的时候，标题也变化

                if(position == 0) {
                    investTvAll.setSelected(true);
                    investTvRecommond.setSelected(false);
                    investTvHot.setSelected(false);
                }
                if(position == 1) {
                    investTvAll.setSelected(false);
                    investTvRecommond.setSelected(true);
                    investTvHot.setSelected(false);
                }
                if(position == 2) {
                    investTvAll.setSelected(false);
                    investTvRecommond.setSelected(false);
                    investTvHot.setSelected(true);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected int getSelfLayoutID() {
        return R.layout.fragment_invest;
    }

    @Override
    protected String getSelfUrl() {
        return "";
    }

    /**
     * 顶部TextView的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.invest_tv_all :
                investViewPager.setCurrentItem(0);
                break;
            case R.id.invest_tv_recommond :
                investViewPager.setCurrentItem(1);
                break;
            case R.id.invest_tv_hot :
                investViewPager.setCurrentItem(2);
                break;
        }
    }


    /**
     * 内部类：ViewPager的适配器
     */
    private class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }
}
