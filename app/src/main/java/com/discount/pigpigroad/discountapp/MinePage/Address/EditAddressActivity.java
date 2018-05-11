package com.discount.pigpigroad.discountapp.MinePage.Address;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by PIGROAD on 2018/4/19.
 * Email:920015363@qq.com
 */

public class EditAddressActivity extends BaseActivity {
    private LinearLayout ll_back,ll_choose;
    private EditText et_user_name,et_user_phone,et_user_detail;
    private TextView tv_user_location;
    private SwitchButton switchButton;
    private Button btn_save,btn_delete;
    String user_name,user_phone,user_detail,user_location,select,login_token,provice_name,city_name,area_name,id;
    public Boolean default_select=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editaddress_layout);
        //获取token
        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        login_token = sp.getString("user_token", null);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        ll_back=findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        et_user_name=findViewById(R.id.et_user_name);
        et_user_phone=findViewById(R.id.et_user_phone);
        et_user_detail=findViewById(R.id.et_user_detail);
        tv_user_location=findViewById(R.id.tv_user_location);
        switchButton=findViewById(R.id.switchbutton);
        ll_choose=findViewById(R.id.ll_choose);
        id=getIntent().getStringExtra("id");
        user_name=getIntent().getStringExtra("user_name");
        user_phone=getIntent().getStringExtra("user_phone");
        user_detail=getIntent().getStringExtra("user_details");
        user_location=getIntent().getStringExtra("user_location");
        select=getIntent().getStringExtra("select");
        provice_name=getIntent().getStringExtra("provice_name");
        city_name=getIntent().getStringExtra("city_name");
        area_name=getIntent().getStringExtra("area_name");
        et_user_name.setText(user_name);
        et_user_phone.setText(user_phone);
        et_user_detail.setText(user_detail);
        tv_user_location.setText(user_location);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                default_select = b;
            }
        });
        if (select.equals("1")){
            switchButton.setChecked(true);
        }else if (select.equals("0")){
            switchButton.setChecked(false);
        }
        btn_save=findViewById(R.id.btn_save);
        btn_delete=findViewById(R.id.btn_delete);
        //保存按钮操作
        btn_save.setOnClickListener(new View.OnClickListener() {
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
                String user_city = tv_user_location.getText().toString().trim();
                if (!(user_name.isEmpty()) && !(user_phone.isEmpty()) && !(user_detail.isEmpty()) && !(user_city.isEmpty())) {
                        editaddress(user_name,user_phone,user_detail,i,provice_name,city_name,area_name,id);
                }else {
                    sendToast("请正确输入信息！");
                }
            }
        });
        //删除按钮操作
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    deleteaddress(id);
            }
        });
        //选择地址
        ll_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showcitydialog();
            }
        });
    }
    /**
     * 地址选择器
     */
    private void showcitydialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditAddressActivity.this);
        LayoutInflater inflater = LayoutInflater.from(EditAddressActivity.this);
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
                tv_user_location.setText(address);
                provice_name=(address.substring(0,(address.indexOf(" "))));
                city_name=(address.substring(address.indexOf(" "),(address.indexOf(" ", address.indexOf(" ")+1))));
                area_name=(address.substring(address.indexOf(" ", address.indexOf(" ")+1)));
                dialog.dismiss();
            }

        });
    }
    /**
     * 编辑收获地址操作
     */
    public void editaddress(String shop_name,String shop_phone,String address_details,String default_select,String provice_name,String city_name,String area_name,String id){
        OkGo.<String>post(URL_BASE + "?request=private.address.edit.my.address.action&platform=android&token="+login_token+"&shop_name="+
                               shop_name+"&shop_phone="+shop_phone+"&address_details="+address_details+"&default_select="+
                                default_select+"&provice_name="+provice_name+"&city_name="+city_name+"&area_name="+area_name+"&id="+id
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
                                MyApplication.ExitClear(EditAddressActivity.this);
                                removeALLActivity();
                                sendToast(USER_OVERDUE);
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
     * 删除收获地址操作
     */
    public void deleteaddress(String id){
        OkGo.<String>post(URL_BASE + "?request=private.address.del.my.address.action&platform=android&token=" + login_token
                +"&default_select=1&id="+id
        )
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            int code =jsonObject.getInt("code");
                            String msg=jsonObject.getString("msg");
                            if (code ==0){
                                sendToast(msg);
                                finish();
                            }else if (code==1){
                                sendToast(msg);
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
}
