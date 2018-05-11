package com.discount.pigpigroad.discountapp.MinePage.PayOrder.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.discount.pigpigroad.discountapp.MinePage.MyOrder.OrderBean.GoodsOrderInfo;
import com.discount.pigpigroad.discountapp.MinePage.MyOrder.OrderBean.OrderGoodsItem;
import com.discount.pigpigroad.discountapp.MinePage.MyOrder.OrderBean.OrderPayInfo;
import com.discount.pigpigroad.discountapp.R;

import java.util.List;


/**
 * Created by PIGROAD on 2018/4/29.
 * Email:920015363@qq.com
 */

public class PayOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> data;
    private Context context;
    private View headerView;
    private int ITEM_HEADER = 1, ITEM_CONTENT = 2, ITEM_FOOTER = 3;

    public PayOrderAdapter(Context context, List<Object> data) {
        this.data = data;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_HEADER) {
            view = LayoutInflater.from(context).inflate(R.layout.payorder_head, parent, false);
            return new PayOrderAdapter.MyViewHolderHeader(view);
        } else if (viewType == ITEM_CONTENT) {
            view = LayoutInflater.from(context).inflate(R.layout.payorder_body, parent, false);
            return new PayOrderAdapter.MyViewHolderContent(view);
        } else if (viewType == ITEM_FOOTER) {
            view = LayoutInflater.from(context).inflate(R.layout.payorder_foot, parent, false);
            return new PayOrderAdapter.MyViewHolderFooter(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolderHeader) {
            GoodsOrderInfo datas = (GoodsOrderInfo) data.get(position);
            ((MyViewHolderHeader) holder).tv_shop_title.setText(datas.getShopName());
            Glide.with(context).load(datas.getShopPic()).into(((MyViewHolderHeader) holder).iv_shop_pho);
        } else if (holder instanceof MyViewHolderContent) {
            OrderGoodsItem datas = (OrderGoodsItem) data.get(position);
            ((MyViewHolderContent) holder).tv_product_attr.setText(datas.getAttr_name());
            ((MyViewHolderContent) holder).tv_product_count.setText("x" + datas.getCount());
            ((MyViewHolderContent) holder).tv_product_price.setText(datas.getProductPrice());
            ((MyViewHolderContent) holder).tv_product_title.setText(datas.getProductName());
            Glide.with(context).load(datas.getProductPic()).into(((MyViewHolderContent) holder).iv_product_pho);
        } else if (holder instanceof MyViewHolderFooter) {

        }
    }

    @Override
    public int getItemCount() {
        int count = (data == null ? 0 : data.size());
        if (headerView != null) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof GoodsOrderInfo) {
            return ITEM_HEADER;
        } else if (data.get(position) instanceof OrderGoodsItem) {
            return ITEM_CONTENT;
        } else if (data.get(position) instanceof OrderPayInfo) {
            return ITEM_FOOTER;
        }
        return ITEM_CONTENT;
    }

    //三种适配器
    //head
    class MyViewHolderHeader extends RecyclerView.ViewHolder {
        private TextView tv_shop_title;
        private ImageView iv_shop_pho;

        public MyViewHolderHeader(View itemView) {
            super(itemView);
            tv_shop_title = itemView.findViewById(R.id.tv_shop_title);
            iv_shop_pho = itemView.findViewById(R.id.iv_shop_pho);

        }
    }

    //body
    class MyViewHolderContent extends RecyclerView.ViewHolder {
        private TextView tv_product_title, tv_product_attr, tv_product_price, tv_product_count;
        private ImageView iv_product_pho;

        public MyViewHolderContent(View itemView) {
            super(itemView);
            tv_product_title = itemView.findViewById(R.id.tv_product_title);
            tv_product_attr = itemView.findViewById(R.id.tv_product_attr);
            tv_product_price = itemView.findViewById(R.id.tv_product_price);
            tv_product_count = itemView.findViewById(R.id.tv_product_count);
            iv_product_pho = itemView.findViewById(R.id.iv_product_pho);

        }
    }

    //foot
    class MyViewHolderFooter extends RecyclerView.ViewHolder {

        public MyViewHolderFooter(View itemView) {
            super(itemView);
        }
    }

    public View getHeaderView() {
        return headerView;
    }
}
