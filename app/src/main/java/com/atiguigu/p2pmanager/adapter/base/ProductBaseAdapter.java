package com.atiguigu.p2pmanager.adapter.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by PC on 2017/6/25.
 */

public abstract class ProductBaseAdapter<T> extends BaseAdapter {


    private final List<T> t;
    private final Context mContext;

    public ProductBaseAdapter(Context mContext, List<T> t) {
        this.t = t;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return t == null ? 0 : t.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProdectBaseViewHolder viewHolder;

        if(convertView == null) {

            viewHolder = getViewHolder();

        } else {
            viewHolder = (ProdectBaseViewHolder) convertView.getTag();
        }

        viewHolder.setData(t.get(position));

        return viewHolder.getConvertView();
    }

    public abstract ProdectBaseViewHolder getViewHolder();


}
