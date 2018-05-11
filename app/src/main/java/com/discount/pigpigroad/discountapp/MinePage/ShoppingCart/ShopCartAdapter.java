package com.discount.pigpigroad.discountapp.MinePage.ShoppingCart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aitsuki.swipe.SwipeItemLayout;
import com.bumptech.glide.Glide;
import com.discount.pigpigroad.discountapp.R;
import com.discount.pigpigroad.discountapp.ShopDetailsActivity;

import java.util.List;

import static com.discount.pigpigroad.discountapp.Api.ApiConnect.PP_TIP;

/**
 * Created by PIGROAD on 2018/4/18.
 * Email:920015363@qq.com
 */

public class ShopCartAdapter extends RecyclerView.Adapter<ShopCartAdapter.MyViewHolder> {
    private ItemTouchListener mItemTouchListener;
    private Context context;
    private List<ShopCartBean.CartlistBean> data;
    private View headerView;
    private OnDeleteClickListener mOnDeleteClickListener;
    private OnEditClickListener mOnEditClickListener;
    private OnResfreshListener mOnResfreshListener;

    public ShopCartAdapter(Context context, List<ShopCartBean.CartlistBean> data, ItemTouchListener itemTouchListener) {
        this.mItemTouchListener = itemTouchListener;
        this.context = context;
        this.data = data;
    }

    @Override
    public ShopCartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_right_menu, parent, false);
        return new ShopCartAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShopCartAdapter.MyViewHolder holder, final int position) {

        Glide.with(context).load(data.get(position).getDefaultPic()).into(holder.ivShopCartClothPic);
        Glide.with(context).load(data.get(position).getShopPic()).into(holder.ivShopClothPic);
        if (position > 0) {
            if (data.get(position).getShopId() == data.get(position - 1).getShopId()) {
                holder.llShopCartHeader.setVisibility(View.GONE);
            } else {
                holder.llShopCartHeader.setVisibility(View.VISIBLE);
            }
        } else {
            holder.llShopCartHeader.setVisibility(View.VISIBLE);
        }

        holder.tvShopCartAttr.setText(data.get(position).getAttr());
        holder.tvShopCartClothName.setText(data.get(position).getProductName());
        holder.tvShopCartShopName.setText(data.get(position).getShopName());
        holder.tvShopCartClothPrice.setText("¥" + data.get(position).getPrice());
        holder.etShopCartClothNum.setText(data.get(position).getCount() + "");
        holder.mSwipeItemLayout.setSwipeEnable(getItemViewType(position) != Type.DISABLE_SWIPE_MENU);
        //侧滑操作
        if (mItemTouchListener != null) {
            if (holder.mRightMenu != null) {
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(350,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                params.setMargins(0, 0, 0, 0);
                params2.setMargins(0, 30, 0, 0);
                //判断是不是头部
                if (position > 0) {
                    if (data.get(position).getShopId() == data.get(position - 1).getShopId()) {
                        //不在头部
                        holder.mRightMenu.setLayoutParams(params);
                    } else {
                       //这里是在头部
                        holder.mRightMenu.setLayoutParams(params2);
                    }
                } else {
                    //单一一个在头部
                    holder.mRightMenu.setLayoutParams(params2);
                }
                holder.mRightMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mItemTouchListener.onRightMenuClick("删除" + holder.getAdapterPosition());
                        showDialog(view,position);
                        holder.mSwipeItemLayout.close();
                    }
                });
            }
        }

        if (mOnResfreshListener != null) {
            boolean isSelect = false;
            for (int i = 0; i < data.size(); i++) {
                if (!data.get(i).getIsSelect()) {
                    isSelect = false;
                    break;
                } else {
                    isSelect = true;
                }
            }
            mOnResfreshListener.onResfresh(isSelect);
        }

        holder.ivShopCartClothMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getCount() > 1) {
                    int count = data.get(position).getCount() - 1;
                    if (mOnEditClickListener != null) {
                        mOnEditClickListener.onEditClick(position, data.get(position).getId(), count);
                    }
                    data.get(position).setCount(count);
                    notifyDataSetChanged();
                }
            }
        });

        holder.ivShopCartClothAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = data.get(position).getCount() + 1;
                if (mOnEditClickListener != null) {
                    mOnEditClickListener.onEditClick(position, data.get(position).getId(), count);
                }
                data.get(position).setCount(count);
                notifyDataSetChanged();
            }
        });

        if (data.get(position).getIsSelect()) {
            holder.ivShopCartClothSel.setImageDrawable(context.getResources().getDrawable(R.drawable.shopcart_selected));
        } else {
            holder.ivShopCartClothSel.setImageDrawable(context.getResources().getDrawable(R.drawable.shopcart_unselected));
        }

        if (data.get(position).getIsShopSelect()) {
            holder.ivShopCartShopSel.setImageDrawable(context.getResources().getDrawable(R.drawable.shopcart_selected));
        } else {
            holder.ivShopCartShopSel.setImageDrawable(context.getResources().getDrawable(R.drawable.shopcart_unselected));
        }

        holder.ivShopCartClothSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setSelect(!data.get(position).getIsSelect());
                //通过循环找出不同商铺的第一个商品的位置
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getIsFirst() == 1) {
                        //遍历去找出同一家商铺的所有商品的勾选情况
                        for (int j = 0; j < data.size(); j++) {
                            //如果是同一家商铺的商品，并且其中一个商品是未选中，那么商铺的全选勾选取消
                            if (data.get(j).getShopId() == data.get(i).getShopId() && !data.get(j).getIsSelect()) {
                                data.get(i).setShopSelect(false);
                                break;
                            } else {
                                //如果是同一家商铺的商品，并且所有商品是选中，那么商铺的选中全选勾选
                                data.get(i).setShopSelect(true);
                            }
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });

        holder.ivShopCartShopSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getIsFirst() == 1) {
                    data.get(position).setShopSelect(!data.get(position).getIsShopSelect());
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getShopId() == data.get(position).getShopId()) {
                            data.get(i).setSelect(data.get(position).getIsShopSelect());
                        }
                    }
                    notifyDataSetChanged();
                }
            }
        });

        //查看商品详情
        holder.ivShopCartClothPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),ShopDetailsActivity.class);
                intent.putExtra("product_id", data.get(position).getId()+"");
                Log.i(PP_TIP,data.get(position).getId()+"");
                view.getContext().startActivity(intent);
            }
        });
    }

    private void showDialog(final View view, final int position) {
        //调用删除某个规格商品的接口
        if (mOnDeleteClickListener != null) {
            mOnDeleteClickListener.onDeleteClick(view, position, data.get(position).getId());
        }
        data.remove(position);
        //重新排序，标记所有商品不同商铺第一个的商品位置
        ShoppingCartActivity.isSelectFirst(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int count = (data == null ? 0 : data.size());
        if (headerView != null) {
            count++;
        }
        return count;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private SwipeItemLayout mSwipeItemLayout;
        private View mRightMenu;
        private ImageView ivShopCartShopSel;
        private TextView tvShopCartShopName;
        private TextView tvShopCartClothName;
        private TextView tvShopCartClothPrice;
        private TextView etShopCartClothNum;
        private TextView tvShopCartAttr;
        private ImageView ivShopCartClothSel;
        private ImageView ivShopCartClothMinus;
        private ImageView ivShopCartClothAdd;
        private ImageView ivShopClothPic;
        private ImageView ivShopCartClothPic;
        private LinearLayout llShopCartHeader;

        public MyViewHolder(View view) {
            super(view);
            llShopCartHeader = view.findViewById(R.id.ll_shopcart_header);
            ivShopCartShopSel = view.findViewById(R.id.iv_item_shopcart_shopselect);
            tvShopCartShopName = view.findViewById(R.id.tv_item_shopcart_shopname);
            tvShopCartClothName = view.findViewById(R.id.tv_item_shopcart_clothname);
            tvShopCartClothPrice = view.findViewById(R.id.tv_item_shopcart_cloth_price);
            etShopCartClothNum = view.findViewById(R.id.et_item_shopcart_cloth_num);
            tvShopCartAttr = view.findViewById(R.id.tv_item_shopcart_attr);
            ivShopCartClothSel = view.findViewById(R.id.tv_item_shopcart_clothselect);
            ivShopCartClothMinus = view.findViewById(R.id.iv_item_shopcart_cloth_minus);
            ivShopCartClothAdd = view.findViewById(R.id.iv_item_shopcart_cloth_add);
            ivShopCartClothPic = view.findViewById(R.id.iv_item_shopcart_cloth_pic);
            ivShopClothPic = view.findViewById(R.id.iv_item_shop_cloth_pic);
            mRightMenu = view.findViewById(R.id.right_menu);
            mSwipeItemLayout = view.findViewById(R.id.swipe_layout);
        }
    }


    public View getHeaderView() {
        return headerView;
    }

    private ShopCartAdapter.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(ShopCartAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(View view, int position, int cartid);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener mOnDeleteClickListener) {
        this.mOnDeleteClickListener = mOnDeleteClickListener;
    }

    public interface OnEditClickListener {
        void onEditClick(int position, int cartid, int count);
    }

    public void setOnEditClickListener(OnEditClickListener mOnEditClickListener) {
        this.mOnEditClickListener = mOnEditClickListener;
    }

    public interface OnResfreshListener {
        void onResfresh(boolean isSelect);
    }

    public void setResfreshListener(OnResfreshListener mOnResfreshListener) {
        this.mOnResfreshListener = mOnResfreshListener;
    }

}

