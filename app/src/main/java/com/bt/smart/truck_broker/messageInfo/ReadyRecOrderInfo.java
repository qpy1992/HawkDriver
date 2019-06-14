package com.bt.smart.truck_broker.messageInfo;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/23 10:16
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class ReadyRecOrderInfo {

    /**
     * message : 成功
     * size : 3
     * data : [{"id":"2c9084dd689ca18101689d3cd92c0011","fstatus":"1","fh_name":"大季","fh_telephone":"15788888888","fh_address":"还好还好街道","sh_name":"小季","sh_telephone":"15900000000","sh_address":"dhjaljl路","sh_area":"","car_type":"1","zh_time":"2019-02-01","goods_name":"粮食","fh":"1596","sh":"2360","fcheck":"0","fmain_id":"yingsu0040","fsub_id":"","is_fapiao":"1","fweight":50,"ffee":2592.34,"is_appoint":"0","appoint_id":"","order_no":"2019013000294992309","origin":"福建省南平市建阳市","destination":"广东省江门市台山市","origin_province_id":"14","origin_city_id":"156","origin_area_id":"1596","destination_province_id":"20","destination_city_id":"238","destination_area_id":"2360"},{"id":"2c9084dd68977697016898948bcd0018","fstatus":"1","fh_name":"理一","fh_telephone":"18723456567","fh_address":"111","sh_name":"利于","sh_telephone":"18723456567","sh_address":"2222","sh_area":"","car_type":"2","zh_time":"2019-01-29 15:48:15","goods_name":"水果","fh":"1447","sh":"390","fcheck":"0","fmain_id":"yingsu0028","fsub_id":"","is_fapiao":"0","fweight":1,"ffee":29.39,"is_appoint":"0","appoint_id":"","order_no":"2019012901697155618","origin":"安徽省安庆市大观区","destination":"北京市市辖区昌平区","origin_province_id":"13","origin_city_id":"140","origin_area_id":"1447","destination_province_id":"2","destination_city_id":"33","destination_area_id":"390"},{"id":"2c90b4e36882e87c016883781fb","fstatus":"1","fh_name":"赵一","fh_telephone":"18234567890","fh_address":"南通","sh_name":"李四","sh_telephone":"18234567898","sh_address":"上海","sh_area":"","car_type":"2","zh_time":"2019-01-25","goods_name":"服装","fh":"1447","sh":"390","fcheck":"0","fmain_id":"yingsu0028","fsub_id":"","is_fapiao":"0","fweight":34,"ffee":999.34,"is_appoint":"0","appoint_id":"","order_no":"2019012501245971363","origin":"安徽省安庆市大观区","destination":"北京市市辖区昌平区","origin_province_id":"13","origin_city_id":"140","origin_area_id":"1447","destination_province_id":"2","destination_city_id":"33","destination_area_id":"390"}]
     * respCode : 0
     * ok : true
     */

    private String message;
    private int            size;
    private String         respCode;
    private boolean        ok;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2c9084dd689ca18101689d3cd92c0011
         * fstatus : 1
         * fh_name : 大季
         * fh_telephone : 15788888888
         * fh_address : 还好还好街道
         * sh_name : 小季
         * sh_telephone : 15900000000
         * sh_address : dhjaljl路
         * sh_area :
         * car_type : 1
         * zh_time : 2019-02-01
         * goods_name : 粮食
         * fh : 1596
         * sh : 2360
         * fcheck : 0
         * fmain_id : yingsu0040
         * fsub_id :
         * is_fapiao : 1
         * fweight : 50.0
         * ffee : 2592.34
         * is_appoint : 0
         * appoint_id :
         * order_no : 2019013000294992309
         * origin : 福建省南平市建阳市
         * destination : 广东省江门市台山市
         * origin_province_id : 14
         * origin_city_id : 156
         * origin_area_id : 1596
         * destination_province_id : 20
         * destination_city_id : 238
         * destination_area_id : 2360
         * floadpics:
         * floadtime:
         * frecepics:
         * frecetime:
         * frece:
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
        private double fweight;
        private double ffee;
        private String is_appoint;
        private String appoint_id;
        private String order_no;
        private String origin;
        private String destination;
        private String origin_province_id;
        private String origin_city_id;
        private String origin_area_id;
        private String destination_province_id;
        private String destination_city_id;
        private String destination_area_id;
        private String floadpics;
        private String floadtime;
        private String frecepics;
        private String frecetime;
        private String frece;
        private String time_interval;

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

        public double getFweight() {
            return fweight;
        }

        public void setFweight(double fweight) {
            this.fweight = fweight;
        }

        public double getFfee() {
            return ffee;
        }

        public void setFfee(double ffee) {
            this.ffee = ffee;
        }

        public String getIs_appoint() {
            return is_appoint;
        }

        public void setIs_appoint(String is_appoint) {
            this.is_appoint = is_appoint;
        }

        public String getAppoint_id() {
            return appoint_id;
        }

        public void setAppoint_id(String appoint_id) {
            this.appoint_id = appoint_id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
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

        public String getFloadpics() {
            return floadpics;
        }

        public void setFloadpics(String floadpics) {
            this.floadpics = floadpics;
        }

        public String getFloadtime() {
            return floadtime;
        }

        public void setFloadtime(String floadtime) {
            this.floadtime = floadtime;
        }

        public String getFrecepics() {
            return frecepics;
        }

        public void setFrecepics(String frecepics) {
            this.frecepics = frecepics;
        }

        public String getFrecetime() {
            return frecetime;
        }

        public void setFrecetime(String frecetime) {
            this.frecetime = frecetime;
        }

        public String getFrece() {
            return frece;
        }

        public void setFrece(String frece) {
            this.frece = frece;
        }

        public String getTime_interval() {
            return time_interval;
        }

        public void setTime_interval(String time_interval) {
            this.time_interval = time_interval;
        }
    }
}
