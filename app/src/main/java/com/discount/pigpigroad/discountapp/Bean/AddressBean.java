package com.discount.pigpigroad.discountapp.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by PIGROAD on 2018/4/27.
 * Email:920015363@qq.com
 */

public class AddressBean implements Serializable {

    /**
     * code : 0
     * data : {"data":[{"id":"109","userid":"1","shop_name":"收货人","shop_phone":"18665716953","provice_name":"广东省","city_name":"广州市","area_name":"白云区","address_details":"广园新村公交站","addtime":"2018-04-11 19:36:39","default_select":"1","address_location":"广东省 广州市 白云区","last_upd_date":"2018-04-11 19:36:39"},{"id":"42","userid":"1","shop_name":"黄玉钧","shop_phone":"18665716953","provice_name":"","city_name":"","area_name":"","address_details":"啦啦啦咯你","addtime":"2017-05-06 11:44:54","default_select":"0","address_location":"广东省 广州市 白云区","last_upd_date":"0000-00-00 00:00:00"},{"id":"39","userid":"1","shop_name":"谢雪明","shop_phone":"13250797821","provice_name":"","city_name":"","area_name":"","address_details":"107国道三华段1号绿苑熙园","addtime":"2017-05-04 21:42:54","default_select":"0","address_location":"广东省 广州市 花都区","last_upd_date":"0000-00-00 00:00:00"},{"id":"37","userid":"1","shop_name":"曹亚鹏","shop_phone":"13268326113","provice_name":"","city_name":"","area_name":"","address_details":"广元新村联名商贸中心3105","addtime":"2017-05-02 10:06:37","default_select":"0","address_location":"广东省 广州市 白云区","last_upd_date":"0000-00-00 00:00:00"}],"total":4,"curpage":1,"page_size":24,"canshu":""}
     */

    private int code;
    private DataBeanX data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * data : [{"id":"109","userid":"1","shop_name":"收货人","shop_phone":"18665716953","provice_name":"广东省","city_name":"广州市","area_name":"白云区","address_details":"广园新村公交站","addtime":"2018-04-11 19:36:39","default_select":"1","address_location":"广东省 广州市 白云区","last_upd_date":"2018-04-11 19:36:39"},{"id":"42","userid":"1","shop_name":"黄玉钧","shop_phone":"18665716953","provice_name":"","city_name":"","area_name":"","address_details":"啦啦啦咯你","addtime":"2017-05-06 11:44:54","default_select":"0","address_location":"广东省 广州市 白云区","last_upd_date":"0000-00-00 00:00:00"},{"id":"39","userid":"1","shop_name":"谢雪明","shop_phone":"13250797821","provice_name":"","city_name":"","area_name":"","address_details":"107国道三华段1号绿苑熙园","addtime":"2017-05-04 21:42:54","default_select":"0","address_location":"广东省 广州市 花都区","last_upd_date":"0000-00-00 00:00:00"},{"id":"37","userid":"1","shop_name":"曹亚鹏","shop_phone":"13268326113","provice_name":"","city_name":"","area_name":"","address_details":"广元新村联名商贸中心3105","addtime":"2017-05-02 10:06:37","default_select":"0","address_location":"广东省 广州市 白云区","last_upd_date":"0000-00-00 00:00:00"}]
         * total : 4
         * curpage : 1
         * page_size : 24
         * canshu :
         */

        private List<addressBean> data;

        public List<addressBean> getData() {
            return data;
        }

        public void setData(List<addressBean> data) {
            this.data = data;
        }

        public static class addressBean {
            /**
             * id : 109
             * userid : 1
             * shop_name : 收货人
             * shop_phone : 18665716953
             * provice_name : 广东省
             * city_name : 广州市
             * area_name : 白云区
             * address_details : 广园新村公交站
             * addtime : 2018-04-11 19:36:39
             * default_select : 1
             * address_location : 广东省 广州市 白云区
             * last_upd_date : 2018-04-11 19:36:39
             */

            private String id;
            private String userid;
            private String shop_name;
            private String shop_phone;
            private String provice_name;
            private String city_name;
            private String area_name;
            private String address_details;
            private String addtime;
            private String default_select;
            private String address_location;
            private String last_upd_date;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public String getShop_phone() {
                return shop_phone;
            }

            public void setShop_phone(String shop_phone) {
                this.shop_phone = shop_phone;
            }

            public String getProvice_name() {
                return provice_name;
            }

            public void setProvice_name(String provice_name) {
                this.provice_name = provice_name;
            }

            public String getCity_name() {
                return city_name;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }

            public String getAddress_details() {
                return address_details;
            }

            public void setAddress_details(String address_details) {
                this.address_details = address_details;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getDefault_select() {
                return default_select;
            }

            public void setDefault_select(String default_select) {
                this.default_select = default_select;
            }

            public String getAddress_location() {
                return address_location;
            }

            public void setAddress_location(String address_location) {
                this.address_location = address_location;
            }

            public String getLast_upd_date() {
                return last_upd_date;
            }

            public void setLast_upd_date(String last_upd_date) {
                this.last_upd_date = last_upd_date;
            }
        }
    }
}
