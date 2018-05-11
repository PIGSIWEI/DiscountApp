package com.discount.pigpigroad.discountapp.MinePage.TipOff;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.MinePage.TipOff.MyTipOff.MyTipOffActivity;
import com.discount.pigpigroad.discountapp.R;

/**
 * Created by PIGROAD on 2018/4/17.
 * Email:920015363@qq.com
 */

public class TipOffActivity extends BaseActivity {
    private LinearLayout ll_back;
    private Button btn_get_tipoff_detail,btn_tipoff_rule;
    private TextView tv_tipoff_rule;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tipoff_layout);
        init();
    }

    private void init() {
        btn_tipoff_rule=findViewById(R.id.btn_tipoff_rule);
        btn_tipoff_rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TipOffActivity.this,MyTipOffActivity.class);
                startActivity(intent);
            }
        });
        tv_tipoff_rule=findViewById(R.id.tv_tipoff_rule);
        tv_tipoff_rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TipOffActivity.this,TipOffRuleActivity.class);
                startActivity(intent);
            }
        });
        ll_back=findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_get_tipoff_detail=findViewById(R.id.btn_get_tipoff_detail);
        btn_get_tipoff_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TipOffActivity.this,TipOffDetail.class);
                startActivity(intent);
            }
        });
    }
}
