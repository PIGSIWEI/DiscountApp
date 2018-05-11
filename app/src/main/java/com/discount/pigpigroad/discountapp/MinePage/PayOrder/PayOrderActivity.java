package com.discount.pigpigroad.discountapp.MinePage.PayOrder;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.Bean.AddressBean;
import com.discount.pigpigroad.discountapp.MinePage.Address.AddressActivity;
import com.discount.pigpigroad.discountapp.MinePage.MyOrder.Helper.OrderDataHelper;
import com.discount.pigpigroad.discountapp.MinePage.MyOrder.OrderBean.AllOrderBean;
import com.discount.pigpigroad.discountapp.MinePage.MyOrder.OrderBean.GoodsOrderInfo;
import com.discount.pigpigroad.discountapp.MinePage.MyOrder.OrderBean.OrderGoodsItem;
import com.discount.pigpigroad.discountapp.MinePage.MyOrder.OrderBean.OrderSummary;
import com.discount.pigpigroad.discountapp.MinePage.PayOrder.Adapter.PayOrderAdapter;
import com.discount.pigpigroad.discountapp.MyApplication;
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

import static com.discount.pigpigroad.discountapp.Api.ApiConnect.URL_BASE;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.USER_OVERDUE;

/**
 * Created by PIGROAD on 2018/4/19.
 * Email:920015363@qq.com
 */

public class PayOrderActivity extends BaseActivity {
    private Button settlement;
    private RecyclerView recyclerView;
    private String login_token;
    private List<AddressBean.DataBeanX.addressBean> datalist2=new ArrayList<>();
    private PayOrderAdapter adapter;
    private LinearLayout ll_address,ll_back;
    private TextView tv_user,tv_useraddress,tv_all_price;
    private int all_price=0;
    private Intent intent;
    private GoodsOrderInfo goodsOrderInfo;
    private OrderGoodsItem orderGoodsItem;
    private List<Object> mDatas = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payorder_layout);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //获取token
        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        login_token = sp.getString("user_token", null);
        //获取数据
        intent=new Intent(this,SettlementActivity.class);
        getOrderDetail();
        tv_user=findViewById(R.id.tv_user);
        tv_useraddress=findViewById(R.id.tv_useraddress);
        getaddress();
        settlement = findViewById(R.id.settlement);
        settlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastClick()) {
                    return;
                }
                startActivity(intent);
                finish();
            }
        });
        recyclerView = findViewById(R.id.recycler_view);
        // 设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //适配器的初始化
        adapter = new PayOrderAdapter(this, mDatas);
        recyclerView.setAdapter(adapter);
        //收获地址的选择
        ll_address=findViewById(R.id.ll_address);
        ll_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent=new Intent(PayOrderActivity.this,AddressActivity.class);
                    startActivity(intent);
            }
        });
        //设置总价格
        tv_all_price=findViewById(R.id.tv_all_price);
        ll_back=findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 获取订单详情
     */
    public void getOrderDetail() {
        OkGo.<String>post(URL_BASE + "?request=private.checkout.cart.select.detail.get&platform=android&token=" + login_token)
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
                                JSONObject cart = data.getJSONObject("cart");
                                Iterator iterator = cart.keys();
                                List<OrderSummary> orderSummaries = new ArrayList<>();
                                AllOrderBean allOrderBean = new AllOrderBean();
                                while (iterator.hasNext()) {
                                    String key = (String) iterator.next();
                                    JSONObject value = cart.getJSONObject(key);
                                    JSONObject cart_list = value.getJSONObject("cart_list");
                                    Iterator iterator1 = cart_list.keys();
                                    OrderSummary orderSummary = new OrderSummary();
                                    List<OrderGoodsItem> orderGoodsItemList = new ArrayList<>();
                                    orderSummary.setOrderid(value.getString("store_id"));
                                    goodsOrderInfo = new GoodsOrderInfo();
                                    goodsOrderInfo.setShopName(value.getString("store_name"));
                                    goodsOrderInfo.setShopPic(value.getString("store_logo"));
                                    while (iterator1.hasNext()) {
                                        //key1是商品的序列号
                                        String key1 = (String) iterator1.next();
                                        JSONObject value1 = cart_list.getJSONObject(key1);
                                        orderGoodsItem = new OrderGoodsItem();
                                        orderGoodsItem.setProductName(value1.getString("product_name"));
                                        orderGoodsItem.setProductPrice(value1.getString("product_price"));
                                        orderGoodsItem.setAttr_name(value1.getString("attr_name"));
                                        orderGoodsItem.setProductPic(value1.getString("product_img"));
                                        orderGoodsItem.setCount(value1.getInt("product_count"));
                                        orderGoodsItem.setOrder(goodsOrderInfo);
                                        orderGoodsItemList.add(orderGoodsItem);
                                        all_price=all_price+value1.getInt("product_price");
                                    }
                                    orderSummary.setOrderDetailList(orderGoodsItemList);
                                    orderSummaries.add(orderSummary);
                                    tv_all_price.setText("￥"+all_price);
                                }
                                allOrderBean.setResultList(orderSummaries);
                                mDatas.addAll(OrderDataHelper.getDataAfterHandle(allOrderBean.getResultList()));
                                adapter.notifyDataSetChanged();
                            } else if (code == 999) {
                                MyApplication.ExitClear(PayOrderActivity.this);
                                removeALLActivity();
                                sendToast(USER_OVERDUE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    /**
     * 获取收货地址
     */
    public void getaddress(){
        OkGo.<String>post(URL_BASE + "?request=private.address.all.my.address.get&platform=android&token=" + login_token)
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
                                JSONObject jsonObject1=jsonObject.getJSONObject("data");
                                JSONArray jsonArray=jsonObject1.getJSONArray("data");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject temp=new JSONObject(jsonArray.getString(i));
                                    AddressBean.DataBeanX.addressBean addressBean=new AddressBean.DataBeanX.addressBean();
                                    addressBean.setShop_name(temp.getString("shop_name"));
                                    addressBean.setAddress_location(temp.getString("address_location"));
                                    addressBean.setShop_phone(temp.getString("shop_phone"));
                                    addressBean.setAddress_details(temp.getString("address_details"));
                                    addressBean.setDefault_select(temp.getString("default_select"));
                                    addressBean.setId(temp.getString("id"));
                                    addressBean.setProvice_name(temp.getString("provice_name"));
                                    addressBean.setCity_name(temp.getString("city_name"));
                                    addressBean.setArea_name(temp.getString("area_name"));
                                    if ((temp.getString("default_select")).equals("1")){
                                        tv_user.setText(temp.getString("shop_name")+" "+temp.getString("shop_phone"));
                                        tv_useraddress.setText(temp.getString("address_location")+" " +temp.getString("address_details"));
                                    }
                                    datalist2.add(addressBean);
                                }
                            } else if (code == 999) {
                                MyApplication.ExitClear(PayOrderActivity.this);
                                removeALLActivity();
                                sendToast(USER_OVERDUE);
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getaddress();
    }
}
