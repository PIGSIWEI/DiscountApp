package com.discount.pigpigroad.discountapp.MinePage.ChangeData;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.R;

/**
 * Created by PIGROAD on 2018/4/24.
 * Email:920015363@qq.com
 */

public class ChangePhoneActivity extends BaseActivity implements View.OnClickListener{
    private Button btn_getmsg,btn_change;
    private EditText et_phone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changephone_layout);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        btn_change=findViewById(R.id.btn_changename);
        btn_getmsg=findViewById(R.id.btn_getmsg);
        et_phone=findViewById(R.id.et_phone);
        et_phone.setInputType(InputType.TYPE_CLASS_NUMBER);
        btn_getmsg.setOnClickListener(this);
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
            btn_getmsg.setClickable(false);
            btn_getmsg.setText(l / 1000 + "s");
        }

        @Override
        public void onFinish() {
            //重新给Button设置文字
            btn_getmsg.setText("重新获取");
            //设置可点击
            btn_getmsg.setClickable(true);
        }
    }
    /**
     * 设置点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_getmsg:
                String phome=et_phone.getText().toString().trim();
                if (phome.equals("")){
                    sendToast("手机号不能为空");
                }else if (isMobileNO(phome)){
                    sendToast("ojbk!");
                }else {
                    sendToast("请正确输入手机号");
                }
                break;
        }
    }
    /**
     * 修改手机号
     */
    public void changephonenumber(){

    }
}
