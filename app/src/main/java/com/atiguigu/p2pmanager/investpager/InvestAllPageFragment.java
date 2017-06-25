package com.atiguigu.p2pmanager.investpager;

import android.view.View;
import android.widget.ListView;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.adapter.ProductAdapter;
import com.atiguigu.p2pmanager.base.BaseFragment;
import com.atiguigu.p2pmanager.bean.ProductBean;
import com.atiguigu.p2pmanager.common.AppNetConfig;
import com.google.gson.Gson;

import butterknife.BindView;

/**
 * Created by PC on 2017/6/24.
 */

public class InvestAllPageFragment extends BaseFragment {

    private ProductAdapter adapter;

    @BindView(R.id.lv)
    ListView lv;

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

    }

    @Override
    public int getSelfLayoutID() {
        return R.layout.fragment_invest_all;
    }

    @Override
    public String getSelfUrl() {
        return AppNetConfig.PRODUCT;
    }

    /**
     * 联网获取数据
     *
     * @param json
     */
    @Override
    public void getNetResult(String json) {
        super.getNetResult(json);

        ProductBean productBean = new Gson().fromJson(json, ProductBean.class);

        //设置适配器
        adapter = new ProductAdapter(mContext,productBean.getData());
        lv.setAdapter(adapter);

    }

}
