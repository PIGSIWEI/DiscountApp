package com.discount.pigpigroad.discountapp.Login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.discount.pigpigroad.discountapp.Api.UserLoginApi;
import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.MainActivity;
import com.discount.pigpigroad.discountapp.R;
import com.discount.pigpigroad.discountapp.Slide.SlidingLayout;

/**
 * Created by PIGROAD on 2018/4/11.
 * Email:920015363@qq.com
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_show_pwd;
    private boolean flag, login_flag = false;
    private EditText et_password,et_sms,et_phonenumber,et_username;
    private Button bt_login, bt_register,bt_sms;
    private TextView tv_smslogin, tv_login, tv_register;
    private LinearLayout ll_password, ll_sms, ll_user,ll_other;
    private TranslateAnimation mShowAnim, HiddenAmin;
    private UserLoginApi userLoginApi;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        init();
        if (enableSliding()) {
            SlidingLayout rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }
    }

    /**
     * 初始化数据
     */
    private void init() {
        et_phonenumber=findViewById(R.id.et_phonenumber);
        ll_other=findViewById(R.id.ll_other);
        et_sms=findViewById(R.id.et_sms);
        et_username=findViewById(R.id.et_username);
        bt_sms=findViewById(R.id.bt_sms);
        bt_sms.setOnClickListener(this);
        iv_show_pwd = findViewById(R.id.iv_show_pwd);
        iv_show_pwd.setOnClickListener(this);
        et_password = findViewById(R.id.et_password);
        bt_login = findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);
        bt_register = findViewById(R.id.bt_register);
        bt_register.setOnClickListener(this);
        ll_user = findViewById(R.id.ll_user);
        tv_login = findViewById(R.id.tv_login);
        tv_register = findViewById(R.id.tv_register);
        tv_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        ll_password = findViewById(R.id.ll_password);
        ll_sms = findViewById(R.id.ll_sms);
        tv_smslogin = findViewById(R.id.tv_smslogin);
        tv_smslogin.setOnClickListener(this);
        //控件显示的动画
        mShowAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF
                , -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAnim.setDuration(500);
        //控件隐藏的动画
        HiddenAmin = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF
                , 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        HiddenAmin.setDuration(500);
        //初始化接口
        userLoginApi=new UserLoginApi();
        //设置只输入数字
        et_phonenumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_sms.setInputType(InputType.TYPE_CLASS_NUMBER);
        //intent跳转主页面
        intent=new Intent(this, MainActivity.class);
    }
    protected boolean enableSliding() {
        return false;
    }
    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        String phone=et_phonenumber.getText().toString().trim();
        String password=et_password.getText().toString().trim();
        String sms=et_sms.getText().toString().trim();
        String username=et_username.getText().toString().trim();
        switch (view.getId()) {
            case R.id.iv_show_pwd:
                if (flag == true) {
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    iv_show_pwd.setImageResource(R.drawable.login_password_see2);
                    flag = false;
                } else {
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    iv_show_pwd.setImageResource(R.drawable.login_password_see);
                    flag = true;
                }
                break;
            case R.id.tv_smslogin:
                if (login_flag == true) {
                    tv_login.setTextColor(Color.parseColor("#ffffff"));
                    tv_register.setTextColor(Color.parseColor("#333333"));
                    ll_password.startAnimation(mShowAnim);
                    ll_password.setVisibility(View.VISIBLE);
                    ll_sms.startAnimation(HiddenAmin);
                    ll_sms.setVisibility(View.GONE);
                    ll_user.setVisibility(View.GONE);
                    tv_smslogin.setText("验证码登录");
                    bt_register.setVisibility(View.GONE);
                    bt_login.setVisibility(View.VISIBLE);
                    ll_other.setVisibility(View.VISIBLE);
                    login_flag = false;
                } else {
                    tv_login.setTextColor(Color.parseColor("#ffffff"));
                    tv_register.setTextColor(Color.parseColor("#333333"));
                    ll_sms.startAnimation(mShowAnim);
                    ll_sms.setVisibility(View.VISIBLE);
                    ll_user.setVisibility(View.GONE);
                    ll_password.startAnimation(HiddenAmin);
                    ll_password.setVisibility(View.GONE);
                    tv_smslogin.setText("密码登录");
                    bt_register.setVisibility(View.GONE);
                    bt_login.setVisibility(View.VISIBLE);
                    login_flag = true;
                    ll_other.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_register:
                tv_register.setTextColor(Color.parseColor("#ffffff"));
                tv_login.setTextColor(Color.parseColor("#333333"));
                ll_sms.startAnimation(mShowAnim);
                ll_sms.setVisibility(View.VISIBLE);
                ll_user.startAnimation(mShowAnim);
                ll_password.startAnimation(mShowAnim);
                ll_password.setVisibility(View.VISIBLE);
                ll_user.setVisibility(View.VISIBLE);
                bt_login.setVisibility(View.GONE);
                bt_register.setVisibility(View.VISIBLE);
                ll_other.setVisibility(View.GONE);
                break;
            case R.id.tv_login:
                tv_login.setTextColor(Color.parseColor("#ffffff"));
                tv_register.setTextColor(Color.parseColor("#333333"));
                ll_sms.setVisibility(View.GONE);
                ll_user.setVisibility(View.GONE);
                bt_register.setVisibility(View.GONE);
                bt_login.setVisibility(View.VISIBLE);
                ll_other.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_login:
                //短信登录
                if ((ll_password.getVisibility()==View.GONE)){
                    //判断手机号和验证码是否为空
                        if (TextUtils.isEmpty(phone)||TextUtils.isEmpty(sms)){
                            Toast.makeText(this, "手机号或短信验证码不能为空，请重新输入！", Toast.LENGTH_SHORT).show();
                        }else{
                            userLoginApi.SmsLogin(phone,sms,this);
                        }
                }
                //密码登录
                else {
                    //判断手机号和验证码是否为空
                    if (TextUtils.isEmpty(phone)||TextUtils.isEmpty(password)){
                        Toast.makeText(this, "手机号或密码不能为空，请重新输入！", Toast.LENGTH_SHORT).show();
                    }else{
                        userLoginApi.PasswordLogin(phone,password,this);
                    }
                }
                break;
            //获取短信验证码
            case R.id.bt_sms:
                //登录获取短信验证码
                if ((ll_password.getVisibility()==View.GONE)){
                    if (isMobileNO(phone)){
                       Boolean tag=userLoginApi.Getsms(phone,this);
                        if (tag ==true){
                            final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);
                            myCountDownTimer.start();
                        }
                    }else{
                        Toast.makeText(this, "请正确输入手机号！", Toast.LENGTH_SHORT).show();
                    }
                }
                //注册获取短信验证码
                else {
                    if (isMobileNO(phone)){
                        Boolean tag=userLoginApi.RegisterGetsms(phone,this);
                        if (tag ==true){
                            final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);
                            myCountDownTimer.start();
                        }
                    }else{
                        Toast.makeText(this, "请正确输入手机号！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.bt_register:
                if (!(isMobileNO(phone))){
                    Toast.makeText(this, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(password)){
                    Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(sms)){
                    Toast.makeText(this, "验证码不能为空！", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(username)){
                    Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                }else {
                    userLoginApi.Register(phone,password,sms,username,this);
                }
                break;
        }
    }

    /**
     * 判断输入的是否为手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {

        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else return mobiles.matches(telRegex);
    }
    /**
     *  验证码发送倒计时
     */
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            bt_sms.setClickable(false);
            bt_sms.setText(l / 1000 + "s");
        }

        @Override
        public void onFinish() {
            //重新给Button设置文字
            bt_sms.setText("重新获取");
            //设置可点击
            bt_sms.setClickable(true);
        }
    }
}
