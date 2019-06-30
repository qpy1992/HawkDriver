package com.bt.smart.truck_broker.messageInfo;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/9 18:53
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class OrderDetailInfo {

    /**
     * message : 成功
     * size : 0
     * data : {"id":"2c90b4e368eede110168eedf279a0000","fstatus":"0","fh_name":"赵三","fh_telephone":"18234567890","fh_address":"南通","sh_name":"李四","sh_telephone":"18234567890","sh_address":"上海","sh_area":"","car_type":"2c90b4e368db82ef0168db8d8e9a0004","zh_time":{"date":15,"hours":9,"seconds":34,"month":1,"timezoneOffset":-480,"year":119,"minutes":56,"time":1550195794000,"day":5},"goods_name":"4d2881f66850132a01685014c6e40007","fh":"2392","sh":"3262","fcheck":"0","fmain_id":"yingsu0028","fsub_id":"","is_fapiao":"0","fweight":34,"ffee":1234,"is_appoint":"0","appoint_id":"","order_no":"2019021500609093396","fmax_fee":9456,"is_box":"0","box_no":"1","origin":"广东省惠州市惠阳区","destination":"甘肃省嘉峪关市市辖区","origin_province_id":"20","origin_city_id":"242","origin_area_id":"2392","destination_province_id":"29","destination_city_id":"337","destination_area_id":"3262","ordergoods":[{"id":"2c90b4e368eede110168eedf27bd0001","goodsName":"上装","goodsSpace":11,"goodsWeight":12,"orderId":"2c90b4e368eede110168eedf279a0000"},{"id":"2c90b4e368eede110168eedf27bd0002","goodsName":"下装","goodsSpace":21,"goodsWeight":22,"orderId":"2c90b4e368eede110168eedf279a0000"}]}
     * respCode : 0
     * ok : true
     */

    private String message;
    private int      size;
    private DataBean data;
    private String   respCode;
    private boolean  ok;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * id : 2c90b4e368eede110168eedf279a0000
         * fstatus : 0
         * fh_name : 赵三
         * fh_telephone : 18234567890
         * fh_address : 南通
         * sh_name : 李四
         * sh_telephone : 18234567890
         * sh_address : 上海
         * sh_area :
         * car_type : 2c90b4e368db82ef0168db8d8e9a0004
         * zh_time : {"date":15,"hours":9,"seconds":34,"month":1,"timezoneOffset":-480,"year":119,"minutes":56,"time":1550195794000,"day":5}
         * goods_name : 4d2881f66850132a01685014c6e40007
         * fh : 2392
         * sh : 3262
         * fcheck : 0
         * fmain_id : yingsu0028
         * fsub_id :
         * is_fapiao : 0
         * fweight : 34
         * ffee : 1234
         * is_appoint : 0
         * appoint_id :
         * order_no : 2019021500609093396
         * fmax_fee : 9456
         * is_box : 0
         * box_no : 1
         * origin : 广东省惠州市惠阳区
         * destination : 甘肃省嘉峪关市市辖区
         * origin_province_id : 20
         * origin_city_id : 242
         * origin_area_id : 2392
         * destination_province_id : 29
         * destination_city_id : 337
         * destination_area_id : 3262
         * ordergoods : [{"id":"2c90b4e368eede110168eedf27bd0001","goodsName":"上装","goodsSpace":11,"goodsWeight":12,"orderId":"2c90b4e368eede110168eedf279a0000"},{"id":"2c90b4e368eede110168eedf27bd0002","goodsName":"下装","goodsSpace":21,"goodsWeight":22,"orderId":"2c90b4e368eede110168eedf279a0000"}]
         */

        private String id;
        private String               fstatus;
        private String               fh_name;
        private String               fh_telephone;
        private String               fh_address;
        private String               sh_name;
        private String               sh_telephone;
        private String               sh_address;
        private String               sh_area;
        private String               car_type;
        private ZhTimeBean           zh_time;
        private String               goods_name;
        private String               fh;
        private String               sh;
        private String               fcheck;
        private String               fmain_id;
        private String               fsub_id;
        private String               is_fapiao;
        private int                  fweight;
        private int                  ffee;
        private String               is_appoint;
        private String               appoint_id;
        private String               order_no;
        private int                  fmax_fee;
        private String               is_box;
        private String               box_no;
        private String               origin;
        private String               destination;
        private String               origin_province_id;
        private String               origin_city_id;
        private String               origin_area_id;
        private String               destination_province_id;
        private String               destination_city_id;
        private String               destination_area_id;
        private String               floadpics;
        private String               floadtime;
        private String               frecepics;
        private String               frecetime;
        private String               frece;
        private String               time_interval;
        private String               goodsname;
        private String               cartype;
        private String               companyname;
        private List<OrdergoodsBean> ordergoods;


        public String getCompanyname() {
            return companyname;
        }

        public void setCompanyname(String companyname) {
            this.companyname = companyname;
        }

        public String getGoodsname() {
            return goodsname;
        }

        public void setGoodsname(String goodsname) {
            this.goodsname = goodsname;
        }

        public String getCartype() {
            return cartype;
        }

        public void setCartype(String cartype) {
            this.cartype = cartype;
        }

        public String getTime_interval() {
            return time_interval;
        }

        public void setTime_interval(String time_interval) {
            this.time_interval = time_interval;
        }

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

        public ZhTimeBean getZh_time() {
            return zh_time;
        }

        public void setZh_time(ZhTimeBean zh_time) {
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

        public int getFmax_fee() {
            return fmax_fee;
        }

        public void setFmax_fee(int fmax_fee) {
            this.fmax_fee = fmax_fee;
        }

        public String getIs_box() {
            return is_box;
        }

        public void setIs_box(String is_box) {
            this.is_box = is_box;
        }

        public String getBox_no() {
            return box_no;
        }

        public void setBox_no(String box_no) {
            this.box_no = box_no;
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

        public List<OrdergoodsBean> getOrdergoods() {
            return ordergoods;
        }

        public void setOrdergoods(List<OrdergoodsBean> ordergoods) {
            this.ordergoods = ordergoods;
        }

        public static class ZhTimeBean {
            /**
             * date : 15
             * hours : 9
             * seconds : 34
             * month : 1
             * timezoneOffset : -480
             * year : 119
             * minutes : 56
             * time : 1550195794000
             * day : 5
             */

            private int date;
            private int  hours;
            private int  seconds;
            private int  month;
            private int  timezoneOffset;
            private int  year;
            private int  minutes;
            private long time;
            private int  day;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }
        }

        public static class OrdergoodsBean {
            /**
             * id : 2c90b4e368eede110168eedf27bd0001
             * goodsName : 上装
             * goodsSpace : 11
             * goodsWeight : 12
             * orderId : 2c90b4e368eede110168eedf279a0000
             */

            private String id;
            private String goodsName;
            private int    goodsSpace;
            private int    goodsWeight;
            private String orderId;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public int getGoodsSpace() {
                return goodsSpace;
            }

            public void setGoodsSpace(int goodsSpace) {
                this.goodsSpace = goodsSpace;
            }

            public int getGoodsWeight() {
                return goodsWeight;
            }

            public void setGoodsWeight(int goodsWeight) {
                this.goodsWeight = goodsWeight;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }
        }
    }
}
