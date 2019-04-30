package com.bt.smart.truck_broker.messageInfo;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/18 10:21
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class DrivierOrderInfo {

    /**
     * message : 成功
     * data : [{"id":"2c9084dd683aff1c01683b3e4aaa0005","fstatus":"0","fh_name":"发货人张三","fh_telephone":"18234567890","fh_address":"发货人地址南通","sh_name":"收货人李四","sh_telephone":"18234567898","sh_address":"收货人地址上海","sh_area":"收货人地区上海","car_type":"0","zh_time":"2019-01-11","goods_name":"货物类别服装","fh":"1236","sh":"1235","fcheck":"0","fmain_id":"yingsu0033","fsub_id":"","is_fapiao":"0","fweight":1,"ffee":1,"origin":"江苏省南通市港闸区","destination":"江苏省南通市崇川区","origin_province_id":"11","origin_city_id":"114","origin_area_id":"1236","destination_province_id":"11","destination_city_id":"114","destination_area_id":"1235"},{"id":"2c9084dd683a662c01683aca5e840010","fstatus":"0","fh_name":"发货人张三","fh_telephone":"18234567890","fh_address":"发货人地址南通","sh_name":"收货人李四","sh_telephone":"18234567898","sh_address":"收货人地址上海","sh_area":"收货人地区上海","car_type":"0","zh_time":"2019-01-11","goods_name":"货物类别服装","fh":"1236","sh":"1235","fcheck":"0","fmain_id":"yingsu0032","fsub_id":"","is_fapiao":"0","fweight":1,"ffee":1,"origin":"江苏省南通市港闸区","destination":"江苏省南通市崇川区","origin_province_id":"11","origin_city_id":"114","origin_area_id":"1236","destination_province_id":"11","destination_city_id":"114","destination_area_id":"1235"}]
     * ok : true
     * respCode : 0
     */

    private String message;
    private boolean        ok;
    private String         respCode;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2c9084dd683aff1c01683b3e4aaa0005
         * fstatus : 0
         * fh_name : 发货人张三
         * fh_telephone : 18234567890
         * fh_address : 发货人地址南通
         * sh_name : 收货人李四
         * sh_telephone : 18234567898
         * sh_address : 收货人地址上海
         * sh_area : 收货人地区上海
         * car_type : 0
         * zh_time : 2019-01-11
         * goods_name : 货物类别服装
         * fh : 1236
         * sh : 1235
         * fcheck : 0
         * fmain_id : yingsu0033
         * fsub_id :
         * is_fapiao : 0
         * fweight : 1
         * ffee : 1
         * origin : 江苏省南通市港闸区
         * destination : 江苏省南通市崇川区
         * origin_province_id : 11
         * origin_city_id : 114
         * origin_area_id : 1236
         * destination_province_id : 11
         * destination_city_id : 114
         * destination_area_id : 1235
         */

        private String id;
        private String fstatus;
        private String fh_name;
        private String fh_telephone;
        private String fh_address;
        private String sh_name;
        private String sh_telephone;
        private String sh_address;
        private String sh_area;
        private String car_type;
        private String zh_time;
        private String goods_name;
        private String fh;
        private String sh;
        private String fcheck;
        private String fmain_id;
        private String fsub_id;
        private String is_fapiao;
        private int    fweight;
        private int    ffee;
        private String origin;
        private String destination;
        private String origin_province_id;
        private String origin_city_id;
        private String origin_area_id;
        private String destination_province_id;
        private String destination_city_id;
        private String destination_area_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFstatus() {
            return fstatus;
        }

        public void setFstatus(String fstatus) {
            this.fstatus = fstatus;
        }

        public String getFh_name() {
            return fh_name;
        }

        public void setFh_name(String fh_name) {
            this.fh_name = fh_name;
        }

        public String getFh_telephone() {
            return fh_telephone;
        }

        public void setFh_telephone(String fh_telephone) {
            this.fh_telephone = fh_telephone;
        }

        public String getFh_address() {
            return fh_address;
        }

        public void setFh_address(String fh_address) {
            this.fh_address = fh_address;
        }

        public String getSh_name() {
            return sh_name;
        }

        public void setSh_name(String sh_name) {
            this.sh_name = sh_name;
        }

        public String getSh_telephone() {
            return sh_telephone;
        }

        public void setSh_telephone(String sh_telephone) {
            this.sh_telephone = sh_telephone;
        }

        public String getSh_address() {
            return sh_address;
        }

        public void setSh_address(String sh_address) {
            this.sh_address = sh_address;
        }

        public String getSh_area() {
            return sh_area;
        }

        public void setSh_area(String sh_area) {
            this.sh_area = sh_area;
        }

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
        }

        public String getZh_time() {
            return zh_time;
        }

        public void setZh_time(String zh_time) {
            this.zh_time = zh_time;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getFh() {
            return fh;
        }

        public void setFh(String fh) {
            this.fh = fh;
        }

        public String getSh() {
            return sh;
        }

        public void setSh(String sh) {
            this.sh = sh;
        }

        public String getFcheck() {
            return fcheck;
        }

        public void setFcheck(String fcheck) {
            this.fcheck = fcheck;
        }

        public String getFmain_id() {
            return fmain_id;
        }

        public void setFmain_id(String fmain_id) {
            this.fmain_id = fmain_id;
        }

        public String getFsub_id() {
            return fsub_id;
        }

        public void setFsub_id(String fsub_id) {
            this.fsub_id = fsub_id;
        }

        public String getIs_fapiao() {
            return is_fapiao;
        }

        public void setIs_fapiao(String is_fapiao) {
            this.is_fapiao = is_fapiao;
        }

        public int getFweight() {
            return fweight;
        }

        public void setFweight(int fweight) {
            this.fweight = fweight;
        }

        public int getFfee() {
            return ffee;
        }

        public void setFfee(int ffee) {
            this.ffee = ffee;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getOrigin_province_id() {
            return origin_province_id;
        }

        public void setOrigin_province_id(String origin_province_id) {
            this.origin_province_id = origin_province_id;
        }

        public String getOrigin_city_id() {
            return origin_city_id;
        }

        public void setOrigin_city_id(String origin_city_id) {
            this.origin_city_id = origin_city_id;
        }

        public String getOrigin_area_id() {
            return origin_area_id;
        }

        public void setOrigin_area_id(String origin_area_id) {
            this.origin_area_id = origin_area_id;
        }

        public String getDestination_province_id() {
            return destination_province_id;
        }

        public void setDestination_province_id(String destination_province_id) {
            this.destination_province_id = destination_province_id;
        }

        public String getDestination_city_id() {
            return destination_city_id;
        }

        public void setDestination_city_id(String destination_city_id) {
            this.destination_city_id = destination_city_id;
        }

        public String getDestination_area_id() {
            return destination_area_id;
        }

        public void setDestination_area_id(String destination_area_id) {
            this.destination_area_id = destination_area_id;
        }
    }
}
