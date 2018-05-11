package com.discount.pigpigroad.discountapp.MinePage.Address;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.Bean.AddressBean;
import com.discount.pigpigroad.discountapp.MinePage.ShoppingCart.ShoppingCartActivity;
import com.discount.pigpigroad.discountapp.MyApplication;
import com.discount.pigpigroad.discountapp.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.discount.pigpigroad.discountapp.Api.ApiConnect.URL_BASE;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.USER_OVERDUE;

/**
 * Created by PIGROAD on 2018/4/18.
 * Email:920015363@qq.com
 */

public class AddressActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private LinearLayout ll_newadd,ll_back;
    private String login_token;
    private List<AddressBean.DataBeanX.addressBean> datalist=new ArrayList<>();
    private AddressAdapter addressAdapter;
    private Dialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_layout);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        ll_newadd=findViewById(R.id.ll_newadd);
        ll_newadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddressActivity.this,NewAddressActivity.class);
                startActivity(intent);
            }
        });
        recyclerView=findViewById(R.id.recycler_view);
        // 设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //获取token
        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        login_token = sp.getString("user_token", null);
        //适配器的初始化
        addressAdapter=new AddressAdapter(this,datalist);
        recyclerView.setAdapter(addressAdapter);
        //收获地址的单选项
        addressAdapter.setOnItemListener(new AddressAdapter.OnItemListener() {
            @Override
            public void onClick(View v, int pos) {
                dialog(datalist.get(pos).getId(),pos);
            }
        });
        ll_back=findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                                    datalist.add(addressBean);
                                }
                                addressAdapter.notifyDataSetChanged();
                            } else if (code == 999) {
                                MyApplication.ExitClear(AddressActivity.this);
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
        datalist.clear();
        getaddress();
    }
    /**
     * 设为默认地址弹窗
     */
    public void dialog(final String id, final int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);
        LayoutInflater inflater = LayoutInflater.from(AddressActivity.this);
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
        TextView tv_edit=view.findViewById(R.id.exit_text);
        tv_edit.setText("将此地址设为默认地址？");
        Button btn_cancle=view.findViewById(R.id.btn_cancel);
        Button btn_confirm=view.findViewById(R.id.btn_exit);
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
                setaddress(id,pos);
            }
        });

    }
    /**
     * 设为默认地址操作
     */
    public void setaddress(String id, final int pos){
        OkGo.<String>post(URL_BASE + "?request=private.address.set.default.select.action&platform=android&token=" + login_token+"&select_id="+id)
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
                            if (code ==0) {
                                sendToast(msg);
                                dialog.dismiss();
                                addressAdapter.setDefSelect(pos);
                            }else if (code==1){
                                sendToast(msg);
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
