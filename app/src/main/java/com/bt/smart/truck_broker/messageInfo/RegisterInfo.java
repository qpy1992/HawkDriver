package com.bt.smart.truck_broker.messageInfo;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/11 14:08
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class RegisterInfo {

    /**
     * message : 成功
     * data : {"id":"2c9084dd683aff1c01683b8356530028","fid":null,"idCard1":null,"fname":null,"fpoints":null,"fcartype":null,"fcarlength":null,"fpassword":"e10adc3949ba59abbe56e057f20f883e","fcarno":null,"idCard2":null,"fphoto":null,"idNumber":null,"drivingLicense":null,"driverLicense":null,"faccountEncryption":null,"checkStatus":null,"companyName":null,"checkReason":null,"fmobile":"18036215618","faccount":null}
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
         * id : 2c9084dd683aff1c01683b8356530028
         * fid : null
         * idCard1 : null
         * fname : null
         * fpoints : null
         * fcartype : null
         * fcarlength : null
         * fpassword : e10adc3949ba59abbe56e057f20f883e
         * fcarno : null
         * idCard2 : null
         * fphoto : null
         * idNumber : null
         * drivingLicense : null
         * driverLicense : null
         * faccountEncryption : null
         * checkStatus : null
         * companyName : null
         * checkReason : null
         * fmobile : 18036215618
         * faccount : null
         */

        private String id;
        private Object fid;
        private Object idCard1;
        private Object fname;
        private Object fpoints;
        private Object fcartype;
        private Object fcarlength;
        private String fpassword;
        private Object fcarno;
        private Object idCard2;
        private Object fphoto;
        private Object idNumber;
        private Object drivingLicense;
        private Object driverLicense;
        private Object faccountEncryption;
        private Object checkStatus;
        private Object companyName;
        private Object checkReason;
        private String fmobile;
        private Object faccount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getFid() {
            return fid;
        }

        public void setFid(Object fid) {
            this.fid = fid;
        }

        public Object getIdCard1() {
            return idCard1;
        }

        public void setIdCard1(Object idCard1) {
            this.idCard1 = idCard1;
        }

        public Object getFname() {
            return fname;
        }

        public void setFname(Object fname) {
            this.fname = fname;
        }

        public Object getFpoints() {
            return fpoints;
        }

        public void setFpoints(Object fpoints) {
            this.fpoints = fpoints;
        }

        public Object getFcartype() {
            return fcartype;
        }

        public void setFcartype(Object fcartype) {
            this.fcartype = fcartype;
        }

        public Object getFcarlength() {
            return fcarlength;
        }

        public void setFcarlength(Object fcarlength) {
            this.fcarlength = fcarlength;
        }

        public String getFpassword() {
            return fpassword;
        }

        public void setFpassword(String fpassword) {
            this.fpassword = fpassword;
        }

        public Object getFcarno() {
            return fcarno;
        }

        public void setFcarno(Object fcarno) {
            this.fcarno = fcarno;
        }

        public Object getIdCard2() {
            return idCard2;
        }

        public void setIdCard2(Object idCard2) {
            this.idCard2 = idCard2;
        }

        public Object getFphoto() {
            return fphoto;
        }

        public void setFphoto(Object fphoto) {
            this.fphoto = fphoto;
        }

        public Object getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(Object idNumber) {
            this.idNumber = idNumber;
        }

        public Object getDrivingLicense() {
            return drivingLicense;
        }

        public void setDrivingLicense(Object drivingLicense) {
            this.drivingLicense = drivingLicense;
        }

        public Object getDriverLicense() {
            return driverLicense;
        }

        public void setDriverLicense(Object driverLicense) {
            this.driverLicense = driverLicense;
        }

        public Object getFaccountEncryption() {
            return faccountEncryption;
        }

        public void setFaccountEncryption(Object faccountEncryption) {
            this.faccountEncryption = faccountEncryption;
        }

        public Object getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(Object checkStatus) {
            this.checkStatus = checkStatus;
        }

        public Object getCompanyName() {
            return companyName;
        }

        public void setCompanyName(Object companyName) {
            this.companyName = companyName;
        }

        public Object getCheckReason() {
            return checkReason;
        }

        public void setCheckReason(Object checkReason) {
            this.checkReason = checkReason;
        }

        public String getFmobile() {
            return fmobile;
        }

        public void setFmobile(String fmobile) {
            this.fmobile = fmobile;
        }

        public Object getFaccount() {
            return faccount;
        }

        public void setFaccount(Object faccount) {
            this.faccount = faccount;
        }
    }
}
