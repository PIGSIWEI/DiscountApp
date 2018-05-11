package com.discount.pigpigroad.discountapp.MinePage.Setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.R;

/**
 * Created by PIGROAD on 2018/4/13.
 * Email:920015363@qq.com
 */

public class FeedBackActivity extends BaseActivity {
    private LinearLayout ll_select_feedback;
    private TextView tv_feedback;
    private String[] Str={"功能意见","页面意见","你的新需求","操作意见","搜索意见"};
    int i;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_layout);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        tv_feedback=findViewById(R.id.tv_feedback);
        ll_select_feedback=findViewById(R.id.ll_select_feedback);
        ll_select_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (tv_feedback.getText().toString().trim()){
                    case "功能意见":
                        i=0;
                        break;
                    case "页面意见":
                        i=1;
                        break;
                    case "你的新需求":
                        i=2;
                        break;
                    case "操作意见":
                        i=3;
                        break;
                    case "搜索意见":
                        i=4;
                        break;
                }
                new AlertDialog.Builder(FeedBackActivity.this)
                        .setSingleChoiceItems(Str, i,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        tv_feedback.setText(Str[which]);
                                        dialog.dismiss();
                                    }
                                }
                        )
                        .show();
            }
        });
    }
}
