package com.bt.smart.truck_broker.messageInfo;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/10 13:28
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class AddDriverLinesInfo {

    /**
     * message : 成功
     * data : {"id":"4d2881e468838fe2016883f815d80003","origin2":"安远县","origin3":"潜山县","destination2":"怀宁县","destination3":"枞阳县","addDate":"2019-01-25 15:45","destination1":"宜秀区","origin1":"大观区","carLong":"2.3米","driverId":"2c979074687943f3016879727bc70001","carType":"2米"}
     * ok : true
     * respCode : 0
     */

    private String message;
    private DataBean data;
    private boolean  ok;
    private String   respCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * id : 4d2881e468838fe2016883f815d80003
         * origin2 : 安远县
         * origin3 : 潜山县
         * destination2 : 怀宁县
         * destination3 : 枞阳县
         * addDate : 2019-01-25 15:45
         * destination1 : 宜秀区
         * origin1 : 大观区
         * carLong : 2.3米
         * driverId : 2c979074687943f3016879727bc70001
         * carType : 2米
         */

        private String id;
        private String origin2;
        private String origin3;
        private String destination2;
        private String destination3;
        private String addDate;
        private String destination1;
        private String origin1;
        private String carLong;
        private String driverId;
        private String carType;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrigin2() {
            return origin2;
        }

        public void setOrigin2(String origin2) {
            this.origin2 = origin2;
        }

        public String getOrigin3() {
            return origin3;
        }

        public void setOrigin3(String origin3) {
            this.origin3 = origin3;
        }

        public String getDestination2() {
            return destination2;
        }

        public void setDestination2(String destination2) {
            this.destination2 = destination2;
        }

        public String getDestination3() {
            return destination3;
        }

        public void setDestination3(String destination3) {
            this.destination3 = destination3;
        }

        public String getAddDate() {
            return addDate;
        }

        public void setAddDate(String addDate) {
            this.addDate = addDate;
        }

        public String getDestination1() {
            return destination1;
        }

        public void setDestination1(String destination1) {
            this.destination1 = destination1;
        }

        public String getOrigin1() {
            return origin1;
        }

        public void setOrigin1(String origin1) {
            this.origin1 = origin1;
        }

        public String getCarLong() {
            return carLong;
        }

        public void setCarLong(String carLong) {
            this.carLong = carLong;
        }

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public String getCarType() {
            return carType;
        }

        public void setCarType(String carType) {
            this.carType = carType;
        }
    }
}
