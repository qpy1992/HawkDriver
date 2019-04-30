package com.bt.smart.truck_broker.messageInfo;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/10 11:31
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class SearchDriverLinesInfo {

    /**
     * message : 成功
     * data : [{"id":"2c979074687943f30168797597790003","origin1":"朝阳区","destination1":"石景山区","car_long":"2.7米","car_type":"高栏","driver_id":"2c979074687943f3016879727bc70001","add_date":"2019-01-25 01:04:57","destination2":"宿豫区","destination3":null,"origin2":"科尔沁右翼前旗","origin3":null},{"id":"2c979074687943f301687975ef570004","origin1":"东台市","destination1":"武进区","car_long":"不限车长","car_type":"平板/高栏","driver_id":"2c979074687943f3016879727bc70001","add_date":"2019-01-24 01:05","destination2":null,"destination3":null,"origin2":"东台市","origin3":null},{"id":"2c979074687943f301687976a5b30005","origin1":"科尔沁右翼前旗","destination1":"泗洪县","car_long":"不限车长","car_type":"不限车型","driver_id":"2c979074687943f3016879727bc70001","add_date":"2019-01-23 01:05:06","destination2":"宿豫区","destination3":"市辖区","origin2":"乌兰浩特市","origin3":"科尔沁右翼中旗"},{"id":"4d2881e468838fe2016883f79a770002","origin1":"大观区","destination1":"宜秀区","car_long":"2.3米","car_type":"2米","driver_id":"2c979074687943f3016879727bc70001","add_date":"2019-01-25 15:44:28","destination2":"岳西县","destination3":"枞阳县","origin2":"怀宁县","origin3":"潜山县"},{"id":"4d2881e468838fe2016883f815d80003","origin1":"大观区","destination1":"宜秀区","car_long":"2.3米","car_type":"2米","driver_id":"2c979074687943f3016879727bc70001","add_date":"2019-01-25 15:45","destination2":"怀宁县","destination3":"枞阳县","origin2":"安远县","origin3":"潜山县"}]
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
         * id : 2c979074687943f30168797597790003
         * origin1 : 朝阳区
         * destination1 : 石景山区
         * car_long : 2.7米
         * car_type : 高栏
         * driver_id : 2c979074687943f3016879727bc70001
         * add_date : 2019-01-25 01:04:57
         * destination2 : 宿豫区
         * destination3 : null
         * origin2 : 科尔沁右翼前旗
         * origin3 : null
         */

        private String id;
        private String origin1;
        private String destination1;
        private String car_long;
        private String car_type;
        private String driver_id;
        private String add_date;
        private String destination2;
        private Object destination3;
        private String origin2;
        private Object origin3;
        /**
         * canDel : false
         */

        private boolean canDel;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrigin1() {
            return origin1;
        }

        public void setOrigin1(String origin1) {
            this.origin1 = origin1;
        }

        public String getDestination1() {
            return destination1;
        }

        public void setDestination1(String destination1) {
            this.destination1 = destination1;
        }

        public String getCar_long() {
            return car_long;
        }

        public void setCar_long(String car_long) {
            this.car_long = car_long;
        }

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
        }

        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
        }

        public String getAdd_date() {
            return add_date;
        }

        public void setAdd_date(String add_date) {
            this.add_date = add_date;
        }

        public String getDestination2() {
            return destination2;
        }

        public void setDestination2(String destination2) {
            this.destination2 = destination2;
        }

        public Object getDestination3() {
            return destination3;
        }

        public void setDestination3(Object destination3) {
            this.destination3 = destination3;
        }

        public String getOrigin2() {
            return origin2;
        }

        public void setOrigin2(String origin2) {
            this.origin2 = origin2;
        }

        public Object getOrigin3() {
            return origin3;
        }

        public void setOrigin3(Object origin3) {
            this.origin3 = origin3;
        }

        public boolean isCanDel() {
            return canDel;
        }

        public void setCanDel(boolean canDel) {
            this.canDel = canDel;
        }
    }
}
