package com.discount.pigpigroad.discountapp.MinePage.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.MyApplication;
import com.discount.pigpigroad.discountapp.R;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

/**
 * Created by PIGROAD on 2018/4/13.
 * Email:920015363@qq.com
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{
    private Button btn_exit;
    private LinearLayout ll_feedback,ll_commonproblem,ll_aboutus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        ll_aboutus=findViewById(R.id.ll_aboutus);
        ll_aboutus.setOnClickListener(this);
        ll_commonproblem=findViewById(R.id.ll_commonproblem);
        ll_commonproblem.setOnClickListener(this);
        ll_feedback=findViewById(R.id.ll_feedback);
        ll_feedback.setOnClickListener(this);
        btn_exit=findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TDialog.Builder(getSupportFragmentManager())
                        .addOnClickListener(R.id.btn_cancel, R.id.btn_exit)
                        .setOnViewClickListener(new OnViewClickListener() {
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                switch (view.getId()) {
                                    case R.id.btn_cancel:
                                        tDialog.dismiss();
                                        break;
                                    case R.id.btn_exit:
                                        MyApplication.ExitClear(SettingActivity.this);
                                        Toast.makeText(SettingActivity.this, "退出登录成功", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        })
                        .setLayoutRes(R.layout.exit_dialog)                        //弹窗布局
                        .setScreenWidthAspect(SettingActivity.this, 0.9f)               //屏幕宽度比
                        .setDimAmount(0f)                                                    //设置焦点
                        .create()
                        .show();
            }
        });
    }

    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //意见反馈
            case R.id.ll_feedback:
                Intent intent=new Intent(SettingActivity.this,FeedBackActivity.class);
                startActivity(intent);
                break;
            //常见问题
            case R.id.ll_commonproblem:
                Intent intent1=new Intent(SettingActivity.this,CommonProblemActivity.class);
                startActivity(intent1);
                break;
            //关于我们
            case R.id.ll_aboutus:
                Intent intent2=new Intent(SettingActivity.this,AboutUsActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
