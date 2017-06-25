package com.atiguigu.p2pmanager.adapter;

import android.content.Context;

import com.atiguigu.p2pmanager.adapter.base.ProdectBaseViewHolder;
import com.atiguigu.p2pmanager.adapter.base.ProductBaseAdapter;
import com.atiguigu.p2pmanager.bean.ProductBean;

import java.util.List;

/**
 * Created by PC on 2017/6/25.
 */

public class ProductAdapter extends ProductBaseAdapter {


    public ProductAdapter(Context mContext, List<ProductBean.DataBean> data) {
        super(mContext, data);
    }

    @Override
    public ProdectBaseViewHolder getViewHolder() {
        return new ProductViewHolder();
    }
}
