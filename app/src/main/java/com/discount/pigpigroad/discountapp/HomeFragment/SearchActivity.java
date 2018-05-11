package com.discount.pigpigroad.discountapp.HomeFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.discount.pigpigroad.discountapp.BaseActivity;
import com.discount.pigpigroad.discountapp.Flow.Flowlayout;
import com.discount.pigpigroad.discountapp.Flow.TagItem;
import com.discount.pigpigroad.discountapp.R;

import java.util.ArrayList;

/**
 * Created by PIGROAD on 2018/4/17.
 * Email:920015363@qq.com
 */

public class SearchActivity extends BaseActivity {
    private Button btn_search;
    private ImageView btn_clear;
    private EditText et_search;
    private LinearLayout ll_back;
    private String[] mTextStr = new String[]
            {"女鞋", "连衣裙","两件套","女包","卫衣","零食","运动鞋","耳机","面膜","玩具"};
    private String[] mTextStr2 = new String[]
            {};
    private int keynumber=0;
    Flowlayout mTagLayout,mTagLayout2;
    ArrayList<String>  list = new ArrayList<String>();
    ArrayList<String>  list2 = new ArrayList<String>();
    private ArrayList<TagItem> mAddTags = new ArrayList<TagItem>();
    private ArrayList<TagItem> mAddTags2 = new ArrayList<TagItem>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        init();
        initList();
        initLayout(list);
        initList2();
        initLayout2(list2);
    }
    /**
     * 初始化
     */
    private void init() {
        btn_clear=findViewById(R.id.btn_clear);
        mTagLayout=findViewById(R.id.flowlayout);
        mTagLayout2=findViewById(R.id.flowlayout2);
        et_search=findViewById(R.id.et_search);
        ll_back=findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_search=findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String label = et_search.getText().toString().trim();
                String[] newStr = new String[mTagLayout2.getChildCount()];
                /**
                 * 获取  子view的数量   并添加进去
                 */
                if(label!=null&&!label.equals("")){
                    if (keynumber<20){
                        for(int m = 0;m < mTagLayout2.getChildCount()-1;m++){
                            newStr[m] =((TextView)mTagLayout2.getChildAt(m).
                                    findViewById(R.id.text)).getText().toString();//根据  当前   位置查找到 当前    textView中标签  内容
                        }
                        list2.add(label);
                        initLayout2(list2);
                        et_search.setText("");
                        keynumber=keynumber+1;
                    }else {

                    }

                }
            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTagLayout2.removeAllViews();
                list2.clear();
                initLayout2(list2);
                keynumber=0;
            }
        });
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
            final View view = (View) LayoutInflater.from(SearchActivity.this).inflate(R.layout.search_textview, mTagLayout, false);
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
                            //点击事件
                            et_search.setText(text.getText());
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
     * 初始化标签layout
     */
    private void initLayout2(final ArrayList<String> arr) {
        mTagLayout2.removeAllViewsInLayout();
         /**
         * 创建 textView数组
         */
        final TextView[] textViews = new TextView[arr.size()];
        final TextView[] icons = new TextView[arr.size()];

        for (final int[] i = {0}; i[0] < arr.size(); i[0]++) {
            final int pos = i[0];
            final View view = (View) LayoutInflater.from(SearchActivity.this).inflate(R.layout.search_textview, mTagLayout2, false);
            final TextView text = (TextView) view.findViewById(R.id.text);  //查找  到当前     textView
            final TextView icon = (TextView) view.findViewById(R.id.delete_icon);  //查找  到当前  删除小图标
            // 将     已有标签设置成      可选标签
            text.setText(list2.get(i[0]));
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
                    boolean bResult = doAddText2(list2.get(pos), false, pos);
                    text.setActivated(bResult);
                    //遍历   数据    将图标设置为可见：
                    for(int j = 0;j< textViews.length;j++){
                        if(text.equals(textViews[j])){
                            //点击事件
                            et_search.setText(text.getText());
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
            mTagLayout2.addView(view);
        }
    }
    // 标签添加文本状态
    private boolean doAddText2(final String str, boolean bCustom, int idx) {
        int tempIdx = idxTextTag2(str);
        if (tempIdx >= 0) {
            TagItem item = mAddTags2.get(tempIdx);
            item.tagCustomEdit = false;
            item.idx = tempIdx;
            return true;
        }
        int tagCnt = mAddTags2.size(); // 添加标签的条数
        TagItem item = new TagItem();
        item.tagText = str;
        item.tagCustomEdit = bCustom;
        item.idx = idx;
        mAddTags2.add(item);
        tagCnt++;
        return true;
    }
    /**
     * 初始化list
     */
    private void initList2() {
        for(int i=0;i<mTextStr2.length;i++){
            list2.add(mTextStr2[i]);
        }
    }
    // 标签索引文本
    protected int idxTextTag2(String text) {
        int mTagCnt = mAddTags2.size(); // 添加标签的条数
        for (int i = 0; i < mTagCnt; i++) {
            TagItem item = mAddTags2.get(i);
            if (text.equals(item.tagText)) {
                return i;
            }
        }
        return -1;
    }
}
