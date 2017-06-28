package com.atiguigu.p2pmanager.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.atiguigu.p2pmanager.R;
import com.atiguigu.p2pmanager.activity.gesture.GestureEditActivity;
import com.atiguigu.p2pmanager.base.BaseFragment;
import com.atiguigu.p2pmanager.common.AppNetConfig;
import com.atiguigu.p2pmanager.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by PC on 2017/6/20.
 */

public class MainMoreFragment extends BaseFragment {


    @BindView(R.id.base_title)
    TextView baseTitle;
    @BindView(R.id.base_back)
    ImageView baseBack;
    @BindView(R.id.base_setting)
    ImageView baseSetting;
    @BindView(R.id.tv_more_regist)
    TextView tvMoreRegist;
    @BindView(R.id.toggle_more)
    ToggleButton toggleMore;
    @BindView(R.id.tv_more_reset)
    TextView tvMoreReset;
    @BindView(R.id.tv_more_phone)
    TextView tvMorePhone;
    @BindView(R.id.rl_more_contact)
    RelativeLayout rlMoreContact;
    @BindView(R.id.tv_more_fankui)
    TextView tvMoreFankui;
    @BindView(R.id.tv_more_share)
    TextView tvMoreShare;
    @BindView(R.id.tv_more_about)
    TextView tvMoreAbout;

    @Override
    public void initTitle() {

    }

    @Override
    public View initView() {


        return null;
    }

    @Override
    public void initListener() {

        //点击togglebutton的事件
        toggleMore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    //如果是true说明开启

                    //从SP文件中查看之前是否有设置过手势密码
                    boolean isGesture = (boolean) SPUtils.get(mContext, "isGesture", false);

                    //判断
                    if(!isGesture) {
                        //没有设置过
                        //保存，设置过的状态
                        SPUtils.put(mContext,"isGesture",isGesture);

                        //，跳转页面
                        startActivity(new Intent(mContext, GestureEditActivity.class));

                    }
                    //保存开关状态
                    SPUtils.put(mContext,"isChecked",isChecked);

                } else {
                    //false说明关闭，保存开关状态
                    SPUtils.put(mContext,"isChecked",isChecked);
                }
            }
        });


        //点击重置手势密码
        tvMoreReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //先获取有没有打开手势密码
                boolean isChecked = (boolean) SPUtils.get(mContext, "isChecked", false);
                if(isChecked) {
                    //打开
                    startActivity(new Intent(mContext,  GestureEditActivity.class));
                } else {
                    //关闭
                    Toast.makeText(mContext, "请打开手势密码开关", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //联系客服
        tvMorePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle("联系客服")
                        .setMessage("是否联系客服010-56253825")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(Intent.ACTION_CALL);

                                Uri uri = Uri.parse("tel:010-56253825");
                                intent.setData(uri);
                                try {
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });


        //反馈消息
        tvMoreFankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //自定义了一个View

                final View view = View.inflate(getActivity(),R.layout.more_fragment_dialog,null);
                final RadioGroup fankui = (RadioGroup) view.findViewById(R.id.rg_fankui);
                final EditText et = (EditText) view.findViewById(R.id.et_fankui_content);

                new AlertDialog.Builder(getActivity())
                        .setView(view)
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //获取数据，然后发送给服务
                                fankui.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                                        RadioButton rb =
                                                (RadioButton) view.findViewById(checkedId);
                                        String department = rb.getText().toString().trim();
                                        String message = et.getText().toString();

                                        Map<String,String> map = new HashMap<String, String>();
                                        map.put("department",department);
                                        map.put("content",message);

                                        //联网发送Post消息
                                        OkGo.<String>post(AppNetConfig.FEEDBACK)
                                                .tag(mContext)
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onSuccess(Response<String> response) {

                                                    }

                                                    @Override
                                                    public void onError(Response<String> response) {
                                                        super.onError(response);
                                                    }
                                                });
                                    }
                                });

                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void initData() {
        //初始化的时候

        //1.从SP中获取开关手势状态的信息
        boolean isChecked = (boolean) SPUtils.get(mContext, "isChecked", false);
        if(isChecked) {
            //如果选中，改变按钮状态
            toggleMore.setChecked(isChecked);
        }

    }

    @Override
    public int getSelfLayoutID() {
        return R.layout.fragment_more;
    }

    @Override
    public String getSelfUrl() {
        return null;
    }


}
