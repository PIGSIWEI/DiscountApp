package com.discount.pigpigroad.discountapp.MinePage.TipOff;

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
 * Created by PIGROAD on 2018/4/17.
 * Email:920015363@qq.com
 */

public class TipOffDetail extends BaseActivity {
    private LinearLayout ll_changedata,ll_back;
    private String[] Str={"请选择","女装","男装","鞋品","美妆","配饰","鞋包","儿童","母婴","居家"};
    private TextView tv_changedata;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tipoff_detail_layout);
        init();
    }

    private void init() {
        ll_back=findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ll_changedata=findViewById(R.id.ll_changedata);
        tv_changedata=findViewById(R.id.tv_changedata);
        ll_changedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(TipOffDetail.this)
                        .setSingleChoiceItems(Str, 0,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        tv_changedata.setText(Str[which]);
                                        dialog.dismiss();
                                    }
                                }
                        )
                        .show();
            }
        });

    }
}
