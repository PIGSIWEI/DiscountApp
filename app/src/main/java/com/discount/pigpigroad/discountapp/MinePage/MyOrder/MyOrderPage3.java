package com.discount.pigpigroad.discountapp.MinePage.MyOrder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.discount.pigpigroad.discountapp.MinePage.MyOrder.Adapter.MyOrderPage3Adapter;
import com.discount.pigpigroad.discountapp.MinePage.MyOrder.Helper.OrderDataHelper;
import com.discount.pigpigroad.discountapp.MinePage.MyOrder.OrderBean.AllOrderBean;
import com.discount.pigpigroad.discountapp.MinePage.MyOrder.OrderBean.GoodsOrderInfo;
import com.discount.pigpigroad.discountapp.MinePage.MyOrder.OrderBean.OrderGoodsItem;
import com.discount.pigpigroad.discountapp.MinePage.MyOrder.OrderBean.OrderSummary;
import com.discount.pigpigroad.discountapp.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.PP_TIP;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.URL_BASE;

/**
 * Created by PIGROAD on 2018/4/17.
 * Email:920015363@qq.com
 */

public class MyOrderPage3 extends Fragment {
    private String login_token;
    private GoodsOrderInfo goodsOrderInfo;
    private OrderGoodsItem orderGoodsItem;
    private List<Object> mDatas =new ArrayList<>();
    private MyOrderPage3Adapter adapter;
    private RecyclerView recyclerView;
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.myorder_page3,container,false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    /**
     * 初始化
     */

    private void init() {
        //获取token
        SharedPreferences sp = getActivity().getSharedPreferences("User", MODE_PRIVATE);
        login_token = sp.getString("user_token", null);
        //recyleview初始化
        recyclerView=getActivity().findViewById(R.id.myorderpage3_recyclerview);
        //布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //adapter
        adapter=new MyOrderPage3Adapter(getActivity(),mDatas);
        recyclerView.setAdapter(adapter);
        getData();
        adapter.notifyDataSetChanged();
    }

    /**
     * 获取待付款订单
     */

    private void getData(){
        OkGo.<String>post(URL_BASE + "?request=private.order.my.order.list.get&platform=android&token=" + login_token+"&order_status=4&curpage=1")
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
                                         JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                         JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                                         JSONObject order = jsonObject2.getJSONObject("order");
                                         JSONObject data = jsonObject2.getJSONObject("data");
                                         Iterator order_iterator = order.keys();
                                         Iterator data_iterator = order.keys();
                                         List<OrderSummary> orderSummaries = new ArrayList<>();
                                         AllOrderBean allOrderBean = new AllOrderBean();
                                         //order订单情况
                                         while (order_iterator.hasNext() && data_iterator.hasNext()) {
                                             OrderSummary orderSummary = new OrderSummary();
                                             List<OrderGoodsItem> orderGoodsItemList = new ArrayList<>();
                                             String order_kay = (String) order_iterator.next();
                                             JSONObject order_value = order.getJSONObject(order_kay);
                                             orderSummary.setId(order_value.getInt("id"));
                                             goodsOrderInfo = new GoodsOrderInfo();
                                             goodsOrderInfo.setShopName(order_value.getString("store_name"));
                                             goodsOrderInfo.setOrderCode(order_value.getString("addtime"));
                                             goodsOrderInfo.setStatus(order_value.getString("order_status"));
                                             String data_key = (String) data_iterator.next();
                                             JSONArray data_value = data.getJSONArray(String.valueOf(data_key));
                                             //遍历data
                                             for (int i = 0; i < data_value.length(); i++) {
                                                 JSONObject temp = (JSONObject) data_value.get(i);
                                                 orderGoodsItem = new OrderGoodsItem();
                                                 orderGoodsItem.setProductName(temp.getString("product_name"));
                                                 orderGoodsItem.setProductPic(temp.getString("product_img"));
                                                 orderGoodsItem.setProductPrice(temp.getString("product_price"));
                                                 orderGoodsItem.setPt_order_auto_id(temp.getString("pt_order_auto_id"));
                                                 orderGoodsItem.setOrder(goodsOrderInfo);
                                                 orderGoodsItemList.add(orderGoodsItem);
                                             }
                                             orderSummary.setStatus(order_value.getString("order_status"));
                                             orderSummary.setOrderid(order_value.getString("orderid"));
                                             orderSummary.setOrderDetailList(orderGoodsItemList);
                                             orderSummaries.add(orderSummary);
                                         }
                                         allOrderBean.setResultList(orderSummaries);
                                         mDatas.addAll(OrderDataHelper.getDataAfterHandle(allOrderBean.getResultList()));
                                         adapter.notifyDataSetChanged();
                                         Log.i(PP_TIP, "OJBK");
                                     }
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }
                             }
                         }
                );
    }
}