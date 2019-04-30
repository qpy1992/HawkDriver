package com.bt.smart.truck_broker.messageInfo;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/9 13:32
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class UpDataDriverInfo {

    /**
     * message : 成功
     * data : {"name":"苟富贵","id":"4d2881f6685044150168504c43bc0002","freason":null,"fstatus":null,"fchecker":null,"submitDate":"2019-01-15 14:56:41","fcheckDate":null,"drivingLicence":"upload/files/1547535289189.png","vehicleLicence":"upload/files/1547535289393.png","fcartype":"高栏","fcarlength":"2.7米","headpic":"upload/files/1547535289394.png","fcarno":"苏F12345","submitMobile":"18036215618","idnumber":"32556585"}
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
         * name : 苟富贵
         * id : 4d2881f6685044150168504c43bc0002
         * freason : null
         * fstatus : null
         * fchecker : null
         * submitDate : 2019-01-15 14:56:41
         * fcheckDate : null
         * drivingLicence : upload/files/1547535289189.png
         * vehicleLicence : upload/files/1547535289393.png
         * fcartype : 高栏
         * fcarlength : 2.7米
         * headpic : upload/files/1547535289394.png
         * fcarno : 苏F12345
         * submitMobile : 18036215618
         * idnumber : 32556585
         */

        private String name;
        private String id;
        private Object freason;
        private Object fstatus;
        private Object fchecker;
        private String submitDate;
        private Object fcheckDate;
        private String drivingLicence;
        private String vehicleLicence;
        private String fcartype;
        private String fcarlength;
        private String headpic;
        private String fcarno;
        private String submitMobile;
        private String idnumber;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getFreason() {
            return freason;
        }

        public void setFreason(Object freason) {
            this.freason = freason;
        }

        public Object getFstatus() {
            return fstatus;
        }

        public void setFstatus(Object fstatus) {
            this.fstatus = fstatus;
        }

        public Object getFchecker() {
            return fchecker;
        }

        public void setFchecker(Object fchecker) {
            this.fchecker = fchecker;
        }

        public String getSubmitDate() {
            return submitDate;
        }

        public void setSubmitDate(String submitDate) {
            this.submitDate = submitDate;
        }

        public Object getFcheckDate() {
            return fcheckDate;
        }

        public void setFcheckDate(Object fcheckDate) {
            this.fcheckDate = fcheckDate;
        }

        public String getDrivingLicence() {
            return drivingLicence;
        }

        public void setDrivingLicence(String drivingLicence) {
            this.drivingLicence = drivingLicence;
        }

        public String getVehicleLicence() {
            return vehicleLicence;
        }

        public void setVehicleLicence(String vehicleLicence) {
            this.vehicleLicence = vehicleLicence;
        }

        public String getFcartype() {
            return fcartype;
        }

        public void setFcartype(String fcartype) {
            this.fcartype = fcartype;
        }

        public String getFcarlength() {
            return fcarlength;
        }

        public void setFcarlength(String fcarlength) {
            this.fcarlength = fcarlength;
        }

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public String getFcarno() {
            return fcarno;
        }

        public void setFcarno(String fcarno) {
            this.fcarno = fcarno;
        }

        public String getSubmitMobile() {
            return submitMobile;
        }

        public void setSubmitMobile(String submitMobile) {
            this.submitMobile = submitMobile;
        }

        public String getIdnumber() {
            return idnumber;
        }

        public void setIdnumber(String idnumber) {
            this.idnumber = idnumber;
        }
    }
}
