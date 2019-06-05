package com.bt.smart.truck_broker.messageInfo;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/9 16:19
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class AllOrderListInfo {
    /**
     * code : 1
     * pageSize : 1
     * pageList : [{"fhName":"赵三","fh":"1447","orderGoodsList":[{"orderId":"2c90b4e368841e4a0168842f855a0001","id":"2c90b4e368841e4a0168842f855b0002","goodsName":"上装","goodsSpace":11,"goodsWeight":12},{"orderId":"2c90b4e368841e4a0168842f855a0001","id":"2c90b4e368841e4a0168842f855b0003","goodsName":"下装","goodsSpace":21,"goodsWeight":22}],"orderNo":"2019012501593707107","isFapiao":"0","zhTime":{"date":25,"hours":16,"seconds":20,"month":0,"nanos":0,"timezoneOffset":-480,"year":119,"minutes":45,"time":1548405920000,"day":5},"appointId":"","fsubId":"","ffee":1026.62,"fcheck":"0","fstatus":"0","fweight":34,"carType":"2","fhTelephone":"18234567890","shArea":"","sh":"2599","shAddress":"上海","id":"2c90b4e368841e4a0168842f855a0001","shName":"李四","fhAddress":"南通","shTelephone":"18234567898","goodsName":"服装","isAppoint":"0","fmainId":"yingsu0028"}]
     * message : 查询成功！
     */

    private String respCode;
    private int                size;
    private String             message;
    private List<PageListBean> data;
    Boolean ok;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PageListBean> getData() {
        return data;
    }

    public void setData(List<PageListBean> data) {
        this.data = data;
    }

    public static class PageListBean {
        /**
         * fhName : 赵三
         * fh : 1447
         * orderGoodsList : [{"orderId":"2c90b4e368841e4a0168842f855a0001","id":"2c90b4e368841e4a0168842f855b0002","goodsName":"上装","goodsSpace":11,"goodsWeight":12},{"orderId":"2c90b4e368841e4a0168842f855a0001","id":"2c90b4e368841e4a0168842f855b0003","goodsName":"下装","goodsSpace":21,"goodsWeight":22}]
         * orderNo : 2019012501593707107
         * isFapiao : 0
         * zhTime : {"date":25,"hours":16,"seconds":20,"month":0,"nanos":0,"timezoneOffset":-480,"year":119,"minutes":45,"time":1548405920000,"day":5}
         * appointId :
         * fsubId :
         * ffee : 1026.62
         * fcheck : 0
         * fstatus : 0
         * fweight : 34
         * carType : 2
         * fhTelephone : 18234567890
         * shArea :
         * sh : 2599
         * shAddress : 上海
         * id : 2c90b4e368841e4a0168842f855a0001
         * shName : 李四
         * fhAddress : 南通
         * shTelephone : 18234567898
         * goodsName : 服装
         * isAppoint : 0
         * fmainId : yingsu0028
         */

        private String fhName;
        private String                   fh;
        private String                   orderNo;
        private String                   isFapiao;
        private String               zhTime;
        private String                   appointId;
        private String                   fsubId;
        private double                   ffee;
        private String                   fcheck;
        private String                   fstatus;
        private int                      fweight;
        private String                   carType;
        private String                   fhTelephone;
        private String                   shArea;
        private String                   sh;
        private String                   shAddress;
        private String                   id;
        private String                   shName;
        private String                   fhAddress;
        private String                   shTelephone;
        private String                   goodsName;
        private String                   isAppoint;
        private String                   fmainId;
        private List<OrderGoodsListBean> orderGoodsList;
        /**
         * zh_time : 2019-01-30 10:30:20
         */

        private String                   zh_time;

        public String getFhName() {
            return fhName;
        }

        public void setFhName(String fhName) {
            this.fhName = fhName;
        }

        public String getFh() {
            return fh;
        }

        public void setFh(String fh) {
            this.fh = fh;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getIsFapiao() {
            return isFapiao;
        }

        public void setIsFapiao(String isFapiao) {
            this.isFapiao = isFapiao;
        }

        public String getZhTime() {
            return zhTime;
        }

        public void setZhTime(String zhTime) {
            this.zhTime = zhTime;
        }

        public String getAppointId() {
            return appointId;
        }

        public void setAppointId(String appointId) {
            this.appointId = appointId;
        }

        public String getFsubId() {
            return fsubId;
        }

        public void setFsubId(String fsubId) {
            this.fsubId = fsubId;
        }

        public double getFfee() {
            return ffee;
        }

        public void setFfee(double ffee) {
            this.ffee = ffee;
        }

        public String getFcheck() {
            return fcheck;
        }

        public void setFcheck(String fcheck) {
            this.fcheck = fcheck;
        }

        public String getFstatus() {
            return fstatus;
        }

        public void setFstatus(String fstatus) {
            this.fstatus = fstatus;
        }

        public int getFweight() {
            return fweight;
        }

        public void setFweight(int fweight) {
            this.fweight = fweight;
        }

        public String getCarType() {
            return carType;
        }

        public void setCarType(String carType) {
            this.carType = carType;
        }

        public String getFhTelephone() {
            return fhTelephone;
        }

        public void setFhTelephone(String fhTelephone) {
            this.fhTelephone = fhTelephone;
        }

        public String getShArea() {
            return shArea;
        }

        public void setShArea(String shArea) {
            this.shArea = shArea;
        }

        public String getSh() {
            return sh;
        }

        public void setSh(String sh) {
            this.sh = sh;
        }

        public String getShAddress() {
            return shAddress;
        }

        public void setShAddress(String shAddress) {
            this.shAddress = shAddress;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShName() {
            return shName;
        }

        public void setShName(String shName) {
            this.shName = shName;
        }

        public String getFhAddress() {
            return fhAddress;
        }

        public void setFhAddress(String fhAddress) {
            this.fhAddress = fhAddress;
        }

        public String getShTelephone() {
            return shTelephone;
        }

        public void setShTelephone(String shTelephone) {
            this.shTelephone = shTelephone;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getIsAppoint() {
            return isAppoint;
        }

        public void setIsAppoint(String isAppoint) {
            this.isAppoint = isAppoint;
        }

        public String getFmainId() {
            return fmainId;
        }

        public void setFmainId(String fmainId) {
            this.fmainId = fmainId;
        }

        public List<OrderGoodsListBean> getOrderGoodsList() {
            return orderGoodsList;
        }

        public void setOrderGoodsList(List<OrderGoodsListBean> orderGoodsList) {
            this.orderGoodsList = orderGoodsList;
        }

        public String getZh_time() {
            return zh_time;
        }

        public void setZh_time(String zh_time) {
            this.zh_time = zh_time;
        }

        public static class ZhTimeBean {
            /**
             * date : 25
             * hours : 16
             * seconds : 20
             * month : 0
             * nanos : 0
             * timezoneOffset : -480
             * year : 119
             * minutes : 45
             * time : 1548405920000
             * day : 5
             */

            private int date;
            private int  hours;
            private int  seconds;
            private int  month;
            private int  nanos;
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

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
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

        public static class OrderGoodsListBean {
            /**
             * orderId : 2c90b4e368841e4a0168842f855a0001
             * id : 2c90b4e368841e4a0168842f855b0002
             * goodsName : 上装
             * goodsSpace : 11
             * goodsWeight : 12
             */

            private String orderId;
            private String id;
            private String goodsName;
            private int    goodsSpace;
            private int    goodsWeight;

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

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
        }
    }


    //    /**
//     * message : 成功
//     * data : [{"id":"2c90b4e3684feaef01684ffd1ff10001","carType":"2","orderNo":"11","fhTelephone":"18234567890","shTelephone":"18234567898","orderGoodsList":[{"id":"2c90b4e3684feaef01684ffd1ff30002","orderId":"2c90b4e3684feaef01684ffd1ff10001","goodsWeight":12,"goodsSpace":11,"goodsName":"上装"},{"id":"2c90b4e3684feaef01684ffd1ff30003","orderId":"2c90b4e3684feaef01684ffd1ff10001","goodsWeight":22,"goodsSpace":21,"goodsName":"下装"}],"fhName":"张三","fstatus":"0","ffee":2679.89,"isAppoint":"0","sh":"3270","isFapiao":"0","fweight":34,"fcheck":"0","appointId":"","fmainId":"yingsu0028","fsubId":"","fh":"1599","shArea":"","fhAddress":"上海路111号","shName":"李四","shAddress":"南京路222号","zhTime":"2019-01-15 08:00","goodsName":"服装"},{"id":"2c90b4e368508ab8016850a7bd2a0000","carType":"2","orderNo":"22","fhTelephone":"18234567890","shTelephone":"18234567898","orderGoodsList":[{"id":"2c90b4e368508ab8016850a7bda70001","orderId":"2c90b4e368508ab8016850a7bd2a0000","goodsWeight":12,"goodsSpace":11,"goodsName":"上装"},{"id":"2c90b4e368508ab8016850a7bda70002","orderId":"2c90b4e368508ab8016850a7bd2a0000","goodsWeight":22,"goodsSpace":21,"goodsName":"下装"}],"fhName":"王五","fstatus":"0","ffee":1665.13,"isAppoint":"0","sh":"390","isFapiao":"1","fweight":34,"fcheck":"0","appointId":"","fmainId":"yingsu0028","fsubId":"","fh":"2433","shArea":"","fhAddress":"人民路","shName":"李四","shAddress":"北京路","zhTime":"2019-01-15","goodsName":"服装"},{"id":"2c90b4e368508ab8016850a892930003","carType":"1","orderNo":"33","fhTelephone":"18234567890","shTelephone":"18234567898","orderGoodsList":[{"id":"2c90b4e368508ab8016850a892940004","orderId":"2c90b4e368508ab8016850a892930003","goodsWeight":12,"goodsSpace":11,"goodsName":"包菜"},{"id":"2c90b4e368508ab8016850a892940005","orderId":"2c90b4e368508ab8016850a892930003","goodsWeight":22,"goodsSpace":21,"goodsName":"青菜"}],"fhName":"张一","fstatus":"0","ffee":1332.46,"isAppoint":"0","sh":"390","isFapiao":"0","fweight":34,"fcheck":"0","appointId":"","fmainId":"yingsu0028","fsubId":"","fh":"1447","shArea":"","fhAddress":"人民路12号","shName":"李四","shAddress":"人民路1222号","zhTime":"2019-01-15 08:00","goodsName":"蔬菜"},{"id":"2c90b4e368508ab8016850a967510006","carType":"2","orderNo":"44","fhTelephone":"18234567890","shTelephone":"18234567898","orderGoodsList":[{"id":"2c90b4e368508ab8016850a967530007","orderId":"2c90b4e368508ab8016850a967510006","goodsWeight":12,"goodsSpace":11,"goodsName":"数据线1"},{"id":"2c90b4e368508ab8016850a967530008","orderId":"2c90b4e368508ab8016850a967510006","goodsWeight":22,"goodsSpace":21,"goodsName":"数据线2"}],"fhName":"张二","fstatus":"0","ffee":2870.52,"isAppoint":"0","sh":"2607","isFapiao":"1","fweight":34,"fcheck":"0","appointId":"","fmainId":"yingsu0028","fsubId":"","fh":"1524","shArea":"","fhAddress":"人民路122号","shName":"李四","shAddress":"人民路212号","zhTime":"2019-01-15","goodsName":"电子产品"},{"id":"2c90b4e368508ab8016850ab44c6000a","carType":"2","orderNo":"55","fhTelephone":"18234567890","shTelephone":"18234567898","orderGoodsList":[{"id":"2c90b4e368508ab8016850ab44c6000b","orderId":"2c90b4e368508ab8016850ab44c6000a","goodsWeight":12,"goodsSpace":11,"goodsName":"西瓜"},{"id":"2c90b4e368508ab8016850ab44c6000c","orderId":"2c90b4e368508ab8016850ab44c6000a","goodsWeight":22,"goodsSpace":21,"goodsName":"苹果"}],"fhName":"张四","fstatus":"0","ffee":74.36,"isAppoint":"0","sh":"391","isFapiao":"0","fweight":34,"fcheck":"0","appointId":"","fmainId":"yingsu0028","fsubId":"","fh":"390","shArea":"","fhAddress":"上海路123号","shName":"李四","shAddress":"上海路1223号","zhTime":"2019-01-15","goodsName":"水果"},{"id":"2c90b4e368508ab8016850b458810017","carType":"2","orderNo":"66","fhTelephone":"18234567890","shTelephone":"18234567898","orderGoodsList":[{"id":"2c90b4e368508ab8016850b458820018","orderId":"2c90b4e368508ab8016850b458810017","goodsWeight":12,"goodsSpace":11,"goodsName":"虾"},{"id":"2c90b4e368508ab8016850b458820019","orderId":"2c90b4e368508ab8016850b458810017","goodsWeight":22,"goodsSpace":21,"goodsName":"鱼"}],"fhName":"张五","fstatus":"0","ffee":2936.48,"isAppoint":"0","sh":"1447","isFapiao":"1","fweight":34,"fcheck":"0","appointId":"","fmainId":"yingsu0028","fsubId":"","fh":"3267","shArea":"","fhAddress":"上海路123号","shName":"李四","shAddress":"上海路1223号","zhTime":"2019-01-15","goodsName":"生鲜"},{"id":"2c90b4e368508ab8016850b6121b001a","carType":"2","orderNo":"77","fhTelephone":"18234567890","shTelephone":"18234567898","orderGoodsList":[{"id":"2c90b4e368508ab8016850b6121b001b","orderId":"2c90b4e368508ab8016850b6121b001a","goodsWeight":12,"goodsSpace":11,"goodsName":"上装"},{"id":"2c90b4e368508ab8016850b6121d001c","orderId":"2c90b4e368508ab8016850b6121b001a","goodsWeight":22,"goodsSpace":21,"goodsName":"下装"}],"fhName":"张刘","fstatus":"0","ffee":999.34,"isAppoint":"0","sh":"390","isFapiao":"0","fweight":34,"fcheck":"0","appointId":"","fmainId":"yingsu0028","fsubId":"","fh":"1447","shArea":"","fhAddress":"南通55号","shName":"李四","shAddress":"南通55号","zhTime":"2019-01-15","goodsName":"服装"}]
//     * ok : true
//     * respCode : 0
//     */
//
//    private String message;
//    private boolean        ok;
//    private String         respCode;
//    private List<DataBean> data;
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public boolean isOk() {
//        return ok;
//    }
//
//    public void setOk(boolean ok) {
//        this.ok = ok;
//    }
//
//    public String getRespCode() {
//        return respCode;
//    }
//
//    public void setRespCode(String respCode) {
//        this.respCode = respCode;
//    }
//
//    public List<DataBean> getData() {
//        return data;
//    }
//
//    public void setData(List<DataBean> data) {
//        this.data = data;
//    }
//
//    public static class DataBean {
//        /**
//         * id : 2c90b4e3684feaef01684ffd1ff10001
//         * carType : 2
//         * orderNo : 11
//         * fhTelephone : 18234567890
//         * shTelephone : 18234567898
//         * orderGoodsList : [{"id":"2c90b4e3684feaef01684ffd1ff30002","orderId":"2c90b4e3684feaef01684ffd1ff10001","goodsWeight":12,"goodsSpace":11,"goodsName":"上装"},{"id":"2c90b4e3684feaef01684ffd1ff30003","orderId":"2c90b4e3684feaef01684ffd1ff10001","goodsWeight":22,"goodsSpace":21,"goodsName":"下装"}]
//         * fhName : 张三
//         * fstatus : 0
//         * ffee : 2679.89
//         * isAppoint : 0
//         * sh : 3270
//         * isFapiao : 0
//         * fweight : 34.0
//         * fcheck : 0
//         * appointId :
//         * fmainId : yingsu0028
//         * fsubId :
//         * fh : 1599
//         * shArea :
//         * fhAddress : 上海路111号
//         * shName : 李四
//         * shAddress : 南京路222号
//         * zhTime : 2019-01-15 08:00
//         * goodsName : 服装
//         */
//
//        private String id;
//        private String                   carType;
//        private String                   orderNo;
//        private String                   fhTelephone;
//        private String                   shTelephone;
//        private String                   fhName;
//        private String                   fstatus;
//        private double                   ffee;
//        private String                   isAppoint;
//        private String                   sh;
//        private String                   isFapiao;
//        private double                   fweight;
//        private String                   fcheck;
//        private String                   appointId;
//        private String                   fmainId;
//        private String                   fsubId;
//        private String                   fh;
//        private String                   shArea;
//        private String                   fhAddress;
//        private String                   shName;
//        private String                   shAddress;
//        private String                   zhTime;
//        private String                   goodsName;
//        private List<OrderGoodsListBean> orderGoodsList;
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getCarType() {
//            return carType;
//        }
//
//        public void setCarType(String carType) {
//            this.carType = carType;
//        }
//
//        public String getOrderNo() {
//            return orderNo;
//        }
//
//        public void setOrderNo(String orderNo) {
//            this.orderNo = orderNo;
//        }
//
//        public String getFhTelephone() {
//            return fhTelephone;
//        }
//
//        public void setFhTelephone(String fhTelephone) {
//            this.fhTelephone = fhTelephone;
//        }
//
//        public String getShTelephone() {
//            return shTelephone;
//        }
//
//        public void setShTelephone(String shTelephone) {
//            this.shTelephone = shTelephone;
//        }
//
//        public String getFhName() {
//            return fhName;
//        }
//
//        public void setFhName(String fhName) {
//            this.fhName = fhName;
//        }
//
//        public String getFstatus() {
//            return fstatus;
//        }
//
//        public void setFstatus(String fstatus) {
//            this.fstatus = fstatus;
//        }
//
//        public double getFfee() {
//            return ffee;
//        }
//
//        public void setFfee(double ffee) {
//            this.ffee = ffee;
//        }
//
//        public String getIsAppoint() {
//            return isAppoint;
//        }
//
//        public void setIsAppoint(String isAppoint) {
//            this.isAppoint = isAppoint;
//        }
//
//        public String getSh() {
//            return sh;
//        }
//
//        public void setSh(String sh) {
//            this.sh = sh;
//        }
//
//        public String getIsFapiao() {
//            return isFapiao;
//        }
//
//        public void setIsFapiao(String isFapiao) {
//            this.isFapiao = isFapiao;
//        }
//
//        public double getFweight() {
//            return fweight;
//        }
//
//        public void setFweight(double fweight) {
//            this.fweight = fweight;
//        }
//
//        public String getFcheck() {
//            return fcheck;
//        }
//
//        public void setFcheck(String fcheck) {
//            this.fcheck = fcheck;
//        }
//
//        public String getAppointId() {
//            return appointId;
//        }
//
//        public void setAppointId(String appointId) {
//            this.appointId = appointId;
//        }
//
//        public String getFmainId() {
//            return fmainId;
//        }
//
//        public void setFmainId(String fmainId) {
//            this.fmainId = fmainId;
//        }
//
//        public String getFsubId() {
//            return fsubId;
//        }
//
//        public void setFsubId(String fsubId) {
//            this.fsubId = fsubId;
//        }
//
//        public String getFh() {
//            return fh;
//        }
//
//        public void setFh(String fh) {
//            this.fh = fh;
//        }
//
//        public String getShArea() {
//            return shArea;
//        }
//
//        public void setShArea(String shArea) {
//            this.shArea = shArea;
//        }
//
//        public String getFhAddress() {
//            return fhAddress;
//        }
//
//        public void setFhAddress(String fhAddress) {
//            this.fhAddress = fhAddress;
//        }
//
//        public String getShName() {
//            return shName;
//        }
//
//        public void setShName(String shName) {
//            this.shName = shName;
//        }
//
//        public String getShAddress() {
//            return shAddress;
//        }
//
//        public void setShAddress(String shAddress) {
//            this.shAddress = shAddress;
//        }
//
//        public String getZhTime() {
//            return zhTime;
//        }
//
//        public void setZhTime(String zhTime) {
//            this.zhTime = zhTime;
//        }
//
//        public String getGoodsName() {
//            return goodsName;
//        }
//
//        public void setGoodsName(String goodsName) {
//            this.goodsName = goodsName;
//        }
//
//        public List<OrderGoodsListBean> getOrderGoodsList() {
//            return orderGoodsList;
//        }
//
//        public void setOrderGoodsList(List<OrderGoodsListBean> orderGoodsList) {
//            this.orderGoodsList = orderGoodsList;
//        }
//
//        public static class OrderGoodsListBean {
//            /**
//             * id : 2c90b4e3684feaef01684ffd1ff30002
//             * orderId : 2c90b4e3684feaef01684ffd1ff10001
//             * goodsWeight : 12.0
//             * goodsSpace : 11.0
//             * goodsName : 上装
//             */
//
//            private String id;
//            private String orderId;
//            private double goodsWeight;
//            private double goodsSpace;
//            private String goodsName;
//
//            public String getId() {
//                return id;
//            }
//
//            public void setId(String id) {
//                this.id = id;
//            }
//
//            public String getOrderId() {
//                return orderId;
//            }
//
//            public void setOrderId(String orderId) {
//                this.orderId = orderId;
//            }
//
//            public double getGoodsWeight() {
//                return goodsWeight;
//            }
//
//            public void setGoodsWeight(double goodsWeight) {
//                this.goodsWeight = goodsWeight;
//            }
//
//            public double getGoodsSpace() {
//                return goodsSpace;
//            }
//
//            public void setGoodsSpace(double goodsSpace) {
//                this.goodsSpace = goodsSpace;
//            }
//
//            public String getGoodsName() {
//                return goodsName;
//            }
//
//            public void setGoodsName(String goodsName) {
//                this.goodsName = goodsName;
//            }
//        }
//    }
}
