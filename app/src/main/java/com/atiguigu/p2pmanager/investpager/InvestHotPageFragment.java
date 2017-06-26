package com.atiguigu.p2pmanager.investpager;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.base.BaseFragment;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Random;

import butterknife.BindView;

/**
 * Created by PC on 2017/6/24.
 */

public class InvestHotPageFragment extends BaseFragment {

    @BindView(R.id.invest_hot_flowlayout)
    TagFlowLayout flowlayout;

    //显示的文字的集合
    private String[] mVals = new String[]{
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
        return null;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        flowlayout.setAdapter(new TagAdapter<String>(mVals)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                //新建一个TextView
                TextView tv = new TextView(getContext());
                //设置textView的样式
                tv.setTextColor(Color.WHITE);//文字颜色
                tv.setTextSize(20);//文字大小

                //设置背景

//                Drawable drawable = getResources().getDrawable(R.drawable.invest_hot_shape);
//                tv.setBackgroundDrawable(drawable);
                tv.setBackgroundResource(R.drawable.invest_hot_shape);

                //产生随机颜色并设置
                Random random = new Random();
                int red = random.nextInt(100)+50;
                int green = random.nextInt(100)+50;
                int blue = random.nextInt(100)+50;
                GradientDrawable background = (GradientDrawable) tv.getBackground();
                background.setColor(Color.rgb(red,green,blue));

                //设置文本
                tv.setText(s);
                return tv;
            }
        });
    }

    @Override
    public int getSelfLayoutID() {
        return R.layout.fragment_invest_hot;
    }

    @Override
    public String getSelfUrl() {
        return null;
    }

}
