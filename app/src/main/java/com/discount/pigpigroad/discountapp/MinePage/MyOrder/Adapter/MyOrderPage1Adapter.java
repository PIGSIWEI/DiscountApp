package com.discount.pigpigroad.discountapp.MinePage.MyOrder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.discount.pigpigroad.discountapp.MinePage.MyOrder.OrderBean.GoodsOrderInfo;
import com.discount.pigpigroad.discountapp.MinePage.MyOrder.OrderBean.OrderGoodsItem;
import com.discount.pigpigroad.discountapp.MinePage.MyOrder.OrderBean.OrderPayInfo;
import com.discount.pigpigroad.discountapp.R;
import com.discount.pigpigroad.discountapp.Util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.PP_TIP;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.URL_BASE;

/**
 * Created by PIGROAD on 2018/5/11.
 * Email:920015363@qq.com
 */

public class MyOrderPage1Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Object> mdatas;
    private View headerView;
    private int ITEM_HEADER = 1, ITEM_CONTENT = 2, ITEM_FOOTER = 3;
    private Context context;
    private String login_token;
    private GoodsOrderInfo Gdatas;
    private OrderGoodsItem Odatas;
    public static final String action = "adapter.broadcast.action";
    public MyOrderPage1Adapter(Context context, List<Object> data) {
        this.context = context;
        this.mdatas = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_HEADER) {
            view = LayoutInflater.from(context).inflate(R.layout.allorder_head, parent, false);
            return new MyOrderPage1Adapter.MyViewHolderHeader(view);
        } else if (viewType == ITEM_CONTENT) {
            view = LayoutInflater.from(context).inflate(R.layout.allorder_body, parent, false);
            return new MyOrderPage1Adapter.MyViewHolderContent(view);
        } else if (viewType == ITEM_FOOTER) {
            view = LayoutInflater.from(context).inflate(R.layout.allorder_foot, parent, false);
            return new MyOrderPage1Adapter.MyViewHolderFooter(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //获取token
        SharedPreferences sp = context.getSharedPreferences("User", MODE_PRIVATE);
        login_token = sp.getString("user_token", null);
        if (holder instanceof MyViewHolderHeader) {
            Gdatas = (GoodsOrderInfo) mdatas.get(position);
            ((MyViewHolderHeader) holder).tv_addtime.setText(Gdatas.getOrderCode());
            //判断当前订单状态
            //0为退款1为待付款2为支付中3为待发货4待收货5为待评价6已完成


        } else if (holder instanceof MyViewHolderContent) {

            Odatas = (OrderGoodsItem) mdatas.get(position);
            ((MyViewHolderContent) holder).tv_product_price.setText("¥" + Odatas.getProductPrice());
            ((MyViewHolderContent) holder).tv_product_name.setText(Odatas.getProductName());
            Glide.with(context).load(Odatas.getProductPic()).into(((MyViewHolderContent) holder).iv_product_img);


        } else if (holder instanceof MyViewHolderFooter) {
            final OrderPayInfo datas = (OrderPayInfo) mdatas.get(position);
            //获取订单类型
            //0为退款1为待付款2为支付中3为待发货4待收货5为待评价6已完成
            if (datas.getStatus().equals("0")) {
                ((MyViewHolderFooter) holder).btn_cancel.setVisibility(View.GONE);
                ((MyViewHolderFooter) holder).btn_pay.setVisibility(View.GONE);
                ((MyViewHolderFooter) holder).btn_delete.setVisibility(View.VISIBLE);
            } else if (datas.getStatus().equals("1")) {
                ((MyViewHolderFooter) holder).btn_cancel.setVisibility(View.VISIBLE);
                ((MyViewHolderFooter) holder).btn_pay.setVisibility(View.VISIBLE);
                ((MyViewHolderFooter) holder).btn_delete.setVisibility(View.GONE);
            } else if (datas.getStatus().equals("2")) {

            } else if (datas.getStatus().equals("3")) {

            } else if (datas.getStatus().equals("4")) {

            } else if (datas.getStatus().equals("5")) {

            } else if (datas.getStatus().equals("6")) {

            }

            //取消订单操作
            ((MyViewHolderFooter) holder).btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancelorder(datas, ((MyViewHolderFooter) holder).btn_cancel, ((MyViewHolderFooter) holder).btn_pay, ((MyViewHolderFooter) holder).btn_delete);
                }
            });
            //删除订单操作
            ((MyViewHolderFooter) holder).btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // deleteorder(datas);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mdatas.get(position) instanceof GoodsOrderInfo) {
            return ITEM_HEADER;
        } else if (mdatas.get(position) instanceof OrderGoodsItem) {
            return ITEM_CONTENT;
        } else if (mdatas.get(position) instanceof OrderPayInfo) {
            return ITEM_FOOTER;
        }
        return ITEM_CONTENT;
    }


    //body
    class MyViewHolderContent extends RecyclerView.ViewHolder {
        private ImageView iv_product_img;
        private TextView tv_product_name, tv_product_price;

        public MyViewHolderContent(View view) {
            super(view);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_product_price = itemView.findViewById(R.id.tv_product_price);
            iv_product_img = itemView.findViewById(R.id.iv_product_img);
        }
    }

    //head
    class MyViewHolderHeader extends RecyclerView.ViewHolder {
        public TextView tv_addtime,tv_state;

        public MyViewHolderHeader(View view) {
            super(view);
            tv_addtime = itemView.findViewById(R.id.tv_addtime);
            tv_state =itemView.findViewById(R.id.tv_state);
        }
    }
    //foot
    class MyViewHolderFooter extends RecyclerView.ViewHolder {
        private Button btn_cancel, btn_pay, btn_delete;

        public MyViewHolderFooter(View view) {
            super(view);
            btn_cancel = view.findViewById(R.id.btn_cancel);
            btn_pay = view.findViewById(R.id.btn_pay);
            btn_delete = view.findViewById(R.id.btn_delete);
        }
    }

    @Override
    public int getItemCount() {
        int count = (mdatas == null ? 0 : mdatas.size());
        if (headerView != null) {
            count++;
        }
        return count;
    }

    public View getHeaderView() {
        return headerView;
    }
    //点击事件

    /**
     * 取消订单操作
     * @param datas
     * @param btn_cancel
     * @param btn_pay
     * @param btn_delete
     */
    private void cancelorder(final OrderPayInfo datas, final Button btn_cancel, final Button btn_pay, final Button btn_delete){
        OkGo.<String>post(URL_BASE + "?request=private.order.cancel.order.action&platform=android&token=" + login_token + "&orderid="+datas.getOrderid())
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String responseStr = response.body();//这个就是返回来的结果
                        Log.i(PP_TIP,"responseStr="+responseStr+",datas="+datas.getOrder_state());
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            int code = jsonObject.getInt("code");
                            String msg=jsonObject.getString("msg");
                            if (code == 0) {
                                btn_cancel.setVisibility(View.GONE);
                                btn_pay.setVisibility(View.GONE);
                                btn_delete.setVisibility(View.GONE);
                                ToastUtils.showToast(msg);
                                datas.setStatus("0");
                                Gdatas.setStatus("0");
                                notifyDataSetChanged();
                            }else {
                                ToastUtils.showToast(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 删除订单操作
     */
    private void  deleteorder(final OrderPayInfo datas){
        OkGo.<String>post(URL_BASE + "?request=private.order.delete.order.action&platform=android&token=" + login_token + "&orderid="+datas.getOrderid())
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                             @Override
                             public void onSuccess(Response<String> response) {
                                 String responseStr = response.body();//这个就是返回来的结果
                                 try {
                                     JSONObject jsonObject = new JSONObject(responseStr);
                                     int code = jsonObject.getInt("code");
                                     String msg=jsonObject.getString("msg");
                                     if (code == 0) {
                                         ToastUtils.showToast(msg);
                                         mdatas.clear();
                                         //线程更新数据，通过广播更新
                                         Intent intent=new Intent(action);
                                         context.sendBroadcast(intent);
                                         notifyDataSetChanged();
                                     }else {
                                         ToastUtils.showToast(msg);
                                     }
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }
                             }
                         }
                );
    }


}
