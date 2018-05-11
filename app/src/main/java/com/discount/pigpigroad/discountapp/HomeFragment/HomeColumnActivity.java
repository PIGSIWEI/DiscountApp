package com.discount.pigpigroad.discountapp.HomeFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.R;
import com.discount.pigpigroad.discountapp.ShopDetailsActivity;
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
 * Created by PIGROAD on 2018/4/21.
 * Email:920015363@qq.com
 */

public class HomeColumnActivity extends BaseActivity {
    private List<Map<String, String>> dataList;
    private LinearLayout ll_back;
    private TextView title;
    private GridView gridView;
    private Adapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_column_layout);
        init();
    }

    private void init() {
        title=findViewById(R.id.title);
        title.setText(getIntent().getStringExtra("title"));
        ll_back=findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        gridView=findViewById(R.id.gridView);
        getdata(getIntent().getStringExtra("special_id"));
    }
    public void getdata(String id){
        OkGo.<String>post(URL_BASE+"?platform=android&request=public.product.store.special.goods.get&special_id="+id)
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
                            dataList=new ArrayList<>();
                            for (int i=0;i<jsonArray.length();i++){
                                Map<String,String> map = new HashMap<String,String>();
                                JSONObject temp = new JSONObject(jsonArray.getString((i)));
                                map.put("product_name",temp.getString("product_name"));
                                map.put("product_sale","已售"+temp.getString("product_sale"));
                                map.put("product_price","￥"+temp.getString("product_price"));
                                map.put("img",temp.getString("product_img"));
                                map.put("other_pt",temp.getString("other_pt"));
                                map.put("product_id",temp.getString("product_id"));
                                dataList.add(map);
                            }
                            adapter=new Adapter(HomeColumnActivity.this,dataList,R.layout.gridview2_layout);
                            gridView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(PP_TIP, "连接失败!!!!!!!!!!!!!!!");
                    }
                });
    }

    //适配器
    class Adapter extends BaseAdapter {
        private List<Map<String, String>> dataList;
        private Context context;
        private int resource;
        /**
         *  有参构造
         * @param context   界面
         * @param dataList     数据
         * @param resoure       列表资源文件
         */
        public Adapter(Context context,List<Map<String, String>> dataList, int resoure){
            this.context=context;
            this.dataList=dataList;
            this.resource=resoure;
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
                util.tv_head=view.findViewById(R.id.tv_head);
                util.tv_price=view.findViewById(R.id.tv_price);
                util.tv_sellnumber=view.findViewById(R.id.tv_sellnumber);
                util.iv_img=view.findViewById(R.id.iv_img);
                util.tm=view.findViewById(R.id.iv_tm);
                util.tb=view.findViewById(R.id.iv_tb);
                util.jd=view.findViewById(R.id.iv_jd);
                // 增加额外变量
                view.setTag(util);
            }else {
                util = (Util) view.getTag();
            }
            // 获取数据显示在各组件
            final Map<String, String> map = dataList.get(i);
            util.tv_head.setText(map.get("product_name"));
            util.tv_price.setText(map.get("product_price"));
            util.tv_sellnumber.setText(map.get("product_sale"));
            Glide.with(view.getContext()).load(map.get("img")).into(util.iv_img);
            String logo=map.get("other_pt");
            switch (logo){
                case "taobao,tianmao":
                    util.jd.setVisibility(View.INVISIBLE);
                    break;
                case "":
                    util.jd.setVisibility(View.INVISIBLE);
                    util.tm.setVisibility(View.INVISIBLE);
                    util.tb.setVisibility(View.INVISIBLE);
                    break;
                case "taobao":
                    util.jd.setVisibility(View.INVISIBLE);
                    util.tm.setVisibility(View.INVISIBLE);
                    break;
                case "tianmao":
                    util.jd.setVisibility(View.INVISIBLE);
                    util.tb.setVisibility(View.INVISIBLE);
                    break;
                case "jingdong":
                    util.tm.setVisibility(View.INVISIBLE);
                    util.tb.setVisibility(View.INVISIBLE);
                    break;
                case "taobao,tianmao,jingdong":
                    break;
                case "taobao,jingdong":
                    util.tm.setVisibility(View.INVISIBLE);
                    break;
                case "tianmao,jingdong":
                    util.tb.setVisibility(View.INVISIBLE);
                    break;
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(view.getContext(),ShopDetailsActivity.class);
                    intent.putExtra("product_id",map.get("product_id"));
                    startActivity(intent);
                }
            });
            return view;
        }
        class Util { // 自定义控件集合
            TextView tv_head;
            TextView tv_price;
            TextView tv_sellnumber;
            ImageView iv_img,tm,tb,jd;
        }
    }
}
