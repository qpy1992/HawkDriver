package com.bt.smart.truck_broker.messageInfo;

public class ApkInfo {

    private String respCode;
    private int                size;
    private String             message;
    private ApkBean data;
    Boolean ok;

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

    public ApkBean getData() {
        return data;
    }

    public void setData(ApkBean data) {
        this.data = data;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public class ApkBean{
        String id;
        int versionCode;
        String versionName;
        String apkPath;
        int isnew;
        String apkInfo;
        int ftype;
        String updateTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getApkPath() {
            return apkPath;
        }

        public void setApkPath(String apkPath) {
            this.apkPath = apkPath;
        }

        public int getIsnew() {
            return isnew;
        }

        public void setIsnew(int isnew) {
            this.isnew = isnew;
        }

        public String getApkInfo() {
            return apkInfo;
        }

        public void setApkInfo(String apkInfo) {
            this.apkInfo = apkInfo;
        }

        public int getFtype() {
            return ftype;
        }

        public void setFtype(int ftype) {
            this.ftype = ftype;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
