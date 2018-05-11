package com.discount.pigpigroad.discountapp.HomeActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.discount.pigpigroad.discountapp.HomeTodayViewPage.HTViewPage1;
import com.discount.pigpigroad.discountapp.HomeTodayViewPage.HTViewPage2;
import com.discount.pigpigroad.discountapp.HomeTodayViewPage.HTViewPage3;
import com.discount.pigpigroad.discountapp.HomeTodayViewPage.HTViewPage4;
import com.discount.pigpigroad.discountapp.R;
import com.discount.pigpigroad.discountapp.Slide.SlidingLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PIGROAD on 2018/3/21.
 */

public class HomeTodayActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewPager viewpager;
    private List<Fragment> list;
    private HomeTodayActivity.FragAdapter adapter;
    private int currentIndex;
    private int screenWidth;
    private ImageView line_iv;
    private TextView home_tv1,home_tv2,home_tv3,home_tv4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_todaynew_layout);
        initDate();
        setViewPage();
        if (enableSliding()) {
            SlidingLayout rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }
    }
    protected boolean enableSliding() {
        return true;
    }
    /**
     * 给适配器设置些参数
     */
    private void setViewPage() {
        adapter = new HomeTodayActivity.FragAdapter(getSupportFragmentManager(), list);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) line_iv
                .getLayoutParams();
        lp.width = screenWidth / 4;
        //viewpager点击事件
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float offset, int offsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) line_iv
                        .getLayoutParams();
                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                }

                else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (offset
                            * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                }
                else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                }
                else if (currentIndex == 2 && position == 2) // 2->3
                {
                    lp.leftMargin = (int) (offset
                            * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                }
                else if (currentIndex == 3 && position == 2) // 3->2
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                }
                else if (currentIndex == 3 && position == 3) // 3->4
                {
                    lp.leftMargin = (int) (offset
                            * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                }

                else if (currentIndex == 4 && position == 3) // 4->3
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                }
                line_iv.setLayoutParams(lp);
            }
            public void onPageSelected(int position) {
                currentIndex = position;
                resetTextView();
                switch (currentIndex){
                    case 0:
                        home_tv1.setTextColor(Color.parseColor("#f74848"));
                        break;
                    case 1:
                        home_tv2.setTextColor(Color.parseColor("#f74848"));
                        break;
                    case 2:
                        home_tv3.setTextColor(Color.parseColor("#f74848"));
                        break;
                    case 3:
                        home_tv4.setTextColor(Color.parseColor("#f74848"));
                        break;
                }
            }



            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    /**
     * 初始化变量
     */
    private void initDate() {
        line_iv=findViewById(R.id.line_iv);
        viewpager = findViewById(R.id.viewpager);
        list = new ArrayList<Fragment>();
        list.add(new HTViewPage1());
        list.add(new HTViewPage2());
        list.add(new HTViewPage3());
        list.add(new HTViewPage4());
        home_tv1=findViewById(R.id.home_tv1);
        home_tv2=findViewById(R.id.home_tv2);
        home_tv3=findViewById(R.id.home_tv3);
        home_tv4=findViewById(R.id.home_tv4);
        home_tv1.setOnClickListener(this);
        home_tv2.setOnClickListener(this);
        home_tv3.setOnClickListener(this);
        home_tv4.setOnClickListener(this);
    }


    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_tv1:
                viewpager.setCurrentItem(0);
                break;
            case R.id.home_tv2:
                viewpager.setCurrentItem(1);
                break;
            case R.id.home_tv3:
                viewpager.setCurrentItem(2);
                break;
            case R.id.home_tv4:
                viewpager.setCurrentItem(3);
                break;
        }
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
        home_tv1.setTextColor(R.color.black);
        home_tv2.setTextColor(R.color.black);
        home_tv3.setTextColor(R.color.black);
        home_tv4.setTextColor(R.color.black);
    }
}
