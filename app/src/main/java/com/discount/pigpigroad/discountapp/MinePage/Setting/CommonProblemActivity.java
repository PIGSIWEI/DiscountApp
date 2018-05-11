package com.discount.pigpigroad.discountapp.MinePage.Setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.R;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

/**
 * Created by PIGROAD on 2018/4/14.
 * Email:920015363@qq.com
 */

public class CommonProblemActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout ll_1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commonproblem_layout);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        ll_1=findViewById(R.id.ll_1);
        ll_1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_1:
                new TDialog.Builder(getSupportFragmentManager())
                        .addOnClickListener(R.id.iv_exit)
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                switch (view.getId()){
                                    case R.id.iv_exit:
                                        tDialog.dismiss();
                                        break;
                                }
                            }
                        })
                        .setLayoutRes(R.layout.commonproblem_dialog)                        //弹窗布局
                        .setScreenWidthAspect(CommonProblemActivity.this, 0.9f)               //屏幕宽度比
                        .setScreenHeightAspect(CommonProblemActivity.this, 0.9f)
                        .setDimAmount(0f)                                                    //设置焦点
                        .create()
                        .show();
                break;
        }

    }
}
