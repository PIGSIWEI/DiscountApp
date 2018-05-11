package com.discount.pigpigroad.discountapp;

import android.app.Fragment;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.discount.pigpigroad.discountapp.Fragment.ClassificationPage;
import com.discount.pigpigroad.discountapp.Fragment.HomePage;
import com.discount.pigpigroad.discountapp.Fragment.MinePage;
import com.discount.pigpigroad.discountapp.Fragment.SearchPage;
import com.discount.pigpigroad.discountapp.Slide.SlidingLayout;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{
    private HomePage homePage;
    private ClassificationPage classificationPage;
    private SearchPage searchPage;
    private MinePage minePage;
    private List<View> bottomList;
    private FragmentManager fragmentManager;
    private View relativebutton_1,relativebutton_2,relativebutton_3,relativebutton_4;
    private ImageView relative_iv_1,relative_iv_2,relative_iv_3,relative_iv_4;
    private TextView relative_tv_1,relative_tv_2,relative_tv_3,relative_tv_4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (enableSliding()) {
            SlidingLayout rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);
        init(); //数据初始化
        fragmentManager = getSupportFragmentManager();
        setSelectTab(0); //Fragment的初始化
    }
    /**
     * 初始化控件
     */
    protected boolean enableSliding() {
        return false;
    }

    private void init() {
        relative_iv_1=findViewById(R.id.relative_iv_1);
        relative_iv_2=findViewById(R.id.relative_iv_2);
        relative_iv_3=findViewById(R.id.relative_iv_3);
        relative_iv_4=findViewById(R.id.relative_iv_4);
        relative_tv_1=findViewById(R.id.relative_tv_1);
        relative_tv_2=findViewById(R.id.relative_tv_2);
        relative_tv_3=findViewById(R.id.relative_tv_3);
        relative_tv_4=findViewById(R.id.relative_tv_4);
        relativebutton_1=findViewById(R.id.relativebutton_1);
        relativebutton_2=findViewById(R.id.relativebutton_2);
        relativebutton_3=findViewById(R.id.relativebutton_3);
        relativebutton_4=findViewById(R.id.relativebutton_4);
        relativebutton_1.setOnClickListener(this);
        relativebutton_2.setOnClickListener(this);
        relativebutton_3.setOnClickListener(this);
        relativebutton_4.setOnClickListener(this);
        bottomList = new ArrayList<>(4);
        bottomList.add(relativebutton_1);
        bottomList.add(relativebutton_2);
        bottomList.add(relativebutton_3);
        bottomList.add(relativebutton_4);

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
                relative_iv_1.setImageResource(R.drawable.bottom_button1_click);
                relative_tv_1.setTextColor(Color.parseColor("#f74848"));

                if (homePage == null) {
                    homePage = new HomePage();
                    transaction.add(R.id.content, homePage);

                } else {
                    transaction.show(homePage);
                }
                break;
            case 1:
                relative_iv_2.setImageResource(R.drawable.bottom_button2_click);
                relative_tv_2.setTextColor(Color.parseColor("#f74848"));
                if (classificationPage == null) {
                    classificationPage = new ClassificationPage();
                    transaction.add(R.id.content, classificationPage);
                } else {
                    transaction.show(classificationPage);
                }
                break;
            case 2:
                relative_iv_3.setImageResource(R.drawable.bottom_button3_click);
                relative_tv_3.setTextColor(Color.parseColor("#f74848"));
                if (searchPage == null) {
                    searchPage = new SearchPage();
                    transaction.add(R.id.content, searchPage);
                } else {
                    transaction.show(searchPage);
                }
                break;
            case 3:
                relative_iv_4.setImageResource(R.drawable.bottom_button4_click);
                relative_tv_4.setTextColor(Color.parseColor("#f74848"));
                if (minePage == null) {
                    minePage = new MinePage();
                    transaction.add(R.id.content, minePage);
                } else {
                    transaction.show(minePage);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    /**
     * 底部栏切换
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relativebutton_1:
                setSelectTab(0);
                break;
            case R.id.relativebutton_2:
                setSelectTab(1);
                break;
            case R.id.relativebutton_3:
                setSelectTab(2);
                break;
            case R.id.relativebutton_4:
                setSelectTab(3);
                break;
        }
    }

    /**
     * 隐藏Fragment
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (homePage != null) {
            transaction.hide(homePage);
        }
        if (classificationPage != null) {
            transaction.hide(classificationPage);
        }
        if (searchPage != null) {
            transaction.hide(searchPage);
        }
        if (minePage != null) {
            transaction.hide(minePage);
        }

    }
    /**
     * 清楚fragment
     */
    private void clearSelection() {
        relative_iv_1.setImageResource(R.drawable.bottom_button1);
        relative_tv_1.setTextColor(Color.parseColor("#b7b9bc"));
        relative_iv_2.setImageResource(R.drawable.bottom_button2);
        relative_tv_2.setTextColor(Color.parseColor("#b7b9bc"));
        relative_iv_3.setImageResource(R.drawable.bottom_button3);
        relative_tv_3.setTextColor(Color.parseColor("#b7b9bc"));
        relative_iv_4.setImageResource(R.drawable.bottom_button4);
        relative_tv_4.setTextColor(Color.parseColor("#b7b9bc"));
    }
}
