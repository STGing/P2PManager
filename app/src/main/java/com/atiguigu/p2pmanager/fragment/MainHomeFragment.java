package com.atiguigu.p2pmanager.fragment;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.base.BaseFragment;
import com.atiguigu.p2pmanager.bean.IndexBean;
import com.atiguigu.p2pmanager.common.AppNetConfig;
import com.atiguigu.p2pmanager.utils.NetConnect;
import com.atiguigu.p2pmanager.view.ProgressView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 主页面的Fragment
 */

public class MainHomeFragment extends BaseFragment {


    @BindView(R.id.base_title)
    TextView baseTitle;
    @BindView(R.id.base_back)
    ImageView baseBack;
    @BindView(R.id.base_setting)
    ImageView baseSetting;
    @BindView(R.id.home_fragment_banner)
    Banner homeFragmentBanner;
    @BindView(R.id.tv_home_product)
    TextView tvHomeProduct;
    @BindView(R.id.tv_home_yearrate)
    TextView tvHomeYearrate;
    Unbinder unbinder;
    @BindView(R.id.home_fragment_progressView)
    ProgressView homeFragmentProgressView;

    private List<String> list = new ArrayList<>();//用于存放图片地址的集合

    @Override
    public View initView() {
        View rootView = View.inflate(mContext, R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void initTitle() {
        baseTitle.setText("首页");
        baseBack.setVisibility(View.VISIBLE);
        baseSetting.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        //数据联网请求获取
        NetConnect.get(mContext, AppNetConfig.INDEX, "homeFragment", new NetConnect.NetListener() {
            @Override
            public void onSuccess(String json) {
                //联网成功
                //解析json数据

                progressData(json);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    /**
     * 处理联网请求获取到的数据
     */
    private void progressData(String json) {
        //1.使用gson解析
        IndexBean indexBean = new Gson().fromJson(json, IndexBean.class);
        //2.使用手动解析json数据

        //初始化Banner
        initBanner(indexBean);

        //初始化ProgressView
        initProgressView(indexBean);

    }

    /**
     * 初始化ProgressVIew
     * @param indexBean
     */
    private void initProgressView(IndexBean indexBean) {
        String progress = indexBean.getProInfo().getProgress();

        //设置自定义ProgressView的进度
        homeFragmentProgressView.setSweepAngle(Integer.parseInt(progress));
    }

    /**
     * 初始化Banner
     *
     * @param indexBean
     */
    private void initBanner(IndexBean indexBean) {
        //获取数据
        List<IndexBean.ImageArrBean> imageArr = indexBean.getImageArr();
        //将数据放入集合中
        for (int i = 0; i < imageArr.size(); i++) {
            String imaurl = imageArr.get(i).getIMAURL();
            list.add(AppNetConfig.BASE_URL + imaurl);
        }
        //设置图片加载器
        homeFragmentBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        homeFragmentBanner.setImages(list);
        //banner设置方法全部调用完毕时最后调用
        homeFragmentBanner.start();

    }

    @Override
    public void initListener() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.base_back, R.id.base_setting, R.id.tv_home_product, R.id.tv_home_yearrate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_back:
                break;
            case R.id.base_setting:
                break;
            case R.id.tv_home_product:
                break;
            case R.id.tv_home_yearrate:
                break;
        }
    }


    /**
     * 用于广告轮播设置图片
     */
    private class GlideImageLoader implements ImageLoaderInterface {
        @Override
        public void displayImage(Context context, Object path, View imageView) {
            //使用picasso，加载图片
            Picasso.with(mContext)
                    .load((String) path)
                    .into((ImageView) imageView);
        }

        @Override
        public View createImageView(Context context) {
            return null;
        }
    }
}
