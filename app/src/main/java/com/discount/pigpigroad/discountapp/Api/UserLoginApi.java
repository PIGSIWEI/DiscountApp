package com.discount.pigpigroad.discountapp.Api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.discount.pigpigroad.discountapp.MainActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import static com.discount.pigpigroad.discountapp.Api.ApiConnect.PP_TIP;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.URL_BASE;

/**
 * 登陆操作
 * Created by PIGROAD on 2018/4/12.
 * Email:920015363@qq.com
 */

public class UserLoginApi {
    int result;
    long time = System.currentTimeMillis()/1000;
    String nowtime=String.valueOf(time);
    //声明Sharedpreferenced对象
    private SharedPreferences sp ;
    private Intent intent;
    /**
     * 获取短信验证码
     * @param phonenumber
     * @param context
     * @return
     */

    public boolean Getsms(String phonenumber, final Context context){
        OkGo.<String>post(URL_BASE+"?phone="+phonenumber+"&platform=android&request=public.user.login.sms.code")
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //注意这里已经是在主线程了
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            int code = (int) jsonObject.get("code");
                            if (code == 0) {
                                Toast.makeText(context, "获取验证码成功！", Toast.LENGTH_SHORT).show();
                                result=0;
                            }else if (code == 1) {
                                Toast.makeText(context, "服务器错误，请稍后再试", Toast.LENGTH_SHORT).show();
                                result=1;
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(PP_TIP, "连接失败!!!!!!!!!!!!!!!");
                        Toast.makeText(context, "请检查你的网络设置", Toast.LENGTH_SHORT).show();
                    }
                });
        if (result==0){
            return true;
        }else {
            return false;
        }
    };
    /**
     * 使用短信验证码登录
     */
    public void SmsLogin(String phonenumber, final String smscode, final Activity activity) {
        OkGo.<String>post(URL_BASE+"?route=data_public&phone="+phonenumber+"&smscode="+smscode+"&platform=android&time="+nowtime+"&request=public.user.login.from.phone")
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //注意这里已经是在主线程了
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            int code = (int) jsonObject.get("code");
                            if (code == 0) {
                                Toast.makeText(activity, "登录成功!", Toast.LENGTH_SHORT).show();
                                String token= (String) jsonObject.get("token");
                                /**
                                 * 获取SharedPreferenced对象
                                 * 第一个参数是生成xml的文件名
                                 * 第二个参数是存储的格式（**注意**本文后面会讲解）
                                 */
                                sp = activity.getSharedPreferences("User", Context.MODE_PRIVATE);
                                //获取到edit对象
                                SharedPreferences.Editor edit = sp.edit();
                                //通过editor对象写入数据
                                edit.putString("user_token",token);
                                //提交数据存入到xml文件中
                                edit.commit();
                                intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
                            }else if (code == 1) {
                                String msg= (String) jsonObject.get("msg");
                                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(PP_TIP, "连接失败!!!!!!!!!!!!!!!");
                        Toast.makeText(activity, "请检查你的网络设置", Toast.LENGTH_SHORT).show();
                    }
                });
    };
    /**
     * 使用密码登录
     */
    public void PasswordLogin(String phonenumber, final String password, final Activity activity) {
        OkGo.<String>post(URL_BASE+"?route=data_public&phone="+phonenumber+"&pwd="+password+"&platform=android&time="+nowtime+"&request=public.user.login.from.pwd")
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //注意这里已经是在主线程了
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            int code = (int) jsonObject.get("code");
                            if (code == 0) {
                                Toast.makeText(activity, "登录成功!", Toast.LENGTH_SHORT).show();
                                String token= (String) jsonObject.get("token");
                                /**
                                 * 获取SharedPreferenced对象
                                 * 第一个参数是生成xml的文件名
                                 * 第二个参数是存储的格式（**注意**本文后面会讲解）
                                 */
                                sp = activity.getSharedPreferences("User", Context.MODE_PRIVATE);
                                //获取到edit对象
                                SharedPreferences.Editor edit = sp.edit();
                                //通过editor对象写入数据
                                edit.putString("user_token",token);
                                //提交数据存入到xml文件中
                                edit.commit();
                                intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
                            }else if (code == 1) {
                                String msg= (String) jsonObject.get("msg");
                                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(PP_TIP, "连接失败!!!!!!!!!!!!!!!");
                        Toast.makeText(activity, "请检查你的网络设置", Toast.LENGTH_SHORT).show();
                    }
                });
    };
    /**
     * 注册获取验证码
     */
    public boolean RegisterGetsms(String phonenumber, final Context context){
        OkGo.<String>post(URL_BASE+"?phone="+phonenumber+"&platform=android&request=public.user.reg.sms.code")
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //注意这里已经是在主线程了
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            int code = (int) jsonObject.get("code");
                            if (code == 0) {
                                Toast.makeText(context, "获取验证码成功！", Toast.LENGTH_SHORT).show();
                                result=0;
                            }else if (code == 1) {
                                Toast.makeText(context, "服务器错误，请稍后再试", Toast.LENGTH_SHORT).show();
                                result=1;
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(PP_TIP, "连接失败!!!!!!!!!!!!!!!");
                        Toast.makeText(context, "请检查你的网络设置", Toast.LENGTH_SHORT).show();
                    }
                });
        if (result==0){
            return true;
        }else {
            return false;
        }
    };
    /**
     * 注册操作
     */
    public void Register(String phonenumber,String password,String smscode,String username, final Context context) {
        OkGo.<String>post(URL_BASE+"?phone="+phonenumber+"&platform=android&request=public.user.reg.new.user.action&username="+username+"&pwd="+password+"&smscode="+smscode)
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //注意这里已经是在主线程了
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            int code = (int) jsonObject.get("code");
                            String msg= (String) jsonObject.get("msg");
                            if (code == 0) {
                                Toast.makeText(context, "注册成功!", Toast.LENGTH_SHORT).show();
                            }else if (code == 1) {
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(PP_TIP, "连接失败!!!!!!!!!!!!!!!");
                        Toast.makeText(context, "请检查你的网络设置", Toast.LENGTH_SHORT).show();
                    }
                });
    };
}
