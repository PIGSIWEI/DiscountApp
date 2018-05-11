package com.discount.pigpigroad.discountapp.MinePage.Address;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.MyApplication;
import com.discount.pigpigroad.discountapp.R;
import com.kyleduo.switchbutton.SwitchButton;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ywp.addresspickerlib.AddressPickerView;

import org.json.JSONException;
import org.json.JSONObject;

import static com.discount.pigpigroad.discountapp.Api.ApiConnect.URL_BASE;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.USER_OVERDUE;

/**
 * Created by PIGROAD on 2018/4/18.
 * Email:920015363@qq.com
 */

public class NewAddressActivity extends BaseActivity {
    private Button btn_add;
    private SwitchButton switchButton;
    private LinearLayout ll_user_city, ll_back;
    private TextView tv_user_city;
    private Boolean default_select = false;
    private EditText et_user_name, et_user_phone, et_user_detail;
    private String login_token, provice_name, city_name, area_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_address_layout);
        //获取token
        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        login_token = sp.getString("user_token", null);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        et_user_name = findViewById(R.id.et_user_name);
        et_user_phone = findViewById(R.id.et_user_phone);
        et_user_detail = findViewById(R.id.et_user_detail);
        ll_back = findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ll_user_city = findViewById(R.id.ll_user_city);
        //选择地址
        ll_user_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showcitydialog();
            }
        });
        tv_user_city = findViewById(R.id.tv_user_city);
        switchButton = findViewById(R.id.switchbutton);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                default_select = b;
            }
        });
        btn_add = findViewById(R.id.btn_add);
        //添加收获地址
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String i = "0";
                if (default_select == true) {
                    i = "1";
                } else {
                    i = "0";
                }
                String user_name = et_user_name.getText().toString().trim();
                String user_phone = et_user_phone.getText().toString().trim();
                String user_detail = et_user_detail.getText().toString().trim();
                String user_city = tv_user_city.getText().toString().trim();
                if (!(user_name.isEmpty()) && !(user_phone.isEmpty()) && !(user_detail.isEmpty()) && !(user_city.isEmpty())) {
                    addAddress(user_name,user_phone,user_detail,i,provice_name,city_name,area_name);
                } else {
                    sendToast("请正确输入信息！");
                }
            }
        });
    }

    /**
     * 地址选择器
     */
    private void showcitydialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NewAddressActivity.this);
        LayoutInflater inflater = LayoutInflater.from(NewAddressActivity.this);
        View view = inflater.inflate(R.layout.address_dialog, null);
        final Dialog dialog = builder.create();
        dialog.show();
        dialog.setContentView(view);
        dialog.getWindow().setGravity(Gravity.BOTTOM);//可以设置显示的位置setContentView(view);//自定义布局应该在这里添加，要在dialog.show()的后面
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialog_animation);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = 600;
        window.setAttributes(lp);
        AddressPickerView addressPickerView = view.findViewById(R.id.addressPickerView);
        addressPickerView.setOnAddressPickerSure(new AddressPickerView.OnAddressPickerSureListener() {
            @Override
            public void onSureClick(String address, String provinceCode, String cityCode, String districtCode) {
                tv_user_city.setText(address);
                provice_name=(address.substring(0,(address.indexOf(" "))));
                city_name=(address.substring(address.indexOf(" "),(address.indexOf(" ", address.indexOf(" ")+1))));
                area_name=(address.substring(address.indexOf(" ", address.indexOf(" ")+1)));
                dialog.dismiss();
            }

        });
    }

    /**
     * 添加新的收货地址
     */
    public void addAddress(String user_name, String user_phone, String user_detail, String default_select, String provice_name, String city_name, String area_name) {
        OkGo.<String>post(URL_BASE + "?request=private.address.add.my.address.action&platform=android&token=" + login_token +
                "&shop_name=" + user_name + "&shop_phone=" + user_phone + "&address_details=" + user_detail +
                "&default_select=" + default_select + "&provice_name=" + provice_name + " &city_name=" + city_name +
                "&area_name=" + area_name
        )
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            sendLog(responseStr);
                            int code =jsonObject.getInt("code");
                            String msg=jsonObject.getString("msg");
                            if (code ==0){
                                sendToast(msg);
                                finish();
                            }else if (code==1){
                                sendToast(msg);
                            }else if (code == 999) {
                                MyApplication.ExitClear(NewAddressActivity.this);
                                removeALLActivity();
                                sendToast(USER_OVERDUE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {

                    }
                });
    }
}
