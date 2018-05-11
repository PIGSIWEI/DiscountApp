package com.discount.pigpigroad.discountapp.MinePage.MyCollection;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.MinePage.Address.AddressActivity;
import com.discount.pigpigroad.discountapp.MinePage.ShoppingCart.ItemTouchListener;
import com.discount.pigpigroad.discountapp.R;
import com.discount.pigpigroad.discountapp.Util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.discount.pigpigroad.discountapp.Api.ApiConnect.URL_BASE;

/**
 * Created by PIGROAD on 2018/4/17.
 * Email:920015363@qq.com
 */

public class MyCollectionActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private MyCollectionAdapter adapter;
    private List<MyCollectionBean> datalist = new ArrayList<>();
    private LinearLayout ll_back, ll_clear;
    private String login_token, product_id = "";
    private ItemTouchListener mItemTouchListener;
    private Dialog dialog;
    private View empty_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycollection_layout);
        init();
    }

    private void init() {
        ll_back = findViewById(R.id.ll_back);
        ll_clear = findViewById(R.id.ll_clear);
        ll_clear.setOnClickListener(this);
        empty_view = findViewById(R.id.empty_view);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //recyclerview初始化
        recyclerView = findViewById(R.id.recycler_view);
        // 设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //适配器的初始化
        mItemTouchListener = new ItemTouchListener() {
            @Override
            public void onItemClick(String str) {

            }

            @Override
            public void onRightMenuClick(String str) {

            }
        };
        adapter = new MyCollectionAdapter(this, datalist, mItemTouchListener);
        recyclerView.setAdapter(adapter);
        //获取token
        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        login_token = sp.getString("user_token", null);
        //获取数据
        getMyCollection();
        adapter.setOnDeleteClickListener(new MyCollectionAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(View view, int position, String cartid) {
                    adapter.notifyDataSetChanged();
                    if (datalist.size()==1){
                        isEmpty();
                    }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_clear:
                for (int i = 0; i < datalist.size(); i++) {
                    if (i == (datalist.size() - 1)) {
                        product_id = product_id + datalist.get(i).getProduct_id();
                    } else {
                        product_id = product_id + datalist.get(i).getProduct_id() + ",";
                    }
                }
                dialog();
                break;
        }
    }

    /**
     * 确认清空弹窗
     */
    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyCollectionActivity.this);
        LayoutInflater inflater = LayoutInflater.from(MyCollectionActivity.this);
        View view = inflater.inflate(R.layout.exit_dialog, null);
        dialog = builder.create();
        dialog.show();
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        TextView tv_edit = view.findViewById(R.id.exit_text);
        tv_edit.setText("确认清空我的收藏？");
        Button btn_cancle = view.findViewById(R.id.btn_cancel);
        Button btn_confirm = view.findViewById(R.id.btn_exit);
        //取消按钮
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTranslate(product_id);
                dialog.dismiss();
            }
        });

    }

    /**
     * 获取我的收藏
     */
    private void getMyCollection() {
        OkGo.<String>post(URL_BASE + "?request=private.collect.goods.collect.get&platform=android&token=" + login_token + "&curpage=1&page_size=24")
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                             @Override
                             public void onSuccess(Response<String> response) {
                                 String responseStr = response.body();//这个就是返回来的结果
                                 try {
                                     JSONObject jsonObject = new JSONObject(responseStr);
                                     int code = jsonObject.getInt("code");
                                     if (code == 0) {
                                         JSONObject data = jsonObject.getJSONObject("data");
                                         JSONArray dataArray = data.getJSONArray("data");
                                         for (int i = 0; i < dataArray.length(); i++) {
                                             JSONObject value = dataArray.getJSONObject(i);
                                             MyCollectionBean bean = new MyCollectionBean();
                                             bean.setAddtime(value.getString("addtime"));
                                             bean.setProduct_img(value.getString("product_img"));
                                             bean.setProduct_name(value.getString("product_name"));
                                             bean.setProduct_price("￥" + value.getString("product_price"));
                                             bean.setProduct_id(value.getString("product_id"));
                                             datalist.add(bean);
                                         }
                                         adapter.notifyDataSetChanged();
                                         if (datalist.isEmpty()) {
                                             isEmpty();
                                         }
                                     }
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }
                             }
                         }
                );
    }

    /**
     * 删除收藏商品
     */
    private void deleteTranslate(String product_id) {
        OkGo.<String>post(URL_BASE + "?request=private.collect.del.collect.goods.action&token=" + login_token + "&platform=android&del_id=" + product_id)
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                             @Override
                             public void onSuccess(Response<String> response) {
                                 String responseStr = response.body();//这个就是返回来的结果
                                 sendLog(responseStr);
                                 try {
                                     JSONObject jsonObject = new JSONObject(responseStr);
                                     int code = jsonObject.getInt("code");
                                     String msg = jsonObject.getString("msg");
                                     if (code == 0) {
                                         ToastUtils.showToast(msg);
                                         datalist.clear();
                                         adapter.notifyDataSetChanged();
                                         isEmpty();
                                     }
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }
                             }
                         }
                );
    }

    /**
     * 数据为空
     */
    public void isEmpty(){
        empty_view.setVisibility(View.VISIBLE);
    }
}
