package com.discount.pigpigroad.discountapp.MinePage.SubscribeSetting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.Flow.Flowlayout;
import com.discount.pigpigroad.discountapp.Flow.TagItem;
import com.discount.pigpigroad.discountapp.R;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.util.ArrayList;

/**
 * Created by PIGROAD on 2018/4/13.
 * Email:920015363@qq.com
 */

public class SubscribeSettingActivity extends BaseActivity implements View.OnClickListener{
    private String[] mTextStr = new String[]
            {"LG洗衣机", "西门子冰箱"};
    Flowlayout mTagLayout;
    private EditText inputLabel;
    int keynumber=2;
    private TextView tv,tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9;
    private Button btn_sure;
    private LinearLayout ll_mysubscribe;
    private ArrayList<TagItem> mAddTags = new ArrayList<TagItem>();
    ArrayList<String>  list = new ArrayList<String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscribe_setting_layout);
        //初始化
        inti();
        initList();
        initLayout(list);
    }

    /**
     * 初始化标签layout
     */
    private void initLayout(final ArrayList<String> arr) {
        mTagLayout.removeAllViewsInLayout();
        /**
         * 创建 textView数组
         */
        final TextView[] textViews = new TextView[arr.size()];
        final TextView[] icons = new TextView[arr.size()];

        for (final int[] i = {0}; i[0] < arr.size(); i[0]++) {
            final int pos = i[0];
            final View view = (View) LayoutInflater.from(SubscribeSettingActivity.this).inflate(R.layout.flowlayout_textview, mTagLayout, false);
            final TextView text = (TextView) view.findViewById(R.id.text);  //查找  到当前     textView
            final TextView icon = (TextView) view.findViewById(R.id.delete_icon);  //查找  到当前  删除小图标
            // 将     已有标签设置成      可选标签
            text.setText(list.get(i[0]));
            /**
             * 将当前  textView  赋值给    textView数组
             */
            textViews[i[0]] = text;
            icons[i[0]] = icon;
            //设置    单击事件：
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        text.setActivated(!text.isActivated()); // true是激活的
                        boolean bResult = doAddText(list.get(pos), false, pos);
                        text.setActivated(bResult);
                        //遍历   数据    将图标设置为可见：
                        for(int j = 0;j< textViews.length;j++){
                            if(text.equals(textViews[j])){
                                new TDialog.Builder(getSupportFragmentManager())
                                        .addOnClickListener(R.id.btn_cancel, R.id.btn_exit)
                                        .setOnViewClickListener(new OnViewClickListener() {
                                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                                switch (view.getId()) {
                                                    case R.id.btn_cancel:
                                                        tDialog.dismiss();
                                                        break;
                                                    case R.id.btn_exit:
                                                        for(int j = 0; j < icons.length;j++) {
                                                            if (icon.equals(icons[j])) {  //获取   当前  点击删除图标的位置：
                                                                mTagLayout.removeViewAt(j);
                                                                list.remove(j);
                                                                initLayout(list);
                                                                keynumber=keynumber-1;
                                                            }
                                                        }
                                                        tDialog.dismiss();
                                                        break;
                                                }
                                            }
                                        })
                                        .setLayoutRes(R.layout.delect_layout)                        //弹窗布局
                                        .setScreenWidthAspect(SubscribeSettingActivity.this, 0.9f)               //屏幕宽度比
                                        .setDimAmount(0f)                                                    //设置焦点
                                        .create()
                                        .show();
                            }
                        }
                    /**
                     * 遍历  textView  满足   已经被选中     并且不是   当前对象的textView   则置为  不选
                     */
                    for(int j = 0;j< textViews.length;j++){
                        if(!text.equals(textViews[j])){//非当前  textView
                            textViews[j].setActivated(false); // true是激活的
                            icons[j].setVisibility(View.GONE);
                        }
                    }
                }
            });
            mTagLayout.addView(view);
        }
    }
    // 标签添加文本状态
    private boolean doAddText(final String str, boolean bCustom, int idx) {
        int tempIdx = idxTextTag(str);
        if (tempIdx >= 0) {
            TagItem item = mAddTags.get(tempIdx);
            item.tagCustomEdit = false;
            item.idx = tempIdx;
            return true;
        }
        int tagCnt = mAddTags.size(); // 添加标签的条数
        TagItem item = new TagItem();
        item.tagText = str;
        item.tagCustomEdit = bCustom;
        item.idx = idx;
        mAddTags.add(item);
        tagCnt++;
        return true;
    }
    /**
     * 初始化list
     */
    private void initList() {
        for(int i=0;i<mTextStr.length;i++){
            list.add(mTextStr[i]);
        }
    }
    // 标签索引文本
    protected int idxTextTag(String text) {
        int mTagCnt = mAddTags.size(); // 添加标签的条数
        for (int i = 0; i < mTagCnt; i++) {
            TagItem item = mAddTags.get(i);
            if (text.equals(item.tagText)) {
                return i;
            }
        }
        return -1;
    }
    /**
     * 初始化
     */
    private void inti() {
        ll_mysubscribe=findViewById(R.id.ll_mysubscribe);
        ll_mysubscribe.setOnClickListener(this);
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
        tv4=findViewById(R.id.tv4);
        tv5=findViewById(R.id.tv5);
        tv6=findViewById(R.id.tv6);
        tv7=findViewById(R.id.tv7);
        tv8=findViewById(R.id.tv8);
        tv9=findViewById(R.id.tv9);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
        tv8.setOnClickListener(this);
        tv9.setOnClickListener(this);
        mTagLayout=findViewById(R.id.flowlayout);
        inputLabel = findViewById(R.id.inputLabel);
        btn_sure=findViewById(R.id.btnSure);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String label = inputLabel.getText().toString().trim();
                String[] newStr = new String[mTagLayout.getChildCount()];
                /**
                 * 获取  子view的数量   并添加进去
                 */
                if(label!=null&&!label.equals("")){
                    if (keynumber<20){
                        for(int m = 0;m < mTagLayout.getChildCount()-1;m++){
                            newStr[m] =((TextView)mTagLayout.getChildAt(m).
                                    findViewById(R.id.text)).getText().toString();//根据  当前   位置查找到 当前    textView中标签  内容
                        }
                        list.add(label);
                        initLayout(list);
                        inputLabel.setText("");
                        Toast.makeText(SubscribeSettingActivity.this, "添加标签成功", Toast.LENGTH_SHORT).show();
                        keynumber=keynumber+1;
                    }else {
                        Toast.makeText(SubscribeSettingActivity.this, "你的订阅已经达到上限！", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    /**
     * 点击操作
     * @param view
     */
    @Override
    public void onClick(View view) {
        String[] newStr = new String[mTagLayout.getChildCount()];
        String[] Strkey= {"LG洗衣机", "西门子冰箱","德运牛奶","福临门大米","清风抽纸","京东全品类券","好奇尿不湿","可悠然沐浴","肯德基优惠券"};
        switch (view.getId()){
            case R.id.tv1:
                if (keynumber<20){
                    for(int m = 0;m < mTagLayout.getChildCount()-1;m++){

                        newStr[m] =((TextView)mTagLayout.getChildAt(m).
                                findViewById(R.id.text)).getText().toString();//根据  当前   位置查找到 当前    textView中标签  内容
                    }
                    list.add(Strkey[0]);
                    initLayout(list);
                    keynumber=keynumber+1;
                }else {
                    Toast.makeText(this, "你的订阅已经达到上限！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv2:
                if (keynumber<20){
                    for(int m = 0;m < mTagLayout.getChildCount()-1;m++){

                        newStr[m] =((TextView)mTagLayout.getChildAt(m).
                                findViewById(R.id.text)).getText().toString();//根据  当前   位置查找到 当前    textView中标签  内容
                    }
                    list.add(Strkey[1]);
                    initLayout(list);
                    keynumber=keynumber+1;
                }else {
                    Toast.makeText(this, "你的订阅已经达到上限！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv3:
                if (keynumber<20){
                    for(int m = 0;m < mTagLayout.getChildCount()-1;m++){

                        newStr[m] =((TextView)mTagLayout.getChildAt(m).
                                findViewById(R.id.text)).getText().toString();//根据  当前   位置查找到 当前    textView中标签  内容
                    }
                    list.add(Strkey[2]);
                    initLayout(list);
                    keynumber=keynumber+1;
                }else {
                    Toast.makeText(this, "你的订阅已经达到上限！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv4:
                if (keynumber<20){
                    for(int m = 0;m < mTagLayout.getChildCount()-1;m++){

                        newStr[m] =((TextView)mTagLayout.getChildAt(m).
                                findViewById(R.id.text)).getText().toString();//根据  当前   位置查找到 当前    textView中标签  内容
                    }
                    list.add(Strkey[3]);
                    initLayout(list);
                    keynumber=keynumber+1;
                }else {
                    Toast.makeText(this, "你的订阅已经达到上限！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv5:
                if (keynumber<20){
                    for(int m = 0;m < mTagLayout.getChildCount()-1;m++){

                        newStr[m] =((TextView)mTagLayout.getChildAt(m).
                                findViewById(R.id.text)).getText().toString();//根据  当前   位置查找到 当前    textView中标签  内容
                    }
                    list.add(Strkey[4]);
                    initLayout(list);
                    keynumber=keynumber+1;
                }else {
                    Toast.makeText(this, "你的订阅已经达到上限！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv6:
                if (keynumber<20){
                    for(int m = 0;m < mTagLayout.getChildCount()-1;m++){

                        newStr[m] =((TextView)mTagLayout.getChildAt(m).
                                findViewById(R.id.text)).getText().toString();//根据  当前   位置查找到 当前    textView中标签  内容
                    }
                    list.add(Strkey[5]);
                    initLayout(list);
                    keynumber=keynumber+1;
                }else {
                    Toast.makeText(this, "你的订阅已经达到上限！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv7:
                if (keynumber<20){
                    for(int m = 0;m < mTagLayout.getChildCount()-1;m++){

                        newStr[m] =((TextView)mTagLayout.getChildAt(m).
                                findViewById(R.id.text)).getText().toString();//根据  当前   位置查找到 当前    textView中标签  内容
                    }
                    list.add(Strkey[6]);
                    initLayout(list);
                    keynumber=keynumber+1;
                }else {
                    Toast.makeText(this, "你的订阅已经达到上限！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv8:
                if (keynumber<20){
                    for(int m = 0;m < mTagLayout.getChildCount()-1;m++){

                        newStr[m] =((TextView)mTagLayout.getChildAt(m).
                                findViewById(R.id.text)).getText().toString();//根据  当前   位置查找到 当前    textView中标签  内容
                    }
                    list.add(Strkey[7]);
                    initLayout(list);
                    keynumber=keynumber+1;
                }else {
                    Toast.makeText(this, "你的订阅已经达到上限！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv9:
                if (keynumber<20){
                    for(int m = 0;m < mTagLayout.getChildCount()-1;m++){

                        newStr[m] =((TextView)mTagLayout.getChildAt(m).
                                findViewById(R.id.text)).getText().toString();//根据  当前   位置查找到 当前    textView中标签  内容
                    }
                    list.add(Strkey[8]);
                    initLayout(list);
                    keynumber=keynumber+1;
                }else {
                    Toast.makeText(this, "你的订阅已经达到上限！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_mysubscribe:
                Intent intent=new Intent(SubscribeSettingActivity.this,MySubscribeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
