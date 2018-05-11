package com.discount.pigpigroad.discountapp.MinePage.MyOrder.OrderBean;

import java.util.List;

/**
 * Created by PIGROAD on 2018/5/4.
 * Email:920015363@qq.com
 */

public class OrderSummary {
    private int id;
    private String orderCode;
    private String addtime;
    private double totalPrice;
    private String status;
    private String createTime;
    private String payTime;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    private String orderid;


    private List<OrderGoodsItem> orderDetailList;
    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public List<OrderGoodsItem> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderGoodsItem> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}
