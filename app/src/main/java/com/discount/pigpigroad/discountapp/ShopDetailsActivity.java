package com.discount.pigpigroad.discountapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.test.TouchUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.discount.pigpigroad.discountapp.Adapter.FlowLayout;
import com.discount.pigpigroad.discountapp.Adapter.TagAdapter;
import com.discount.pigpigroad.discountapp.Adapter.TagFlowLayout;
import com.discount.pigpigroad.discountapp.Flow.Flowlayout;
import com.discount.pigpigroad.discountapp.Flow.TagItem;
import com.discount.pigpigroad.discountapp.MinePage.Setting.SettingActivity;
import com.discount.pigpigroad.discountapp.Slide.SlidingLayout;
import com.discount.pigpigroad.discountapp.Util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.timmy.tdialog.TDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.discount.pigpigroad.discountapp.Api.ApiConnect.PP_TIP;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.URL_BASE;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.USER_OVERDUE;


/**
 * Created by XYSM on 2018/3/21.
 */

public class ShopDetailsActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_add_shopcar, btn_sure;
    private TextView discount_tv, tv_shop_name, tv_shop_price, tv_shop_sell, tv_shop_reasons, tv_choose, tv_translate;
    private LinearLayout ll_back;
    private ImageView iv_shop_img, iv_translate;
    private Dialog dialog;
    private JSONObject shopdetail_object;
    int product_number = 1;
    private TextView tv_number;
    private List<Map<String, String>> dataList, dataList2;
    TagFlowLayout mTagLayout, mTagLayout2;
    private ArrayList<String> mVals, mVals2, text_choose, color_id, size_id, id_choose;
    String login_token, product_id;
    private Boolean isSelect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_details_layout);
        initData();
        if (enableSliding()) {
            SlidingLayout rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }
    }

    protected boolean enableSliding() {
        return true;
    }

    /**
     * 初始化
     */
    private void initData() {
        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        login_token = sp.getString("user_token", null);
        btn_add_shopcar = findViewById(R.id.btn_add_shopcar);
        btn_add_shopcar.setOnClickListener(this);
        ll_back = findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        iv_shop_img = findViewById(R.id.iv_shop_img);
        tv_shop_name = findViewById(R.id.tv_shop_name);
        tv_shop_price = findViewById(R.id.tv_shop_price);
        tv_shop_sell = findViewById(R.id.tv_shop_sell);
        tv_shop_reasons = findViewById(R.id.tv_shop_reasons);
        tv_translate = findViewById(R.id.tv_translate);
        iv_translate = findViewById(R.id.iv_translate);
        tv_translate.setOnClickListener(this);
        iv_translate.setOnClickListener(this);
        product_id = getIntent().getStringExtra("product_id");
        getSelect();
        getData(product_id);
        dataList = new ArrayList<>();
        dataList2 = new ArrayList<>();
        text_choose = new ArrayList<>();
        text_choose.add("已选：");
        mVals = new ArrayList<>();
        mVals2 = new ArrayList<>();
        color_id = new ArrayList<>();
        size_id = new ArrayList<>();
        id_choose = new ArrayList<>();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //加入购物车操作
            case R.id.btn_add_shopcar:
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                view = inflater.inflate(R.layout.add_shop_dialog, null);
                ImageView iv_exit = view.findViewById(R.id.iv_exit);
                ImageView iv_minus = view.findViewById(R.id.iv_minus);
                TextView tv_tag1 = view.findViewById(R.id.tv_tag1);
                TextView tv_tag2 = view.findViewById(R.id.tv_tag2);
                ImageView iv_add = view.findViewById(R.id.iv_add);
                btn_sure = view.findViewById(R.id.btn_sure);
                btn_sure.setOnClickListener(this);
                tv_number = view.findViewById(R.id.tv_product_number);
                tv_choose = view.findViewById(R.id.tv_choose);
                LinearLayout ll_attribute = view.findViewById(R.id.ll_attribute);
                //属性面板
                mTagLayout = view.findViewById(R.id.tagflowlayout);
                mTagLayout2 = view.findViewById(R.id.tagflowlayout2);
                final LayoutInflater mInflater = LayoutInflater.from(view.getContext());
                final LayoutInflater mInflater2 = LayoutInflater.from(view.getContext());
                //判断数据是否为空
                if (dataList.isEmpty()) {
                    tv_tag1.setVisibility(View.GONE);
                    mTagLayout.setVisibility(View.GONE);
                } else {
                    tv_tag1.setText(dataList.get(0).get("attr_type_name"));
                    mTagLayout.setAdapter(new TagAdapter<String>(mVals) {
                        @Override
                        public View getView(FlowLayout parent, int position, final String s) {
                            TextView tv = (TextView) mInflater.inflate(R.layout.textview_tagflowlayout,
                                    mTagLayout, false);
                            tv.setText(s);
                            return tv;
                        }

                        @Override
                        public void onSelected(int position, View view) {
                            super.onSelected(position, view);
                            text_choose.add(mVals.get(position));
                            tv_choose.setText(String.valueOf(text_choose));
                            id_choose.add(color_id.get(position));
                        }

                        @Override
                        public void unSelected(int position, View view) {
                            super.unSelected(position, view);
                            text_choose.remove(mVals.get(position));
                            id_choose.remove(color_id.get(position));
                            tv_choose.setText(String.valueOf(text_choose));
                            if (text_choose.size() == 1) {
                                tv_choose.setText("请选择属性");
                            }
                        }
                    });
                }
                if (dataList2.isEmpty()) {
                    tv_tag2.setVisibility(View.GONE);
                    mTagLayout2.setVisibility(View.GONE);
                } else {
                    tv_tag2.setText(dataList2.get(0).get("attr_type_name"));
                    mTagLayout2.setAdapter(new TagAdapter<String>(mVals2) {
                        @Override
                        public View getView(FlowLayout parent, int position, final String s) {
                            TextView tv = (TextView) mInflater2.inflate(R.layout.textview_tagflowlayout,
                                    mTagLayout2, false);
                            tv.setText(s);
                            return tv;
                        }

                        @Override
                        public void onSelected(int position, View view) {
                            super.onSelected(position, view);
                            text_choose.add(mVals2.get(position));
                            tv_choose.setText(String.valueOf(text_choose));
                            id_choose.add(size_id.get(position));
                        }

                        @Override
                        public void unSelected(int position, View view) {
                            super.unSelected(position, view);
                            id_choose.remove(size_id.get(position));
                            text_choose.remove(mVals2.get(position));
                            tv_choose.setText(String.valueOf(text_choose));
                            if (text_choose.size() == 1) {
                                tv_choose.setText("请选择属性");
                            }
                        }
                    });
                }
                tv_number.setText(product_number + "");
                iv_add.setOnClickListener(this);
                iv_minus.setOnClickListener(this);
                TextView tv_dialog_shop_price = view.findViewById(R.id.tv_dialog_shop_price);
                TextView tv_product_stock = view.findViewById(R.id.tv_product_stock);
                //库存
                try {
                    tv_product_stock.setText("库存" + shopdetail_object.getString("product_stock"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tv_dialog_shop_price.setText(tv_shop_price.getText());
                iv_exit.setOnClickListener(this);
                dialog = builder.create();
                dialog.show();
                dialog.setContentView(view);
                dialog.getWindow().setGravity(Gravity.BOTTOM);//可以设置显示的位置setContentView(view);//自定义布局应该在这里添加，要在dialog.show()的后面
                Window window = dialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                window.setWindowAnimations(R.style.dialog_animation);
                window.getDecorView().setPadding(0, 0, 0, 0);
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(lp);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        text_choose.clear();
                        text_choose.add("已选");
                        id_choose.clear();
                    }
                });
                break;
            //返回操作
            case R.id.ll_back:
                finish();
                break;
            //购物车中X的操作
            case R.id.iv_exit:
                dialog.dismiss();
                break;
            //购物车中加的操作
            case R.id.iv_add:
                product_number = product_number + 1;
                tv_number.setText(product_number + "");
                break;
            //购物车中减的操作
            case R.id.iv_minus:
                if (product_number == 1) {

                } else {
                    product_number = product_number - 1;
                }
                tv_number.setText(product_number + "");
                break;
            //购物车确认操作
            case R.id.btn_sure:
                if (dataList.isEmpty() && dataList2.isEmpty()) {
                    //没颜色没尺寸
                    addshopcar("");
                } else if (dataList2.isEmpty() && !dataList.isEmpty()) {
                    //有颜色没尺寸
                    if (text_choose.size() < 2) {
                        sendToast("请正确选择商品属性");
                    } else {
                        String attr_id = id_choose.get(0);
                        addshopcar(attr_id);
                    }
                } else if (!dataList2.isEmpty() && dataList.isEmpty()) {
                    //有尺寸没颜色
                    if (text_choose.size() < 2) {
                        sendToast("请正确选择商品属性");
                    } else {
                        String attr_id = id_choose.get(0);
                        addshopcar(attr_id);
                    }
                } else {
                    //有颜色有尺寸
                    if (text_choose.size() < 3) {
                        sendToast("请正确选择商品属性");
                    } else {
                        String attr_id = id_choose.get(0) + "," + id_choose.get(1);
                        addshopcar(attr_id);
                    }
                }
                break;
            //收藏按钮
            case R.id.tv_translate:
                if (isSelect == false) {
                    translate();
                } else {
                    deleteTranslate();
                }
                break;
            //收藏按钮
            case R.id.iv_translate:
                if (isSelect == false) {
                    translate();
                } else {
                    deleteTranslate();
                }
                break;
        }
    }

    /**
     * 获取商品详情
     */
    public void getData(String id) {
        OkGo.<String>post(URL_BASE + "?request=public.product.store.goods.detail.get&platform=android&product_id=" + id)
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            sendLog(String.valueOf(jsonObject));
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            shopdetail_object = jsonObject1.getJSONObject("data");
                            Glide.with(ShopDetailsActivity.this).load(shopdetail_object.getString("product_source_img")).into(iv_shop_img);
                            tv_shop_name.setText(shopdetail_object.getString("product_name"));
                            tv_shop_price.setText("￥" + shopdetail_object.getString("product_price"));
                            tv_shop_sell.setText("销售量:" + shopdetail_object.getString("product_sale"));
                            tv_shop_reasons.setText(shopdetail_object.getString("product_content"));
                            Object attr = jsonObject1.get("attr");
                            if (attr instanceof JSONObject) {
                                JSONObject attrObject = jsonObject1.getJSONObject("attr");
                                JSONObject data = attrObject.getJSONObject("data");
                                Iterator data_iterator = data.keys();
                                while (data_iterator.hasNext()) {
                                    String data_kay = (String) data_iterator.next();
                                    if (data_kay.equals("1")) {
                                        JSONArray jsonArray1 = data.getJSONArray("1");
                                        for (int i = 0; i < jsonArray1.length(); i++) {
                                            Map<String, String> map = new HashMap<String, String>();
                                            JSONObject temp = new JSONObject(jsonArray1.getString((i)));
                                            map.put("attr_temp_name", temp.getString("attr_temp_name"));
                                            map.put("attr_type_name", temp.getString("attr_type_name"));
                                            color_id.add(temp.getString("id"));
                                            dataList.add(map);
                                            mVals.add(temp.getString("attr_temp_name"));
                                        }
                                    } else if (data_kay.equals("7")) {
                                        JSONArray jsonArray7 = data.getJSONArray("7");
                                        for (int i = 0; i < jsonArray7.length(); i++) {
                                            Map<String, String> map = new HashMap<String, String>();
                                            JSONObject temp = new JSONObject(jsonArray7.getString((i)));
                                            map.put("attr_temp_name", temp.getString("attr_temp_name"));
                                            map.put("attr_type_name", temp.getString("attr_type_name"));
                                            size_id.add(temp.getString("id"));
                                            dataList2.add(map);
                                            mVals2.add(temp.getString("attr_temp_name"));
                                        }
                                    }
                                }
                            } else if (attr instanceof JSONArray) {
                                JSONArray attrArray = jsonObject1.getJSONArray("attr");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    public void onError(Response<String> response) {
                        super.onError(response);
                        sendLog(String.valueOf(response));
                    }
                });
    }

    /**
     * 添加到购物车请求
     */
    public void addshopcar(String attr_id) {
        OkGo.<String>post(URL_BASE + "?product_id=" + product_id + "&request=private.cart.goods.add.cart.action&platform=android&token=" + login_token + "&product_count=" + product_number + "&attr_id=" + attr_id + "&cart_express=0")
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            sendLog("加入购物车信息" + String.valueOf(jsonObject));
                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            if (code == 0) {
                                sendToast("加入购物车成功");
                                dialog.dismiss();
                            } else if (code == 999) {
                                MyApplication.ExitClear(ShopDetailsActivity.this);
                                removeALLActivity();
                                sendToast(USER_OVERDUE);
                            } else if (code == 1) {
                                sendToast(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(PP_TIP, "连接失败!!!!!!!!!!!!!!!");
                    }
                });
    }

    /**
     * 添加收藏操作
     */
    public void translate() {
        OkGo.<String>post(URL_BASE + "?request=private.collect.goods.add.collect.action&token=" + login_token + "&platform=android&product_id=" + product_id)
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {

                             @Override
                             public void onSuccess(Response<String> response) {
                                 String responseStr = response.body();//这个就是返回来的结果
                                 try {
                                     JSONObject jsonObject = new JSONObject(responseStr);
                                     int code = jsonObject.getInt("code");
                                     String msg = jsonObject.getString("msg");
                                     if (code == 0) {
                                         ToastUtils.showToast(msg);
                                         tv_translate.setText("已收藏");
                                         iv_translate.setBackgroundResource(R.drawable.mine_translate);
                                         isSelect = true;
                                     } else {
                                         ToastUtils.showToast(msg);
                                     }
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }
                             }

                             @Override
                             public void onError(Response<String> response) {
                                 super.onError(response);
                                 Log.i(PP_TIP, "连接失败!!!!!!!!!!!!!!!");
                             }
                         }
                );
    }

    /**
     * 判断收藏操作
     */
    public void getSelect() {
        OkGo.<String>post(URL_BASE + "?request=private.collect.goods.is.collect.get&token=" + login_token + "&platform=android&product_id=" + product_id)
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                             @Override
                             public void onSuccess(Response<String> response) {
                                 String responseStr = response.body();//这个就是返回来的结果
                                 try {
                                     sendLog(responseStr);
                                     JSONObject jsonObject = new JSONObject(responseStr);
                                     String collect = jsonObject.getString("collect");
                                     //collect 为0是没收藏，1是收藏
                                     if (collect.equals("0")) {
                                         isSelect = false;
                                     } else if (collect.equals("1")) {
                                         isSelect = true;
                                     }
                                     if (isSelect == false) {
                                         tv_translate.setText("收藏");
                                         iv_translate.setBackgroundResource(R.drawable.mine_logo2);
                                     } else {
                                         tv_translate.setText("已收藏");
                                         iv_translate.setBackgroundResource(R.drawable.mine_translate);
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
    public void deleteTranslate() {
        OkGo.<String>post(URL_BASE + "?request=private.collect.del.collect.goods.action&token=" + login_token + "&platform=android&del_id=" + product_id)
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                             @Override
                             public void onSuccess(Response<String> response) {
                                 String responseStr = response.body();//这个就是返回来的结果
                                 try {
                                     JSONObject jsonObject = new JSONObject(responseStr);
                                     int code = jsonObject.getInt("code");
                                     String msg = jsonObject.getString("msg");
                                     if (code == 0) {
                                         ToastUtils.showToast(msg);
                                         tv_translate.setText("收藏");
                                         iv_translate.setBackgroundResource(R.drawable.mine_logo2);
                                         isSelect = false;
                                     }
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }
                             }
                         }
                );
    }
}
