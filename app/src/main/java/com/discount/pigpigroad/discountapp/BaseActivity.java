package com.discount.pigpigroad.discountapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.discount.pigpigroad.discountapp.Slide.SlidingLayout;

import static com.discount.pigpigroad.discountapp.Api.ApiConnect.PP_TIP;

/**
 * Created by PIGROAD on 2018/4/12.
 * Email:920015363@qq.com
 */

public class BaseActivity extends AppCompatActivity{
    private static long lastClickTime;
    private LinearLayout ll_back;
    private MyApplication myApplication;
    private BaseActivity oContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (enableSliding()) {
            SlidingLayout rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }
        if (myApplication == null) {
            // 得到Application对象
            myApplication = (MyApplication) getApplication();
        }
        oContext = this;// 把当前的上下文对象赋值给BaseActivity
        addActivity();// 调用添加方法
    }

    // 添加Activity方法
    public void addActivity() {
        myApplication.addActivity_(oContext);// 调用myApplication的添加Activity方法
    }
    //销毁所有Activity方法
    public void removeALLActivity() {
        myApplication.removeALLActivity_();// 调用myApplication的销毁所有Activity方法
    }
    protected boolean enableSliding() {
        return true;
    }
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
    //自定义Toast
    public void sendToast(String text){
        Toast.makeText(myApplication, text, Toast.LENGTH_SHORT).show();
    }
    //自定义Log
    public void sendLog(String text){
        Log.i(PP_TIP,text);
    }
}
