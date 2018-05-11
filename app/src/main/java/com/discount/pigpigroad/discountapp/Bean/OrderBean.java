package com.discount.pigpigroad.discountapp.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by PIGROAD on 2018/5/3.
 * Email:920015363@qq.com
 */

public class OrderBean  implements Serializable {
    private List<orderBean> data;

    public List<orderBean> getData() {
        return data;
    }

    public void setData(List<orderBean> data) {
        this.data = data;
    }

    public static class orderBean {
        /**
         * "product_name": "手机9"
         *  "addtime": "2018-04-29 10:47:08"
         *  "product_price": "4.00"
         *  "product_img": "http://img10.360buyimg.com/n1/s450x450_jfs/t7297/154/3413903491/65679/45ae4902/59e42830N9da56c41.jpg"
         *  "orderid": "2018042905241"
         *  "order_status": "1"
         */
        private String product_name;
        private String addtime;
        private String product_price;
        private String product_img;
        private String id;
        private String order_status;

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProduct_price() {
            return product_price;
        }

        public void setProduct_price(String product_price) {
            this.product_price = product_price;
        }

        public String getProduct_img() {
            return product_img;
        }

        public void setProduct_img(String product_img) {
            this.product_img = product_img;
        }
    }
}
