package com.discount.pigpigroad.discountapp.MinePage.ExchangeRecord;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.Fragment.HomePage;
import com.discount.pigpigroad.discountapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PIGROAD on 2018/4/13.
 * Email:920015363@qq.com
 */

public class ExchangeRecordActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_text1,tv_text2,tv_text3;
    private ViewPager viewpager;
    private List<Fragment> list;
    private FragAdapter adapter;
    private int currentIndex;
    private int screenWidth;
    private ImageView line_iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange_record_layout);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        viewpager=findViewById(R.id.viewpager);
        line_iv=findViewById(R.id.line_iv);
        tv_text1=findViewById(R.id.tv_text1);
        tv_text2=findViewById(R.id.tv_text2);
        tv_text3=findViewById(R.id.tv_text3);
        tv_text1.setOnClickListener(this);
        tv_text2.setOnClickListener(this);
        tv_text3.setOnClickListener(this);
        list = new ArrayList<Fragment>();
        list.add(new ExchangeRecordPage1());
        list.add(new ExchangeRecordPage2());
        list.add(new ExchangeRecordPage3());
        adapter = new FragAdapter(getSupportFragmentManager(), list);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        initSlide();

    }

    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_text1:
                viewpager.setCurrentItem(0);
                break;
            case R.id.tv_text2:
                viewpager.setCurrentItem(1);
                break;
            case R.id.tv_text3:
                viewpager.setCurrentItem(3);
                break;
        }
    }
    /**
     * 滑动条的初始化
     */
    public void initSlide(){
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) line_iv
                .getLayoutParams();
        lp.width = screenWidth / 3;
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float offset, int offsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) line_iv
                        .getLayoutParams();
                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                }

                else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (offset
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                }
                else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                }
                else if (currentIndex == 2 && position == 2) // 2->3
                {
                    lp.leftMargin = (int) (offset
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                }
                else if (currentIndex == 3 && position == 2) // 3->2
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                }
                line_iv.setLayoutParams(lp);
            }
            public void onPageSelected(int position) {
                currentIndex = position;
                resetTextView();
                switch (currentIndex){
                    case 0:
                        tv_text1.setTextColor(Color.parseColor("#f74848"));
                        break;
                    case 1:
                        tv_text2.setTextColor(Color.parseColor("#f74848"));
                        break;
                    case 2:
                        tv_text3.setTextColor(Color.parseColor("#f74848"));
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    /**
     * 声明适配器
     */
    class FragAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;
        public FragAdapter(FragmentManager fm) {
            super(fm);
        }
        public FragAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }
        @Override
        public Fragment getItem(int postion) {
            return list.get(postion);
        }
        @Override
        public int getCount() {
            return list.size();
        }
    }
    /**
     * 初始化颜色
     */
    @SuppressLint("ResourceAsColor")
    private void resetTextView() {
        tv_text1.setTextColor(R.color.black);
        tv_text2.setTextColor(R.color.black);
        tv_text3.setTextColor(R.color.black);

    }
}
