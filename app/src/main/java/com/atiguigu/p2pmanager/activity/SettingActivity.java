package com.atiguigu.p2pmanager.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.base.BaseActivity;
import com.atiguigu.p2pmanager.common.AppNetConfig;
import com.atiguigu.p2pmanager.utils.SPUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class SettingActivity extends BaseActivity {


    @BindView(R.id.base_title)
    TextView baseTitle;
    @BindView(R.id.base_back)
    ImageView baseBack;
    @BindView(R.id.base_setting)
    ImageView baseSetting;
    @BindView(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @BindView(R.id.tv_user_change)
    TextView tvUserChange;
    @BindView(R.id.btn_user_logout)
    Button btnUserLogout;

    @Override
    public int getLayoutID() {
        return R.layout.activity_setting;
    }

    @Override
    public void initTitle() {
        super.initTitle();
        baseTitle.setText("设置");
        baseBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        //第一次进入的时候，设置头像

        //判断之前有没有自定义过头像，如果有：使用自定义的。没有：使用系统的
        //从本地保存的获取
        boolean isSwitchAvatar = (boolean) SPUtils.get(this, "isSwitchAvatar", false);
        if(isSwitchAvatar) {
            //如果更换过头像，就使用更换的
            String avatarPath = (String) SPUtils.get(this, "avatarPath", "");
            if(!TextUtils.isEmpty(avatarPath)) {
                //不为空
                Picasso.with(this)
                        .load(new File(avatarPath))
                        .transform(new CropCircleTransformation())
                        .into(ivUserIcon);
            }
        } else {
            //否则，使用系统的
            Picasso.with(this)
                    .load(AppNetConfig.BASE_URL+"images/tx.png")
                    .transform(new CropCircleTransformation())
                    .into(ivUserIcon);
        }


    }

    @Override
    public void initListener() {
        //点击返回的时候
        baseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //点击更换头像
        tvUserChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SettingActivity.this)
                            .setItems(new String[]{"相机", "相册"}, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0){
                                        showCamera();
                                    }else{
                                        showPhoto();
                                    }
                                }
                            })
                            .show();
            }
        });
    }

    /**
     * 调用系统相机
     */
    private void showCamera() {

        GalleryFinal.openCamera(01, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                //打开成功,拍摄照片

                //处理照片
                progressImage(resultList.get(0).getPhotoPath());
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }

    /**
     * 处理拍摄到的照片
     * @param photoPath
     */
    private void progressImage(String photoPath) {

        //Log.e("TAG","处理照片----"+photoPath);

        //将照片设置到头像位置
        Picasso.with(this)
                .load(new File(photoPath))
                .transform(new CropCircleTransformation())
                .into(ivUserIcon);
        //保存状态
        SPUtils.put(this,"isSwitchAvatar",true);
        //保存图片路径
        SPUtils.put(this,"avatarPath",photoPath);
    }

    /**
     * 调用本地相册
     */
    private void showPhoto() {

        GalleryFinal.openGallerySingle(02, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                //打开相册

                //选择照片，处理照片
                progressImage(resultList.get(0).getPhotoPath());
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }
}
