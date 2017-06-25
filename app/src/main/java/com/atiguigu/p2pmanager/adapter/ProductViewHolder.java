package com.atiguigu.p2pmanager.adapter;

import android.view.View;
import android.widget.TextView;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.adapter.base.ProdectBaseViewHolder;
import com.atiguigu.p2pmanager.bean.ProductBean;
import com.atiguigu.p2pmanager.utils.UIUtils;
import com.atiguigu.p2pmanager.view.ProgressView;

import butterknife.BindView;

/**
 * Created by PC on 2017/6/25.
 */

public class ProductViewHolder extends ProdectBaseViewHolder<ProductBean.DataBean> {


    @BindView(R.id.p_name)
    TextView pName;
    @BindView(R.id.p_money)
    TextView pMoney;
    @BindView(R.id.p_yearlv)
    TextView pYearlv;
    @BindView(R.id.p_suodingdays)
    TextView pSuodingdays;
    @BindView(R.id.p_minzouzi)
    TextView pMinzouzi;
    @BindView(R.id.p_minnum)
    TextView pMinnum;
    @BindView(R.id.p_progresss)
    ProgressView pProgresss;

    @Override
    public View initView() {
        return View.inflate(UIUtils.getContext(), R.layout.invest_product_item, null);
    }

    @Override
    public void setData(ProductBean.DataBean dataBeen) {
        pName.setText(dataBeen.getName());
        pMoney.setText(dataBeen.getMoney());
        pYearlv.setText(dataBeen.getYearRate());
        pSuodingdays.setText(dataBeen.getSuodingDays());
        pMinzouzi.setText(dataBeen.getMinTouMoney());
        pMinnum.setText(dataBeen.getMemberNum());
        pProgresss.setSweepAngle(Integer.parseInt(dataBeen.getProgress()));
    }
}
