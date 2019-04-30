package com.bt.smart.truck_broker.messageInfo;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/9 19:20
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class TakeOrderResultInfo {

    /**
     * message : 成功
     * data : {"id":"2c9084dd683aff1c01683b5c6d630009","createDate":"2019-01-11 13:22:19","orderStatus":"0","driverId":"2c9084dd6831e10501683266018b0003","orderId":"2c90b4e368368c94016836a7ef26000a"}
     * respCode : 0
     * ok : true
     */

    private String message;
    private DataBean data;
    private String   respCode;
    private boolean  ok;

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
         * id : 2c9084dd683aff1c01683b5c6d630009
         * createDate : 2019-01-11 13:22:19
         * orderStatus : 0
         * driverId : 2c9084dd6831e10501683266018b0003
         * orderId : 2c90b4e368368c94016836a7ef26000a
         */

        private String id;
        private String createDate;
        private String orderStatus;
        private String driverId;
        private String orderId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }
}
