package com.discount.pigpigroad.discountapp.MinePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.MinePage.ExchangeRecord.ExchangeRecordActivity;
import com.discount.pigpigroad.discountapp.MinePage.ExchangeRecord.IntegralExchangDetailActivity;
import com.discount.pigpigroad.discountapp.R;


/**
 * Created by PIGROAD on 2018/4/12.
 * Email:920015363@qq.com
 */

public class IntergralExchaneActivity extends BaseActivity{
    private LinearLayout ll_back,ll_exchang_record;
    private Button bt_exchang;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.integral_exchang_layout);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        ll_back=findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ll_exchang_record=findViewById(R.id.ll_exchang_record);
        ll_exchang_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(IntergralExchaneActivity.this,ExchangeRecordActivity.class);
                startActivity(intent);
            }
        });
        bt_exchang=findViewById(R.id.bt_exchang);
        bt_exchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(IntergralExchaneActivity.this,IntegralExchangDetailActivity.class);
                startActivity(intent);
            }
        });
    }

}
