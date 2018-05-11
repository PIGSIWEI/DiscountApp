package com.discount.pigpigroad.discountapp.MinePage.MyOrder.OrderBean;

/**
 * Created by PIGROAD on 2018/5/4.
 * Email:920015363@qq.com
 */

public class OrderGoodsItem {

    private String productName;
    private String productPic;
    private int count;
    private double totalPrice;
    private int orderid;
    private String productPrice;
    private String pt_order_auto_id;
    private String attr_name;

    public String getAttr_name() {
        return attr_name;
    }

    public void setAttr_name(String attr_name) {
        this.attr_name = attr_name;
    }

    public String getPt_order_auto_id() {
        return pt_order_auto_id;
    }

    public void setPt_order_auto_id(String pt_order_auto_id) {
        this.pt_order_auto_id = pt_order_auto_id;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    private GoodsOrderInfo order;

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public GoodsOrderInfo getOrder() {
        return order;
    }

    public void setOrder(GoodsOrderInfo order) {
        this.order = order;
    }
}
