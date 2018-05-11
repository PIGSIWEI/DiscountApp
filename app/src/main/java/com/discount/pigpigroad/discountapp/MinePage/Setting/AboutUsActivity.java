package com.discount.pigpigroad.discountapp.MinePage.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.R;

/**
 * Created by PIGROAD on 2018/4/14.
 * Email:920015363@qq.com
 */

public class AboutUsActivity extends BaseActivity {
    private LinearLayout ll_invite;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus_layout);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        ll_invite=findViewById(R.id.ll_invite);
        ll_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AboutUsActivity.this,InviteActivity.class);
                startActivity(intent);
            }
        });
    }
}
