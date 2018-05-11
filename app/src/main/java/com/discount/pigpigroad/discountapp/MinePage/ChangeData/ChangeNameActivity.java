package com.discount.pigpigroad.discountapp.MinePage.ChangeData;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import static com.discount.pigpigroad.discountapp.Api.ApiConnect.PP_TIP;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.URL_BASE;

/**
 * Created by PIGROAD on 2018/4/18.
 * Email:920015363@qq.com
 */

public class ChangeNameActivity extends BaseActivity {
    private EditText et_changename;
    private LinearLayout ll_back;
    private Button btn_changename;
    String login_token;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changename_layout);
        init();
    }

    /**
     * 初始化数据
     */
    private void init() {
        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        login_token=sp.getString("user_token",null);
        ll_back=findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        et_changename=findViewById(R.id.et_changename);
        et_changename.setHint(getIntent().getStringExtra("name"));
        btn_changename=findViewById(R.id.btn_changename);
        btn_changename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_name=et_changename.getText().toString().trim();
                if (TextUtils.isEmpty(text_name)){
                    sendToast("用户名不能为空，请正确输入用户名！");
                }else {
                    changeUserName(text_name);
                }
            }
        });
    }
    /**
     * 修改用户名字
     */
    public void changeUserName(String name){
        OkGo.<String>post(URL_BASE + "?platform=android&token="+login_token+"&request=private.user.update.username.action&username="+name)
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            int code=jsonObject.getInt("code");
                            String msg=jsonObject.getString("msg");
                            if (code==0){
                                sendToast("修改名字成功");
                                sendLog("修改名字成功");
                                finish();
                            }else if (code ==1){
                                sendToast(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        sendLog(String.valueOf(response));
                    }
                });
    }
}
