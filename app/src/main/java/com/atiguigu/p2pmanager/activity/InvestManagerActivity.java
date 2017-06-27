package com.atiguigu.p2pmanager.activity;

import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.base.BaseActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import butterknife.BindView;


public class InvestManagerActivity extends BaseActivity {


    @BindView(R.id.base_title)
    TextView baseTitle;
    @BindView(R.id.base_back)
    ImageView baseBack;
    @BindView(R.id.base_setting)
    ImageView baseSetting;
    @BindView(R.id.chart)
    BarChart chart;

    @Override
    public int getLayoutID() {
        return R.layout.activity_invest_manager;
    }

    @Override
    public void initTitle() {
        super.initTitle();
        baseTitle.setText("柱状图");
        baseBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        Typeface mTf = Typeface.createFromAsset(this.getAssets(), "OpenSans-Regular.ttf");

       chart.setDescription("");
       chart.setDrawGridBackground(false);
       chart.setDrawBarShadow(false);

        XAxis xAxis =chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis =chart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setSpaceTop(20f);

        YAxis rightAxis =chart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);
        BarData mChartData = generateDataBar(2);

        mChartData.setValueTypeface(mTf);

        // set data
       chart.setData(mChartData);

        // do not forget to refresh the chart
//       chart.invalidate();
       chart.animateY(700);
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



    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private BarData generateDataBar(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry((int) (Math.random() * 70) + 30, i));
        }

        BarDataSet d = new BarDataSet(entries, "New DataSet " + cnt);
        d.setBarSpacePercent(20f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);

        BarData cd = new BarData(getMonths(), d);
        return cd;
    }


    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("Jan");
        m.add("Feb");
        m.add("Mar");
        m.add("Apr");
        m.add("May");
        m.add("Jun");
        m.add("Jul");
        m.add("Aug");
        m.add("Sep");
        m.add("Okt");
        m.add("Nov");
        m.add("Dec");

        return m;
    }



}
