package com.discount.pigpigroad.discountapp.HomeFragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.discount.pigpigroad.discountapp.CustomView.GlideImageLoader;
import com.discount.pigpigroad.discountapp.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;

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
 * Created by PIGROAD on 2018/4/16.
 * Email:920015363@qq.com
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    private View v, v1;
    //回调用来接收参数
    private List<String> images;
    private List<String> home_id;

    private Banner banner;
    private TextView tv_1, tv_2, tv_3, tv_4, tv_5;
    private ImageView im_1, im_2, im_3, im_4, im_5;
    private LinearLayout ll_1, ll_2, ll_3, ll_4, ll_5;
    private JSONObject temp;
    private GridView gridView;
    private ListView listView;
    private List<Map<String, String>> dataList;
    private BaseAdapter adapter;
    private ListAdapter listAdapter;
    private Intent intent;

    public static HomeFragment getiniturl(String gc_id) {
        HomeFragment twoFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("gc_id", Integer.parseInt(gc_id));
        twoFragment.setArguments(bundle);
        return twoFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取参数
        v1 = View.inflate(getActivity(), R.layout.home_all_fragment, null);
        v = View.inflate(getActivity(), R.layout.homepage_fragment, null);
        home_id=new ArrayList<>();
        int gc_id = getArguments().getInt("gc_id");
        if (gc_id == 0) {
            if (v1 == null) {
                v1 = View.inflate(getActivity(), R.layout.home_all_fragment, null);
                return v1;
            }
            //开始写方法
            getbanner();  //获取轮播
            initdata();
            return v1;
        } else {
            if (v == null) {
                v = View.inflate(getActivity(), R.layout.homepage_fragment, null);
                return v;
            }
            return v;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner = (Banner) v1.findViewById(R.id.banner);
        banner.stopAutoPlay();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onClick(View view) {
        intent = new Intent(getLayoutInflater().getContext(), HomeColumnActivity.class);
        switch (view.getId()) {
            case R.id.ll_1:
                intent.putExtra("title", tv_1.getText());
                intent.putExtra("special_id", home_id.get(4));
                startActivity(intent);
                break;
            case R.id.ll_2:
                intent.putExtra("title", tv_2.getText());
                intent.putExtra("special_id", home_id.get(3));
                startActivity(intent);
                break;
            case R.id.ll_3:
                intent.putExtra("title", tv_3.getText());
                intent.putExtra("special_id", home_id.get(2));
                startActivity(intent);
                break;
            case R.id.ll_4:
                intent.putExtra("title", tv_4.getText());
                intent.putExtra("special_id", home_id.get(1));
                startActivity(intent);
                break;
            case R.id.ll_5:
                intent.putExtra("title", tv_5.getText());
                intent.putExtra("special_id", home_id.get(0));
                startActivity(intent);
                break;
        }
    }

    //////////////////////首页数据填充

    /**
     * banner的初始化
     */
    public void getbanner() {
        OkGo.<String>post(URL_BASE + "?platform=android&request=public.home.flash.auto.pic.get&store_id=1")
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //注意这里已经是在主线程了
                        String responseStr = response.body();//这个就是返回来的结果
                        Log.i(PP_TIP,responseStr);
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            images = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject temp = new JSONObject(jsonArray.getString((i)));
                                images.add(temp.getString("banner_url"));
                            }
                            banner = (Banner) v1.findViewById(R.id.banner);
                            //设置图片加载器
                            banner.setImageLoader(new GlideImageLoader());
                            //设置图片集合
                            banner.setImages(images);
                            banner.setDelayTime(3000);
                            //banner设置方法全部调用完毕时最后调用
                            banner.start();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(PP_TIP, "连接失败!!!!!!!!!!!!!!!");
                    }
                });


    }

    /**
     * 首页数据填充
     */
    private void initdata() {
        tv_1 = v1.findViewById(R.id.tv_1);
        tv_2 = v1.findViewById(R.id.tv_2);
        tv_3 = v1.findViewById(R.id.tv_3);
        tv_4 = v1.findViewById(R.id.tv_4);
        tv_5 = v1.findViewById(R.id.tv_5);
        im_1 = v1.findViewById(R.id.im_1);
        im_2 = v1.findViewById(R.id.im_2);
        im_3 = v1.findViewById(R.id.im_3);
        im_4 = v1.findViewById(R.id.im_4);
        im_5 = v1.findViewById(R.id.im_5);
        ll_1 = v1.findViewById(R.id.ll_1);
        ll_2 = v1.findViewById(R.id.ll_2);
        ll_3 = v1.findViewById(R.id.ll_3);
        ll_4 = v1.findViewById(R.id.ll_4);
        ll_5 = v1.findViewById(R.id.ll_5);
        ll_1.setOnClickListener(this);
        ll_2.setOnClickListener(this);
        ll_3.setOnClickListener(this);
        ll_4.setOnClickListener(this);
        ll_5.setOnClickListener(this);
        gridView = v1.findViewById(R.id.gridLayout);
        listView = v1.findViewById(R.id.listview);
        listView.setDividerHeight(0);
        getdata2();
        getdata3();
        getdata4();
    }

    /**
     * 获取首页数据
     */
    public void getdata2() {
        OkGo.<String>post(URL_BASE + "?platform=android&request=public.product.store.special.list.get&store_id=1&type_id=2")
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //注意这里已经是在主线程了
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = jsonObject1.getJSONArray("data");
                            Map<String, String> map = new HashMap<String, String>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                temp = new JSONObject(jsonArray.getString((i)));
                                map.put("title", temp.getString("title"));
                                map.put("special_id", temp.getString("special_id"));
                                map.put("img", temp.getString("img"));
                                setdata(i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(PP_TIP, "连接失败!!!!!!!!!!!!!!!");
                    }
                });

    }

    public void setdata(int i) {
        try {
            switch (i) {
                case 4:
                    tv_1.setText(temp.getString("title"));
                    Glide.with(getLayoutInflater().getContext()).load(temp.getString("img")).into(im_1);
                    home_id.add(temp.getString("special_id"));
                    break;
                case 3:
                    tv_2.setText(temp.getString("title"));
                    Glide.with(getLayoutInflater().getContext()).load(temp.getString("img")).into(im_2);
                    home_id.add(temp.getString("special_id"));
                    break;
                case 2:
                    tv_3.setText(temp.getString("title"));
                    Glide.with(getLayoutInflater().getContext()).load(temp.getString("img")).into(im_3);
                    home_id.add(temp.getString("special_id"));
                    break;
                case 1:
                    tv_4.setText(temp.getString("title"));
                    Glide.with(getLayoutInflater().getContext()).load(temp.getString("img")).into(im_4);
                    home_id.add(temp.getString("special_id"));
                    break;
                case 0:
                    tv_5.setText(temp.getString("title"));
                    Glide.with(getLayoutInflater().getContext()).load(temp.getString("img")).into(im_5);
                    home_id.add(temp.getString("special_id"));
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取第三行数据
     */
    public void getdata3() {
        OkGo.<String>post(URL_BASE + "?platform=android&request=public.product.store.special.list.get&store_id=1&type_id=3")
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = jsonObject1.getJSONArray("data");
                            dataList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Map<String, String> map = new HashMap<String, String>();
                                temp = new JSONObject(jsonArray.getString((i)));
                                map.put("title", temp.getString("title"));
                                map.put("special_id", temp.getString("special_id"));
                                map.put("img", temp.getString("img"));
                                map.put("tag", temp.getString("tag"));
                                dataList.add(map);
                            }
                            adapter = new Adapter(getLayoutInflater().getContext(), dataList, R.layout.gridview_layout);
                            gridView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(PP_TIP, "连接失败!!!!!!!!!!!!!!!");
                    }
                });
    }

    class Adapter extends BaseAdapter {
        private List<Map<String, String>> dataList;
        private Context context;
        private int resource;

        /**
         * 有参构造
         *
         * @param context  界面
         * @param dataList 数据
         * @param resoure  列表资源文件
         */
        public Adapter(Context context, List<Map<String, String>> dataList, int resoure) {
            this.context = context;
            this.dataList = dataList;
            this.resource = resoure;
        }

        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int i) {
            return dataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, final ViewGroup viewGroup) {
            final String name;
            // 声明内部类
            Util util = null;
            // 中间变量
            final int flag = i;
            /**
             * 根据listView工作原理，列表项的个数只创建屏幕第一次显示的个数。
             * 之后就不会再创建列表项xml文件的对象，以及xml内部的组件，优化内存，性能效率
             */
            if (view == null) {
                util = new Util();
                // 给xml布局文件创建java对象
                LayoutInflater inflater = LayoutInflater.from(context);
                view = inflater.inflate(resource, null);
                util.img = view.findViewById(R.id.iv_img);
                util.tag = view.findViewById(R.id.tv_tag);
                util.name = view.findViewById(R.id.tv_name);
                // 增加额外变量
                view.setTag(util);
            } else {
                util = (Util) view.getTag();
            }
            // 获取数据显示在各组件
            final Map<String, String> map = dataList.get(i);
            util.name.setText((String) map.get("title"));
            util.tag.setText((String) map.get("tag"));
            name = (String) util.name.getText();
            Glide.with(getLayoutInflater().getContext()).load(map.get("img")).into(util.img);
            LinearGradient mLinearGradient = new LinearGradient(0, 0, 0, util.name.getPaint().getTextSize(), Color.parseColor("#ff676f"), Color.parseColor("#fea05c"), Shader.TileMode.CLAMP);
            LinearGradient mLinearGradient2 = new LinearGradient(0, 0, 0, util.name.getPaint().getTextSize(), Color.parseColor("#ff5080"), Color.parseColor("#ff5d64"), Shader.TileMode.CLAMP);
            LinearGradient mLinearGradient3 = new LinearGradient(0, 0, 0, util.name.getPaint().getTextSize(), Color.parseColor("#6874e3"), Color.parseColor("#c677ea"), Shader.TileMode.CLAMP);
            LinearGradient mLinearGradient4 = new LinearGradient(0, 0, 0, util.name.getPaint().getTextSize(), Color.parseColor("#fda06b"), Color.parseColor("#ff6595"), Shader.TileMode.CLAMP);
            switch (i) {
                case 0:
                    util.name.getPaint().setShader(mLinearGradient);
                    util.tag.getPaint().setShader(mLinearGradient);
                    break;
                case 1:
                    util.name.getPaint().setShader(mLinearGradient2);
                    util.tag.getPaint().setShader(mLinearGradient2);
                    break;
                case 2:
                    util.name.getPaint().setShader(mLinearGradient3);
                    util.tag.getPaint().setShader(mLinearGradient3);
                    break;
                case 3:
                    util.name.getPaint().setShader(mLinearGradient4);
                    util.tag.getPaint().setShader(mLinearGradient4);
                    break;
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), HomeColumnActivity.class);
                    intent.putExtra("title", name);
                    intent.putExtra("special_id",map.get("special_id"));
                    startActivity(intent);
                }
            });
            return view;
        }

        class Util { // 自定义控件集合
            TextView name;
            TextView tag;
            ImageView img;
        }
    }
    /**
     * 第四行适配器
     */

    /**
     * 获取第四行数据
     */
    public void getdata4() {
        OkGo.<String>post(URL_BASE + "?platform=android&request=public.product.store.special.list.get&store_id=1&type_id=4")
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = jsonObject1.getJSONArray("data");
                            dataList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Map<String, String> map = new HashMap<String, String>();
                                temp = new JSONObject(jsonArray.getString((i)));
                                map.put("title", temp.getString("title"));
                                map.put("special_id", temp.getString("special_id"));
                                dataList.add(map);
                            }
                            listAdapter = new ListAdapter(getLayoutInflater().getContext(), dataList, R.layout.listview_layout);
                            listView.setAdapter(listAdapter);
                            listAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(PP_TIP, "连接失败!!!!!!!!!!!!!!!");
                    }
                });
    }
}
