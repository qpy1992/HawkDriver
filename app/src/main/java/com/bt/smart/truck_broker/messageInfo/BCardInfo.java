package com.bt.smart.truck_broker.messageInfo;

import java.util.Date;
import java.util.List;

public class BCardInfo {

    private String message;
    private List<DataBean> data;
    private String   respCode;
    private boolean  ok;
    private int size;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
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

    public static class DataBean{
        private String id;
        private String fid;
        private String fcardno;
        private String fkhh;
        private String fprovince;
        private String fcity;
        private String fmobile;
        private String fname;
        private String fbindtime;
        private Integer fdeleted;

        public String getFcardno() {
            return fcardno;
        }

        public void setFcardno(String fcardno) {
            this.fcardno = fcardno;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getFkhh() {
            return fkhh;
        }

        public void setFkhh(String fkhh) {
            this.fkhh = fkhh;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getFprovince() {
            return fprovince;
        }

        public void setFprovince(String fprovince) {
            this.fprovince = fprovince;
        }

        public String getFcity() {
            return fcity;
        }

        public void setFcity(String fcity) {
            this.fcity = fcity;
        }

        public String getFmobile() {
            return fmobile;
        }

        public void setFmobile(String fmobile) {
            this.fmobile = fmobile;
        }

        public String getFbindtime() {
            return fbindtime;
        }

        public void setFbindtime(String fbindtime) {
            this.fbindtime = fbindtime;
        }

        public Integer getFdeleted() {
            return fdeleted;
        }

        public void setFdeleted(Integer fdeleted) {
            this.fdeleted = fdeleted;
        }
    }
}
