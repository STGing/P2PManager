package com.atiguigu.p2pmanager.investpager;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.base.BaseFragment;
import com.atiguigu.p2pmanager.utils.UIUtils;
import com.atiguigu.p2pmanager.view.randomLayout.StellarMap;

import java.util.Random;

import butterknife.BindView;

/**
 * Created by PC on 2017/6/24.
 */

public class InvestRecommondPageFragment extends BaseFragment {

    @BindView(R.id.stellar_map)
    StellarMap stellarMap;

    //显示的文字的集合
    private String[] datas = new String[]{
            "人类基因补全计划", "绊脚石计划", "源计划",
            "辐射净水计划", "铁腕计划", "神罗计划",
            "质量效应仙女座", "如龙0神室町计划", "星之海洋中文计划",
            "讨鬼传无伤计划", "心灵杀手：Alen Wake","看门狗计划",
            "巫师3：昆特牌","尼尔：2B小姐姐"
    };

    @Override
    public void initTitle() {

    }

    @Override
    public View initView() {

        stellarMap.setAdapter(new MyAdapter());
        //必须调用如下的两个方法，否则stellarMap不能显示数据
        //设置显示的数据在x轴、y轴方向上的稀疏度
        stellarMap.setRegularity(5,7);
        //设置初始化显示的组别，以及是否需要使用动画
        stellarMap.setGroup(0,true);
        stellarMap.setInnerPadding(UIUtils.dip2px(10),UIUtils.dip2px(10),
                UIUtils.dip2px(10),UIUtils.dip2px(10));

        return null;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected int getSelfLayoutID() {
        return R.layout.fragment_invest_recommand;
    }

    @Override
    protected String getSelfUrl() {
        return null;
    }

    /**
     * 适配器
     */
    class MyAdapter implements StellarMap.Adapter {

        /*
        * 有几个组
        * */
        @Override
        public int getGroupCount() {
            /*
            * 获取有多少个组
            *
            * 先判断有没有余数 如果有余数就加1 这样组数就对了
            *
            * */
            int num = datas.length / 7;

            if (num % 7 != 0){
                num+=1;
            }
            return num;
        }

        /*
        * 每组有多少数量  根据不同的组返回不同的数量
        * */
        @Override
        public int getCount(int group) {
/*
            * 先判断是有没有余数
            *
            * 如果没有余数 每次返回7
            * 如果有余数 最后一次返回最后的余数
            *
            * */
            if (datas.length % 7 == 0){
                return 7;
            }else{
                if(group == datas.length / 7){
                    return datas.length % 7;
                }else{
                    return 7;
                }
            }
        }

        //返回view 根据不同的组返回不同的view
        @Override
        public View getView(int group, int position, View convertView) {

            TextView textView = new TextView(getActivity());
            textView.setText(datas[group*7+position]);
            //产生随机颜色
            Random random = new Random();
            int red = random.nextInt(100)+50;
            int green = random.nextInt(100)+50;
            int blue = random.nextInt(100)+50;

            textView.setTextColor(Color.rgb(red,green,blue));
            return textView;
        }

        //预留方法不用实现
        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        //返回下一组的组号
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            if (group == 0){
                return 1;
            }else{
                return 0;
            }
        }
    }

}
