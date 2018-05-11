package com.discount.pigpigroad.discountapp.MinePage.Address;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.discount.pigpigroad.discountapp.Bean.AddressBean;
import com.discount.pigpigroad.discountapp.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.URL_BASE;
import static com.discount.pigpigroad.discountapp.Api.ApiConnect.USER_OVERDUE;

/**
 * Created by PIGROAD on 2018/4/27.
 * Email:920015363@qq.com
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {
    private String login_token;
    private Context context;
    private Dialog dialog;
    private List<AddressBean.DataBeanX.addressBean> data;
    private int defItem = -1;
    private OnItemListener onItemListener;

    public AddressAdapter(Context context,List<AddressBean.DataBeanX.addressBean> data){
        this.context=context;
        this.data=data;
    }
    @Override
    public AddressAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.address_listitem,parent,false);
        //获取token
        SharedPreferences sp = context.getSharedPreferences("User", MODE_PRIVATE);
        login_token = sp.getString("user_token", null);
        return new AddressAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AddressAdapter.MyViewHolder holder, final int position) {
            holder.tv_user_name.setText(data.get(position).getShop_name());
            holder.tv_user_phone.setText(data.get(position).getShop_phone());
            String location=data.get(position).getAddress_location()+" "+data.get(position).getAddress_details();
            holder.tv_user_location.setText(location);
            String select=data.get(position).getDefault_select();
            if (select.equals("1")){
                holder.iv_choose.setBackgroundResource(R.drawable.address_tag3);
            }else if (select.equals("0")){
                holder.iv_choose.setBackgroundResource(R.drawable.address_tag6);
            }

            //编辑按钮
            holder.ll_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(view.getContext(),EditAddressActivity.class);
                    intent.putExtra("user_name",holder.tv_user_name.getText());
                    intent.putExtra("user_phone",holder.tv_user_phone.getText());
                    intent.putExtra("user_location",data.get(position).getAddress_location());
                    intent.putExtra("user_details",data.get(position).getAddress_details());
                    intent.putExtra("select",data.get(position).getDefault_select());
                    intent.putExtra("id",data.get(position).getId());
                    intent.putExtra("provice_name",data.get(position).getProvice_name());
                    intent.putExtra("area_name",data.get(position).getArea_name());
                    intent.putExtra("city_name",data.get(position).getCity_name());
                    view.getContext().startActivity(intent);
                }
            });

            //删除按钮
            holder.ll_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                            dialog(data.get(position).getId(),position);
                }
            });

        if (defItem != -1) {
            if (defItem == position) {
                holder.iv_choose.setBackgroundResource(R.drawable.address_tag3);
            }else {
                holder.iv_choose.setBackgroundResource(R.drawable.address_tag6);
            }
            }
        }

    @Override
    public int getItemCount() {
        return data.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_user_name,tv_user_phone,tv_user_location;
        private ImageView iv_choose;
        private LinearLayout ll_edit,ll_delete;
         public MyViewHolder(View itemView) {
             super(itemView);
             tv_user_name=itemView.findViewById(R.id.tv_user_name);
             tv_user_phone=itemView.findViewById(R.id.tv_user_phone);
             tv_user_location=itemView.findViewById(R.id.tv_user_location);
             iv_choose=itemView.findViewById(R.id.iv_choose);
             ll_edit=itemView.findViewById(R.id.ll_edit);
             ll_delete=itemView.findViewById(R.id.ll_delete);
             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if (onItemListener != null) {
                         onItemListener.onClick(view,getLayoutPosition());
                     }
                 }
             });
         }

     }
    /**
     *  删除弹窗
     */
    public void dialog(final String id, final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.exit_dialog, null);
        dialog = builder.create();
        dialog.show();
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        TextView tv_edit=view.findViewById(R.id.exit_text);
        tv_edit.setText("确认删除此收货地址？");
        Button btn_cancle=view.findViewById(R.id.btn_cancel);
        Button btn_confirm=view.findViewById(R.id.btn_exit);
        //取消按钮
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //确定按钮
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteaddress(id,position);
            }
        });
    }
    /**
     * 删除收获地址操作
     */
    public void deleteaddress(String id, final int position){
        OkGo.<String>post(URL_BASE + "?request=private.address.del.my.address.action&platform=android&token=" + login_token
                                    +"&default_select=1&id="+id
        )
                .tag(this)
                .headers("header1", "headerValue1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String responseStr = response.body();//这个就是返回来的结果
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            int code =jsonObject.getInt("code");
                            String msg=jsonObject.getString("msg");
                            if (code ==0){
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                data.remove(position);
                                notifyDataSetChanged();
                            }else if (code==1){
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
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
    /**
     * 设置设为地址的默认项
     */
    public void setDefSelect(int position) {
        this.defItem = position;
        notifyDataSetChanged();
    }
    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }
    public interface OnItemListener {
        void onClick(View v, int pos);
    }
}
