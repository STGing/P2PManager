package com.atiguigu.p2pmanager.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.base.BaseActivity;
import com.atiguigu.p2pmanager.utils.SPUtils;

public class SplashActivity extends BaseActivity {

    private TextView splash_tv_versionName;
    private RelativeLayout splash_rl;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//
//        AppManager.getInstance().addActivity(this);//加入任务栈中管理
//
//        initView();
//        initData();
//    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_splash;
    }

    public void initView() {

        splash_rl = (RelativeLayout)findViewById(R.id.splash_rl);
        splash_tv_versionName = (TextView)findViewById(R.id.splash_tv_versionName);


        //设置版本号:第一种方法(直接获取版本号并设置)
//        splash_tv_versionName.setText(getVersionCode());
        //设置版本号:第二种方法(通过使用占位符)
        String verson = String.format(splash_tv_versionName.getText().toString()
        ,getVersionCode());
        splash_tv_versionName.setText(verson);
    }

    /**
     * 获取APP的版本号
     * @return
     */
    private String getVersionCode() {

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

            //获取version name
            String versionName = packageInfo.versionName;
            return versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "1";
    }

    public void initData() {

        /**
         * 第一种方式:通过Timer，在2S后跳转页面
         */
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                //判断是否登陆
//                if(isLogin()) {
//                    //登陆过，就到主页面
//                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
//                    finish();
//                } else {
//                    //没有登陆，就跳转到登陆界面
//                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
//                    finish();
//                }
//
//            }
//        },2000);

        /**
         * 第二种方式，使用动画的监听，动画结束跳转。
         */
        AlphaAnimation aa = new AlphaAnimation(0,1);
        aa.setDuration(2000);

        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束的时候
                //判断是否登陆
                if(isLogin()) {
                    //登陆过，就到主页面
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                } else {
                    //没有登陆，就跳转到登陆界面
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        splash_rl.startAnimation(aa);

    }

    @Override
    public void initListener() {

    }

    /**
     * 判断是否登陆过。
     * @return
     */
    private boolean isLogin() {
//        return true;
//        return false;
        //从本地获取保存的json数据，如果有，说明登陆过
        String json = (String) SPUtils.get(SplashActivity.this, "loginJson", "");
        if(!TextUtils.isEmpty(json)) {
            return true;
        }
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在结束页面的时候
        //释放动画
        splash_rl.clearAnimation();
    }
}
