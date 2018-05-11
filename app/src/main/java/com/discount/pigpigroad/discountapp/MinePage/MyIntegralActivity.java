package com.discount.pigpigroad.discountapp.MinePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.R;
import com.discount.pigpigroad.discountapp.Slide.SlidingLayout;

/**
 * Created by PIGROAD on 2018/4/12.
 * Email:920015363@qq.com
 */

public class MyIntegralActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout ll_integral_exchang,ll_integral_usefor,ll_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myintegral_layout);
        init();
    }
    //初始化
    private void init() {
        ll_integral_exchang=findViewById(R.id.ll_integral_exchang);
        ll_integral_exchang.setOnClickListener(this);
        ll_integral_usefor=findViewById(R.id.ll_integral_usefor);
        ll_integral_usefor.setOnClickListener(this);
        ll_back=findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_integral_exchang:
                Intent intent=new Intent(MyIntegralActivity.this,IntergralExchaneActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_integral_usefor:
                Intent intent2=new Intent(MyIntegralActivity.this,IntergralUseforActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
