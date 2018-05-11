package com.discount.pigpigroad.discountapp.MinePage.MyCollection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.discount.pigpigroad.discountapp.MinePage.ShoppingCart.ItemTouchListener;
import com.discount.pigpigroad.discountapp.R;
import com.discount.pigpigroad.discountapp.Util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.URL_BASE;

/**
 * Created by PIGROAD on 2018/5/10.
 * Email:920015363@qq.com
 */

public class MyCollectionAdapter extends RecyclerView.Adapter<MyCollectionAdapter.MyViewHolder>{
    private ItemTouchListener mItemTouchListener;
    private OnDeleteClickListener mOnDeleteClickListener;
    private List<MyCollectionBean> data;
    private Context context;
    private String login_token;
    public MyCollectionAdapter(Context context, List<MyCollectionBean> data, ItemTouchListener itemTouchListener){
        this.data=data;
        this.context=context;
        this.mItemTouchListener=itemTouchListener;
    }
    @Override
    public MyCollectionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mycollection_item, parent, false);
        //获取token
        SharedPreferences sp = context.getSharedPreferences("User", MODE_PRIVATE);
        login_token = sp.getString("user_token", null);
        return new MyCollectionAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyCollectionAdapter.MyViewHolder holder, final int position) {
        final MyCollectionBean value=data.get(position);
        Glide.with(context).load(value.getProduct_img()).into(holder.iv_product_img);
        holder.tv_addtime.setText(value.getAddtime());
        holder.tv_product_name.setText(value.getProduct_name());
        holder.tv_product_price.setText(value.getProduct_price());
        if (mItemTouchListener != null) {
            if (holder.mRightMenu != null) {
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(350,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                holder.mRightMenu.setLayoutParams(params);
                holder.mRightMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                           deleteTranslate(value.getProduct_id(),position,view);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_addtime,tv_product_price,tv_product_name;
        ImageView iv_product_img;
        View mRightMenu;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_addtime=itemView.findViewById(R.id.tv_addtime);
            tv_product_price=itemView.findViewById(R.id.tv_product_price);
            tv_product_name=itemView.findViewById(R.id.tv_product_name);
            iv_product_img=itemView.findViewById(R.id.iv_product_img);
            mRightMenu=itemView.findViewById(R.id.right_menu);
        }
    }
    /**
     * 删除收藏商品
     */
    private void deleteTranslate(String product_id, final int position, final View view) {
        OkGo.<String>post(URL_BASE + "?request=private.collect.del.collect.goods.action&token=" + login_token + "&platform=android&del_id=" + product_id)
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                             @Override
                             public void onSuccess(Response<String> response) {
                                 String responseStr = response.body();//这个就是返回来的结果
                                 try {
                                     JSONObject jsonObject = new JSONObject(responseStr);
                                     int code = jsonObject.getInt("code");
                                     String msg = jsonObject.getString("msg");
                                     if (code == 0) {
                                         ToastUtils.showToast(msg);
                                         delete(view,position);
                                         if (data.isEmpty()){

                                         }
                                         notifyDataSetChanged();

                                     }
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }
                             }
                         }
                );
    }
    public interface OnDeleteClickListener {
        void onDeleteClick(View view, int position, String cartid);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener mOnDeleteClickListener) {
        this.mOnDeleteClickListener = mOnDeleteClickListener;
    }

    /**
     * 删除收藏
     */
     public void delete(final View view, final int position){
         //调用删除某个规格商品的接口
         if (mOnDeleteClickListener != null) {
             mOnDeleteClickListener.onDeleteClick(view, position, data.get(position).getProduct_id());
         }
         data.remove(position);
         notifyDataSetChanged();
     }
}
