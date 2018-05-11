package com.discount.pigpigroad.discountapp.MinePage.ShoppingCart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.MinePage.PayOrder.PayOrderActivity;
import com.discount.pigpigroad.discountapp.MyApplication;
import com.discount.pigpigroad.discountapp.R;
import com.discount.pigpigroad.discountapp.ShopDetailsActivity;
import com.discount.pigpigroad.discountapp.Util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static com.discount.pigpigroad.discountapp.Api.ApiConnect.PP_TIP;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.URL_BASE;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.USER_OVERDUE;

/**
 * Created by PIGROAD on 2018/4/18.
 * Email:920015363@qq.com
 */

public class ShoppingCartActivity extends BaseActivity {

    private TextView tvShopCartTotalNum;
    private View mEmtryView;
    private LinearLayout tvShopCartSelect;
    private RecyclerView rlvHotProducts;
    private RecyclerView rlvShopCart;
    private ShopCartAdapter mShopCartAdapter;
    private LinearLayout llPay;
    private RelativeLayout rlHaveProduct;
    private List<ShopCartBean.CartlistBean> mAllOrderList = new ArrayList<>();
    private ArrayList<ShopCartBean.CartlistBean> mGoPayList = new ArrayList<>();
    private List<String> mHotProductsList = new ArrayList<>();
    private TextView tvShopCartTotalPrice;
    private int mCount, mPosition;
    private float mTotalPrice1;
    private boolean mSelect;
    private ImageView iv_allselect;
    private Button tv_shopcart_submit, tv_shopcart_delete;
    private LinearLayout ll_back, ll_balance_bar;
    private View empty_layout;
    private String login_token;
    private JSONObject jsoncart;
    private TextView tv_edit, tv_delete;
    public boolean isSelected = true;
    private ItemTouchListener mItemTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppoingcart_layout);
        //获取token
        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        login_token = sp.getString("user_token", null);
        tv_shopcart_submit = findViewById(R.id.tv_shopcart_submit);
        //提交订单操作
        tv_shopcart_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastClick()) {
                    return;
                }
                String selectId="";
                for (int i = 0; i < mAllOrderList.size(); i++) {
                    if (mAllOrderList.get(i).getIsSelect()) {
                        selectId=selectId+mAllOrderList.get(i).getProductId()+",";
                    }
                }
                selectShopCartData(selectId);
                finish();
                Intent intent = new Intent(ShoppingCartActivity.this, PayOrderActivity.class);
                startActivity(intent);
            }
        });
        init();
        setinit();
    }

    /**
     * 初始化
     */
    public void init() {
        tvShopCartSelect = findViewById(R.id.tv_shopcart_addselect);
        tvShopCartTotalPrice = (TextView) findViewById(R.id.tv_shopcart_totalprice);
        tvShopCartTotalNum = (TextView) findViewById(R.id.tv_shopcart_totalnum);
        iv_allselect = findViewById(R.id.iv_allselect);
        rlHaveProduct = (RelativeLayout) findViewById(R.id.rl_shopcart_have);
        rlvShopCart = findViewById(R.id.rlv_shopcart);
        ll_balance_bar = findViewById(R.id.ll_balance_bar);
        ll_back = findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mEmtryView = (View) findViewById(R.id.emtryview);
        mEmtryView.setVisibility(View.GONE);
        llPay = (LinearLayout) findViewById(R.id.ll_pay);
        empty_layout = findViewById(R.id.empty_layout);
        tv_edit = findViewById(R.id.tv_edit);
        tv_delete = findViewById(R.id.tv_delete);
        tv_shopcart_delete = findViewById(R.id.tv_shopcart_delete);
        if (isSelected) {
            tvShopCartTotalPrice.setVisibility(View.VISIBLE);
            tv_shopcart_submit.setVisibility(View.VISIBLE);
            tv_shopcart_delete.setVisibility(View.GONE);
            tv_delete.setVisibility(View.GONE);
            tvShopCartTotalNum.setVisibility(View.VISIBLE);
            tv_edit.setText("编辑");
        } else {
            tvShopCartTotalPrice.setVisibility(View.GONE);
            tv_shopcart_submit.setVisibility(View.GONE);
            tv_shopcart_delete.setVisibility(View.VISIBLE);
            tv_delete.setVisibility(View.VISIBLE);
            tvShopCartTotalNum.setVisibility(View.GONE);
            tv_edit.setText("完成");
        }
        //判断是否为编辑状态
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelected) {
                    isSelected = false;
                    tvShopCartTotalPrice.setVisibility(View.VISIBLE);
                    tv_shopcart_submit.setVisibility(View.VISIBLE);
                    tv_shopcart_delete.setVisibility(View.GONE);
                    tv_delete.setVisibility(View.GONE);
                    tvShopCartTotalNum.setVisibility(View.VISIBLE);
                    tv_edit.setText("编辑");
                } else {
                    isSelected = true;
                    tvShopCartTotalPrice.setVisibility(View.GONE);
                    tv_shopcart_submit.setVisibility(View.GONE);
                    tv_shopcart_delete.setVisibility(View.VISIBLE);
                    tv_delete.setVisibility(View.VISIBLE);
                    tvShopCartTotalNum.setVisibility(View.GONE);
                    tv_edit.setText("完成");
                }
            }
        });
        //清空购物车操作
        tv_shopcart_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < mAllOrderList.size(); i++) {
                    if (mAllOrderList.get(i).getIsSelect()) {
                        deleteShopCartData(mAllOrderList.get(i).getProductId());
                    }
                }
                mAllOrderList.clear();
                getShopCartData();
            }
        });
    }

    /**
     * 适配器操作方法
     */
    public void setinit() {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        llPay.setLayoutParams(lp);
        mItemTouchListener = new ItemTouchListener() {
            @Override
            public void onItemClick(String str) {

            }
            @Override
            public void onRightMenuClick(String str) {

            }
        };
        mShopCartAdapter = new ShopCartAdapter(this, mAllOrderList, mItemTouchListener);
        rlvShopCart.setLayoutManager(new LinearLayoutManager(this));
        delete();
        //修改数量接口
        mShopCartAdapter.setOnEditClickListener(new ShopCartAdapter.OnEditClickListener() {
            public void onEditClick(int position, int cartid, int count) {
                mCount = count;
                mPosition = position;
            }
        });
        //实时监控全选按钮
        mShopCartAdapter.setResfreshListener(new ShopCartAdapter.OnResfreshListener() {
            public void onResfresh(boolean isSelect) {
                mSelect = isSelect;
                if (isSelect) {
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_selected);
                    iv_allselect.setBackground(left);
                } else {
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_unselected);
                    iv_allselect.setBackground(left);
                }
                float mTotalPrice = 0;
                int mTotalNum = 0;
                mTotalPrice1 = 0;
                mGoPayList.clear();
                for (int i = 0; i < mAllOrderList.size(); i++)
                    if (mAllOrderList.get(i).getIsSelect()) {
                        mTotalPrice += Float.parseFloat(mAllOrderList.get(i).getPrice()) * mAllOrderList.get(i).getCount();
                        mTotalNum += mAllOrderList.get(i).getCount();
                        mGoPayList.add(mAllOrderList.get(i));
                    }
                mTotalPrice1 = mTotalPrice;
                tvShopCartTotalPrice.setText("总价：" + mTotalPrice1);
                tvShopCartTotalNum.setText("共" + mTotalNum + "件商品");
                tv_delete.setText("共" + mTotalNum + "件商品");
            }
        });
        //全选
        tvShopCartSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelect = !mSelect;
                if (mSelect) {
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_selected);
                    iv_allselect.setBackground(left);
                    for (int i = 0; i < mAllOrderList.size(); i++) {
                        mAllOrderList.get(i).setSelect(true);
                        mAllOrderList.get(i).setShopSelect(true);
                    }
                } else {
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_unselected);
                    iv_allselect.setBackground(left);
                    for (int i = 0; i < mAllOrderList.size(); i++) {
                        mAllOrderList.get(i).setSelect(false);
                        mAllOrderList.get(i).setShopSelect(false);
                    }
                }
                mShopCartAdapter.notifyDataSetChanged();
            }
        });
        getShopCartData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rlvShopCart.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rlvShopCart.addItemDecoration(dividerItemDecoration);

        rlvShopCart.setAdapter(mShopCartAdapter);
    }

    public void delete() {
        //删除商品接口
        mShopCartAdapter.setOnDeleteClickListener(new ShopCartAdapter.OnDeleteClickListener() {
            public void onDeleteClick(View view, int position, int cartid) {
                mShopCartAdapter.notifyDataSetChanged();
                if (mAllOrderList.size() == 1) {
                    tvShopCartTotalPrice.setText("总价：0");
                    tvShopCartTotalNum.setText("共0件商品");
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_unselected);
                    iv_allselect.setBackground(left);
                    empty_layout.setVisibility(View.VISIBLE);
                    rlvShopCart.setVisibility(View.GONE);
                    ll_balance_bar.setVisibility(View.GONE);
                }
                deleteShopCartData(mAllOrderList.get(position).getProductId());
            }
        });
    }

    /**
     * 判断选中是否第一
     *
     * @param list
     */
    public static void isSelectFirst(List<ShopCartBean.CartlistBean> list) {
        if (list.size() > 0) {
            list.get(0).setIsFirst(1);
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).getShopId() == list.get(i - 1).getShopId()) {
                    list.get(i).setIsFirst(2);
                } else {
                    list.get(i).setIsFirst(1);
                }
            }
        }

    }

    /////////////////////////////////////////////////////购物车数据获取操作

    /**
     * 获取购物车操作
     */
    public void getShopCartData() {
        OkGo.<String>post(URL_BASE + "?platform=android&request=private.cart.goods.cart.get&token=" + login_token)
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
                                JSONObject jsondata = jsonObject.getJSONObject("data");
                                jsoncart = jsondata.getJSONObject("cart");
                                Iterator iterator = jsoncart.keys();
                                while (iterator.hasNext()) {
                                    String key = (String) iterator.next();
                                    JSONObject value = jsoncart.getJSONObject(key);
                                    JSONObject cart_list = value.getJSONObject("cart_list");
                                    Iterator iterator1 = cart_list.keys();
                                    while (iterator1.hasNext()) {
                                        //key1是商品的序列号
                                        String key1 = (String) iterator1.next();
                                        JSONObject value1 = cart_list.getJSONObject(key1);
                                        sendLog(String.valueOf(value1));
                                        ShopCartBean.CartlistBean sb = new ShopCartBean.CartlistBean();
                                        sb.setShopId(value.getInt("store_id"));
                                        sb.setShopName(value.getString("store_name"));
                                        sb.setPrice(value1.getString("product_price"));
                                        sb.setDefaultPic(value1.getString("product_img"));
                                        sb.setProductName(value1.getString("product_name"));
                                        sb.setCount(value1.getInt("product_count"));
                                        sb.setShopPic(value1.getString("store_logo"));
                                        sb.setAttr(value1.getString("attr_name"));
                                        sb.setId(value1.getInt("product_id"));
                                        sb.setProductId(key1);
                                        mAllOrderList.add(sb);
                                    }

                                }
                                isSelectFirst(mAllOrderList);
                                mShopCartAdapter.notifyDataSetChanged();
                            } else if (code == 999) {
                                MyApplication.ExitClear(ShoppingCartActivity.this);
                                removeALLActivity();
                                sendToast(USER_OVERDUE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            empty_layout.setVisibility(View.VISIBLE);
                            rlvShopCart.setVisibility(View.GONE);
                            ll_balance_bar.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    /**
     * 删除购物车操作
     */
    public void deleteShopCartData(String productId) {
        OkGo.<String>post(URL_BASE + "?platform=android&request=private.cart.goods.cart.del.action&token=" + login_token + "&select_id=" + productId)
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

                            } else {
                                sendToast("操作失败！");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    /**
     * 选择购物车操作
     */
    public void selectShopCartData(String productId) {
        OkGo.<String>post(URL_BASE + "?platform=android&request=private.cart.goods.cart.set_select.action&token=" + login_token + "&select_id=" + productId)
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            int code = jsonObject.getInt("code");
                            String msg=jsonObject.getString("msg");
                            if (code == 0) {

                            } else {
                                sendToast("操作失败！");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                );
    }
}
