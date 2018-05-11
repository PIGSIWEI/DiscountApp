package com.discount.pigpigroad.discountapp.MinePage.PayOrder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.R;

/**
 * Created by PIGROAD on 2018/4/19.
 * Email:920015363@qq.com
 */

public class FinshOrderActivity extends BaseActivity {
    private Button btn_return;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finshpay_layout);
        btn_return=findViewById(R.id.btn_return);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastClick()){
                    return;
                }
                finish();
            }
        });
    }
}
