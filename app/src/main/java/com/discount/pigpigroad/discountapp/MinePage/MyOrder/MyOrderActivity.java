package com.discount.pigpigroad.discountapp.MinePage.MyOrder;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.MinePage.TipOff.MyTipOff.MyTipOffActivity;
import com.discount.pigpigroad.discountapp.MinePage.TipOff.MyTipOff.MyTipOffPage1;
import com.discount.pigpigroad.discountapp.MinePage.TipOff.MyTipOff.MyTipOffPage2;
import com.discount.pigpigroad.discountapp.MinePage.TipOff.MyTipOff.MyTipOffPage3;
import com.discount.pigpigroad.discountapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PIGROAD on 2018/4/17.
 * Email:920015363@qq.com
 */

public class MyOrderActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_text1,tv_text2,tv_text3,tv_text4,tv_text;
    private LinearLayout ll_back;
    private FragmentManager fragmentManager;
    private AllOrderPage allOrderPage;
    private MyOrderPage1 myOrderPage1;
    private MyOrderPage2 myOrderPage2;
    private MyOrderPage3 myOrderPage3;
    private MyOrderPage4 myOrderPage4;
    private List<TextView> textselect;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorder_layout);
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
        tv_text=findViewById(R.id.tv_text);
        tv_text1=findViewById(R.id.tv_text1);
        tv_text2=findViewById(R.id.tv_text2);
        tv_text3=findViewById(R.id.tv_text3);
        tv_text4=findViewById(R.id.tv_text4);
        tv_text.setOnClickListener(this);
        tv_text1.setOnClickListener(this);
        tv_text2.setOnClickListener(this);
        tv_text3.setOnClickListener(this);
        tv_text4.setOnClickListener(this);
        textselect = new ArrayList<>(5);
        textselect.add(tv_text);
        textselect.add(tv_text1);
        textselect.add(tv_text2);
        textselect.add(tv_text3);
        textselect.add(tv_text4);
        fragmentManager = getSupportFragmentManager();
        setSelectTab(0);
    }
    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_text:
                setSelectTab(0);
                break;
            case R.id.tv_text1:
                setSelectTab(1);
                break;
            case R.id.tv_text2:
                setSelectTab(2);
                break;
            case R.id.tv_text3:
                setSelectTab(3);
                break;
            case R.id.tv_text4:
                setSelectTab(4);
                break;
        }
    }

    /**
     * Fragment的选择方法
     * @param selectTab
     */
    public void setSelectTab(int selectTab) {
        clearSelection();  //清楚所选
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);

        switch (selectTab) {
            case 0:
                tv_text.setTextColor(Color.parseColor("#f74848"));

                if (allOrderPage == null) {
                    allOrderPage = new AllOrderPage();
                    transaction.add(R.id.framelayout, allOrderPage);

                } else {
                    transaction.show(allOrderPage);
                }
                break;
            case 1:
                tv_text1.setTextColor(Color.parseColor("#f74848"));
                if (myOrderPage1 == null) {
                    myOrderPage1 = new MyOrderPage1();
                    transaction.add(R.id.framelayout, myOrderPage1);
                } else {
                    transaction.show(myOrderPage1);
                }
                break;
            case 2:
                tv_text2.setTextColor(Color.parseColor("#f74848"));
                if (myOrderPage2 == null) {
                    myOrderPage2 = new MyOrderPage2();
                    transaction.add(R.id.framelayout, myOrderPage2);
                } else {
                    transaction.show(myOrderPage2);
                }
                break;
            case 3:
                tv_text3.setTextColor(Color.parseColor("#f74848"));
                if (myOrderPage3 == null) {
                    myOrderPage3 = new MyOrderPage3();
                    transaction.add(R.id.framelayout, myOrderPage3);
                } else {
                    transaction.show(myOrderPage3);
                }
                break;
            case 4:
                tv_text4.setTextColor(Color.parseColor("#f74848"));
                if (myOrderPage4 == null) {
                    myOrderPage4 = new MyOrderPage4();
                    transaction.add(R.id.framelayout, myOrderPage4);
                } else {
                    transaction.show(myOrderPage4);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }
    /**
     * 清楚fragment
     */
    private void clearSelection() {
        tv_text.setTextColor(Color.parseColor("#b7b9bc"));
        tv_text1.setTextColor(Color.parseColor("#b7b9bc"));
        tv_text2.setTextColor(Color.parseColor("#b7b9bc"));
        tv_text3.setTextColor(Color.parseColor("#b7b9bc"));
        tv_text4.setTextColor(Color.parseColor("#b7b9bc"));
    }
    /**
     * 隐藏Fragment
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (allOrderPage != null) {
            transaction.hide(allOrderPage);
        }
        if (myOrderPage1 != null) {
            transaction.hide(myOrderPage1);
        }
        if (myOrderPage2 != null) {
            transaction.hide(myOrderPage2);
        }
        if (myOrderPage3 != null) {
            transaction.hide(myOrderPage3);
        }
        if (myOrderPage4 != null) {
            transaction.hide(myOrderPage4);
        }

    }
}
