package com.discount.pigpigroad.discountapp.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.discount.pigpigroad.discountapp.Login.LoginActivity;
import com.discount.pigpigroad.discountapp.MinePage.Address.AddressActivity;
import com.discount.pigpigroad.discountapp.MinePage.ChangeData.ChangeDataActivity;
import com.discount.pigpigroad.discountapp.MinePage.Common.CommonActivity;
import com.discount.pigpigroad.discountapp.MinePage.IntergralUseforActivity;
import com.discount.pigpigroad.discountapp.MinePage.MyIntegralActivity;
import com.discount.pigpigroad.discountapp.MinePage.MyOrder.MyOrderActivity;
import com.discount.pigpigroad.discountapp.MinePage.Setting.SettingActivity;
import com.discount.pigpigroad.discountapp.MinePage.ShoppingCart.ShoppingCartActivity;
import com.discount.pigpigroad.discountapp.MinePage.SubscribeSetting.SubscribeSettingActivity;
import com.discount.pigpigroad.discountapp.MinePage.MyCollection.MyCollectionActivity;
import com.discount.pigpigroad.discountapp.MinePage.TipOff.TipOffActivity;
import com.discount.pigpigroad.discountapp.MinePage.ViewHistoryActivity;
import com.discount.pigpigroad.discountapp.MyApplication;
import com.discount.pigpigroad.discountapp.R;
import com.discount.pigpigroad.discountapp.ShopDetailsActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.PP_TIP;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.URL_BASE;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.USER_OVERDUE;
import static com.discount.pigpigroad.discountapp.BaseActivity.isFastClick;

/**
 * Created by XYSM on 2018/3/20.
 */

public class MinePage extends Fragment implements View.OnClickListener {
    private LinearLayout ll_setting, ll_integral,ll_integral_usefor,ll_subscribe_setting,ll_checkorder,ll_mycollection,ll_tipoff,
    ll_viewhistory,ll_common,ll_changedata,ll_shoppingcart,ll_address;
    String logintime,login_token;
    private TextView tv_user_name;
    private ImageView iv_user_pho;
    private Intent userintent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View MinePageLayout = inflater.inflate(R.layout.minepage_layout,
                container, false);
        return MinePageLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        ll_address=getActivity().findViewById(R.id.ll_address);
        ll_address.setOnClickListener(this);
        ll_shoppingcart=getActivity().findViewById(R.id.ll_shoppingcart);
        ll_shoppingcart.setOnClickListener(this);
        ll_changedata=getActivity().findViewById(R.id.ll_changedata);
        ll_changedata.setOnClickListener(this);
        ll_common=getActivity().findViewById(R.id.ll_common);
        ll_common.setOnClickListener(this);
        ll_viewhistory=getActivity().findViewById(R.id.ll_viewhistory);
        ll_viewhistory.setOnClickListener(this);
        ll_checkorder=getActivity().findViewById(R.id.ll_checkorder);
        ll_checkorder.setOnClickListener(this);
        ll_setting = getActivity().findViewById(R.id.ll_setting);
        ll_setting.setOnClickListener(this);
        ll_integral = getActivity().findViewById(R.id.ll_integral);
        ll_integral.setOnClickListener(this);
        ll_integral_usefor=getActivity().findViewById(R.id.ll_integral_usefor);
        ll_integral_usefor.setOnClickListener(this);
        ll_subscribe_setting=getActivity().findViewById(R.id.ll_subscribe_setting);
        ll_subscribe_setting.setOnClickListener(this);
        ll_mycollection=getActivity().findViewById(R.id.ll_mycollection);
        ll_mycollection.setOnClickListener(this);
        ll_tipoff=getActivity().findViewById(R.id.ll_tipoff);
        ll_tipoff.setOnClickListener(this);
        tv_user_name=getActivity().findViewById(R.id.tv_user_name);
        iv_user_pho=getActivity().findViewById(R.id.iv_user_pho);
        iv_user_pho.setOnClickListener(this);
        //获取时间戳
        long time = System.currentTimeMillis()/1000;
        logintime=String.valueOf(time);
        //获取token
        SharedPreferences sp = getActivity().getSharedPreferences("User", MODE_PRIVATE);
        login_token=sp.getString("user_token",null);
        getUserinfo();
        userintent=new Intent(getActivity(),ChangeDataActivity.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //查看设置
            case R.id.ll_setting:
                if (isFastClick()){
                    return;
                }
                Intent intent0=new Intent(getActivity(),SettingActivity.class);
                startActivity(intent0);
                break;
            //查看我的积分
            case R.id.ll_integral:
                if (isFastClick()){
                    return;
                }
                Intent intent=new Intent(getActivity(),MyIntegralActivity.class);
                startActivity(intent);
                break;
            //查看积分有什么用
            case R.id.ll_integral_usefor:
                Intent intent2=new Intent(getActivity(),IntergralUseforActivity.class);
                startActivity(intent2);
                break;
            //查看好价订阅设置
            case R.id.ll_subscribe_setting:
                if (isFastClick()){
                    return;
                }
                Intent intent3=new Intent(getActivity(),SubscribeSettingActivity.class);
                startActivity(intent3);
                break;
            //查看订单
            case R.id.ll_checkorder:
                if (isFastClick()){
                    return;
                }
                Intent intent4=new Intent(getActivity(),MyOrderActivity.class);
                startActivity(intent4);
                break;
            //查看我的收藏
            case R.id.ll_mycollection:
                Intent intent5=new Intent(getActivity(),MyCollectionActivity.class);
                startActivity(intent5);
                break;
            //查看我要爆料
            case R.id.ll_tipoff:
                if (isFastClick()){
                    return;
                }
                Intent intent6=new Intent(getActivity(),TipOffActivity.class);
                startActivity(intent6);
                break;
            //查看历史浏览记录
            case R.id.ll_viewhistory:
                Intent intent7=new Intent(getActivity(),ViewHistoryActivity.class);
                startActivity(intent7);
                break;
            //查看评论
            case R.id.ll_common:
                if (isFastClick()){
                    return;
                }
                Intent intent8=new Intent(getActivity(),CommonActivity.class);
                startActivity(intent8);
                break;
            //修改个人资料
            case R.id.ll_changedata:
                startActivity(userintent);
                 break;
            //查看购物车
            case R.id.ll_shoppingcart:
                if (isFastClick()){
                    return;
                }
                Intent intent10=new Intent(getActivity(),ShoppingCartActivity.class);
                startActivity(intent10);
                break;
            //管理收获地址
            case R.id.ll_address:
                if (isFastClick()){
                    return;
                }
                Intent intent11=new Intent(getActivity(),AddressActivity.class);
                startActivity(intent11);
                break;
            //查看个人信息
            case R.id.iv_user_pho:
                startActivity(userintent);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserinfo();
    }

    //获取用户id和头像
    public void getUserinfo(){
        OkGo.<String>post(URL_BASE + "?platform=android&time="+logintime+"&token="+login_token+"&request=private.user.detail.get")
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            int code=jsonObject.getInt("code");
                            if (code==0){
                                Log.i(PP_TIP,"获取用户信息成功！");
                                JSONObject data=jsonObject.getJSONObject("data");
                                tv_user_name.setText(data.getString("username"));
                                //判断头像是否为空
                                Glide.with(getActivity()).load(data.getString("pho")).into(iv_user_pho);
                            }else if (code==999){
                                SharedPreferences sp = getActivity().getSharedPreferences("User", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.clear();
                                editor.commit();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                getActivity().startActivity(intent);
                                Toast.makeText(getActivity(), USER_OVERDUE, Toast.LENGTH_SHORT).show();
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
