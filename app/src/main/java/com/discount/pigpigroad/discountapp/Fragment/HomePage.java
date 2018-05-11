package com.discount.pigpigroad.discountapp.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.discount.pigpigroad.discountapp.HomeFragment.HomeFragment;
import com.discount.pigpigroad.discountapp.HomeFragment.SearchActivity;
import com.discount.pigpigroad.discountapp.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.discount.pigpigroad.discountapp.Api.ApiConnect.PP_TIP;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.URL_BASE;

/**
 * Created by XYSM on 2018/3/20.
 */

public class HomePage extends Fragment{
    private EditText et_search;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Fragment[] f;
    private List<String> titles;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homePageLayout = inflater.inflate(R.layout.homepage_layout,
                container, false);
        return homePageLayout;
    }
    /**
     *  初始化数据
     */
    private void init() {
        postData();
        tabLayout=getActivity().findViewById(R.id.tabLayout);
        viewPager=getActivity().findViewById(R.id.viewPage);
        tabLayout.setupWithViewPager(viewPager);
        et_search=getActivity().findViewById(R.id.et_search);
        et_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if((motionEvent.getAction() == MotionEvent.ACTION_DOWN)){
                    Intent intent=new Intent(getContext(), SearchActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
    public void postData(){
        OkGo.<String>post(URL_BASE+"?platform=android&request=public.product.store.special.list.get&store_id=1&type_id=1")
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //注意这里已经是在主线程了
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            int code = (int) jsonObject.get("code");
                            JSONObject jsonObject1=jsonObject.getJSONObject("data");
                            if (code == 0) {
                                titles=new ArrayList<>();
                                titles.add("全部");
                                JSONArray jsonArray=jsonObject1.getJSONArray("data");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject temp = new JSONObject(jsonArray.getString((i)));
                                    //获得title
                                    titles.add(temp.getString("title"));

                                }
                                Myadapter adapter=new Myadapter(getFragmentManager());
                                //联动
                                viewPager.setAdapter(adapter);
                            }else if (code == 1) {
                                String msg= (String) jsonObject.get("msg");
                                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(PP_TIP, "连接失败!!!!!!!!!!!!!!!");
                        Toast.makeText(getActivity(), "请检查你的网络设置", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    //适配器
    class Myadapter extends FragmentPagerAdapter {
        public Myadapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getfragment(position);
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return  titles.get(position);
        }
    }
    //动态创建Fragment的方法
    public Fragment  getfragment(int position){
            f=new Fragment[titles.size()];
            Fragment fg = f[position];
            if (fg == null) {
                fg = HomeFragment.getiniturl(position+"");
                f[position] = fg;
            }
            return fg;
    }
}
