package com.bt.smart.truck_broker.messageInfo;

import java.math.BigDecimal;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/28 8:58
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class LoginInfo {

    /**
     * message : 成功
     * data : {"registerDriver":{"fcarno":"","fid":"","faccountEncryption":"","fname":"","fphoto":"","companyName":"","fcartype":"","checkReason":"","faccount":0,"fpassword":"e10adc3949ba59abbe56e057f20f883e","idNumber":"","fmobile":"18036215618","fpoints":0,"checkStatus":"0","fcarlength":"","drivingLicense":"","idCard1":"","idCard2":"","driverLicense":"","id":"2c979074686e2cc701686e42737b0002"},"orderno":0,"token":"eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxODAzNjIxNTYxOCIsInN1YiI6IjE4MDM2MjE1NjE4IiwiaWF0IjoxNTQ4MDQwNjc4fQ.T4Wte0LfJi_w-reamhNhuvEC0XUirfwQ67nGYE0vwjc"}
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
         * registerDriver : {"fcarno":"","fid":"","faccountEncryption":"","fname":"","fphoto":"","companyName":"","fcartype":"","checkReason":"","faccount":0,"fpassword":"e10adc3949ba59abbe56e057f20f883e","idNumber":"","fmobile":"18036215618","fpoints":0,"checkStatus":"0","fcarlength":"","drivingLicense":"","idCard1":"","idCard2":"","driverLicense":"","id":"2c979074686e2cc701686e42737b0002"}
         * orderno : 0
         * token : eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxODAzNjIxNTYxOCIsInN1YiI6IjE4MDM2MjE1NjE4IiwiaWF0IjoxNTQ4MDQwNjc4fQ.T4Wte0LfJi_w-reamhNhuvEC0XUirfwQ67nGYE0vwjc
         */

        private RegisterDriverBean registerDriver;
        private int    orderno;
        private String token;

        public RegisterDriverBean getRegisterDriver() {
            return registerDriver;
        }

        public void setRegisterDriver(RegisterDriverBean registerDriver) {
            this.registerDriver = registerDriver;
        }

        public int getOrderno() {
            return orderno;
        }

        public void setOrderno(int orderno) {
            this.orderno = orderno;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static class RegisterDriverBean {
            /**
             * fcarno :
             * fid :
             * faccountEncryption :
             * fname :
             * fphoto :
             * companyName :
             * fcartype :
             * checkReason :
             * faccount : 0.0
             * fpassword : e10adc3949ba59abbe56e057f20f883e
             * idNumber :
             * fmobile : 18036215618
             * fpoints : 0
             * checkStatus : 0
             * fcarlength :
             * drivingLicense :
             * idCard1 :
             * idCard2 :
             * driverLicense :
             * id : 2c979074686e2cc701686e42737b0002
             */

            private String fcarno;
            private String fid;
            private String faccountEncryption;
            private String fname;
            private String fphoto;
            private String companyName;
            private String fcartype;
            private String checkReason;
            private BigDecimal faccount;
            private String fpassword;
            private String idNumber;
            private String fmobile;
            private int    fpoints;
            private String checkStatus;
            private String fcarlength;
            private String drivingLicense;
            private String idCard1;
            private String idCard2;
            private String driverLicense;
            private String id;

            public String getFcarno() {
                return fcarno;
            }

            public void setFcarno(String fcarno) {
                this.fcarno = fcarno;
            }

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public String getFaccountEncryption() {
                return faccountEncryption;
            }

            public void setFaccountEncryption(String faccountEncryption) {
                this.faccountEncryption = faccountEncryption;
            }

            public String getFname() {
                return fname;
            }

            public void setFname(String fname) {
                this.fname = fname;
            }

            public String getFphoto() {
                return fphoto;
            }

            public void setFphoto(String fphoto) {
                this.fphoto = fphoto;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

            public String getFcartype() {
                return fcartype;
            }

            public void setFcartype(String fcartype) {
                this.fcartype = fcartype;
            }

            public String getCheckReason() {
                return checkReason;
            }

            public void setCheckReason(String checkReason) {
                this.checkReason = checkReason;
            }

            public BigDecimal getFaccount() {
                return faccount;
            }

            public void setFaccount(BigDecimal faccount) {
                this.faccount = faccount;
            }

            public String getFpassword() {
                return fpassword;
            }

            public void setFpassword(String fpassword) {
                this.fpassword = fpassword;
            }

            public String getIdNumber() {
                return idNumber;
            }

            public void setIdNumber(String idNumber) {
                this.idNumber = idNumber;
            }

            public String getFmobile() {
                return fmobile;
            }

            public void setFmobile(String fmobile) {
                this.fmobile = fmobile;
            }

            public int getFpoints() {
                return fpoints;
            }

            public void setFpoints(int fpoints) {
                this.fpoints = fpoints;
            }

            public String getCheckStatus() {
                return checkStatus;
            }

            public void setCheckStatus(String checkStatus) {
                this.checkStatus = checkStatus;
            }

            public String getFcarlength() {
                return fcarlength;
            }

            public void setFcarlength(String fcarlength) {
                this.fcarlength = fcarlength;
            }

            public String getDrivingLicense() {
                return drivingLicense;
            }

            public void setDrivingLicense(String drivingLicense) {
                this.drivingLicense = drivingLicense;
            }

            public String getIdCard1() {
                return idCard1;
            }

            public void setIdCard1(String idCard1) {
                this.idCard1 = idCard1;
            }

            public String getIdCard2() {
                return idCard2;
            }

            public void setIdCard2(String idCard2) {
                this.idCard2 = idCard2;
            }

            public String getDriverLicense() {
                return driverLicense;
            }

            public void setDriverLicense(String driverLicense) {
                this.driverLicense = driverLicense;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
