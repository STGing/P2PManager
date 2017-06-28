package com.atiguigu.p2pmanager.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.base.BaseActivity;
import com.atiguigu.p2pmanager.bean.UpdateBean;
import com.atiguigu.p2pmanager.common.AppNetConfig;
import com.atiguigu.p2pmanager.common.ThreadManager;
import com.atiguigu.p2pmanager.utils.NetConnect;
import com.atiguigu.p2pmanager.utils.SPUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends BaseActivity {

    private TextView splash_tv_versionName;
    private RelativeLayout splash_rl;
    private File path;//下载APK文件的路径

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

                /**
                 * 1.判断有无网络
                 * 2.联网判断有无更新
                 * 3.下载更新
                 * 4.安装APP
                 */

                //1.判断网络
                if(isNetWork()) {
                    //有网

                    //2.联网判断有没有更新
                    //联网请求
                    NetConnect.get(SplashActivity.this, AppNetConfig.UPDATE, "update", new NetConnect.NetListener() {
                        @Override
                        public void onSuccess(String json) {
                            //联网请求成功
                            //判断请求到的数据
                            if(json.contains("404")) {
                                //如果包含404页面，说明没有找到页面，错误
                                startUpActivity();
                            } else {
                                //正常，提醒用户是否更新
                                //解析json数据
                                final UpdateBean updateBean = new Gson().fromJson(json, UpdateBean.class);
                                
                                //判断版本号
                                if(updateBean.getVersion().equals(getVersionCode())) {
                                    //如果和当前版本号相同，不更新
                                    startUpActivity();
                                } else {
                                    //更新,通知用户
                                    new AlertDialog.Builder(SplashActivity.this)
                                            .setTitle("发现新版本")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //选择确定，更新版本
                                                    //结束dialog
                                                    dialog.dismiss();
                                                    //3.下载更新
                                                    download(updateBean);


                                                }
                                            })
                                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    //结束当前dialog
                                                    dialog.dismiss();
                                                    //如果选择取消，跳转页面
                                                    startUpActivity();

                                                }
                                            })
                                            .show();

                                }


                            }

                        }

                        @Override
                        public void onFailure(String message) {
                            //联网失败
                        }
                    });

                } else {
                    //无网络
                    //告诉用户检查网络
                    showToast("请检查网络");
                    //跳转到界面
                    startUpActivity();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        splash_rl.startAnimation(aa);

    }

    /**
     * 下载apk文件
     * @param updateBean
     */
    private void download(final UpdateBean updateBean) {

        //获取文件下载的目录和文件名
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //如果SDCard存在
            path = getExternalFilesDir("");
        }  else {
            path = getFilesDir();
        }

        //设置文件名
        final File file = new File(path,"update.apk");

        //设置一个进度
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        //通过线程池，开分线程,下载
        ThreadManager.getInstance().getThreadPool().execute(new Runnable() {

            private FileOutputStream fos;
            private InputStream inputStream;

            @Override
            public void run() {
                //使用系统自带的手动解析
                try {
                    //1.地址
                    URL url = new URL(AppNetConfig.BASE_URL+"app_new.apk");
                    //2.连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //3.设置
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setRequestMethod("GET");
                    //4.判断连接
                    if(connection.getResponseCode() == 200) {
                        //连接成功
                        //1.设置最大进度
                        progressDialog.setMax(connection.getContentLength());

                        //2.开始下载
                        inputStream = connection.getInputStream();
                        fos = new FileOutputStream(file);

                        int length;
                        byte[] buffer = new byte[1024];

                        while ((length = inputStream.read(buffer)) != -1){
                            //更新进度条
                            progressDialog.incrementProgressBy(length);
                            //写入
                            fos.write(buffer,0,length);
                        }

                        //最后写完
                        //1.进度条消失
                        progressDialog.dismiss();
                        //2.安装apk文件
                        Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                        intent.setData(Uri.parse("file:"+file.getAbsolutePath()));
                        startActivity(intent);

                    } else {
                        showToast("下载失败");
                        startUpActivity();
                    }



                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }


    /**
     * 用于判断是否有网络
     * 有网络，返回true，否则false；
     * @return
     */
    private boolean isNetWork() {

        //定义一个变量，保持是否联网的值
        boolean conect = false;

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if(networkInfo != null) {
            conect = networkInfo.isConnected();
        }

        return conect;
    }

    /**
     * 用于跳转到登陆界面或者主界面
     */
    private void startUpActivity() {
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
    public void initListener() {

    }

    /**
     * 判断是否登陆过。
     * @return
     */
    private boolean isLogin() {
//        return true;
//        return false;
        //从本地获取保存的数据，如果有，说明登陆过
        String name = (String) SPUtils.get(SplashActivity.this, "name", "");
        if(!TextUtils.isEmpty(name)) {
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
