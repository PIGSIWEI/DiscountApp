package com.discount.pigpigroad.discountapp.MinePage.PayOrder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.R;

/**
 * Created by PIGROAD on 2018/4/19.
 * Email:920015363@qq.com
 */

public class SettlementActivity extends BaseActivity implements View.OnClickListener{
    private RadioButton rb_1,rb_2,rb_3;
    private LinearLayout ll_wxpay,ll_bankpay,ll_zfbpay;
    private Button btn_finsh;
    private TextView tv_price;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settlement_layout);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        btn_finsh=findViewById(R.id.btn_finsh);
        rb_1=findViewById(R.id.rb_1);
        rb_2=findViewById(R.id.rb_2);
        rb_3=findViewById(R.id.rb_3);
        ll_wxpay=findViewById(R.id.ll_wxpay);
        ll_bankpay=findViewById(R.id.ll_bankpay);
        ll_zfbpay=findViewById(R.id.ll_zfbpay);
        tv_price=findViewById(R.id.tv_price);
        tv_price.setText(getIntent().getStringExtra("price"));
        ll_wxpay.setOnClickListener(this);
        ll_bankpay.setOnClickListener(this);
        ll_zfbpay.setOnClickListener(this);
        btn_finsh.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_wxpay:
                rb_1.setChecked(true);
                rb_2.setChecked(false);
                rb_3.setChecked(false);
                break;
            case R.id.ll_bankpay:
                rb_1.setChecked(false);
                rb_2.setChecked(true);
                rb_3.setChecked(false);
                break;
            case R.id.ll_zfbpay:
                rb_1.setChecked(false);
                rb_2.setChecked(false);
                rb_3.setChecked(true);
                break;
            case R.id.btn_finsh:
                if (isFastClick()){
                    return;
                }
                Intent intent=new Intent(SettlementActivity.this,FinshOrderActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
