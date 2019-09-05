package com.bt.smart.truck_broker.messageInfo;

public class AuthInfo {

    /**
     * message : 组织机构代码号格式错误
     * size : 0
     * data : null
     * respCode : -1
     * ok : false
     */

    private String message;
    private int size;
    private DataBean data;
    private String respCode;
    private boolean ok;

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

    public class DataBean{
        String paccountid;
        String personalUrl;

        public String getPaccountid() {
            return paccountid;
        }

        public void setPaccountid(String paccountid) {
            this.paccountid = paccountid;
        }

        public String getPersonalUrl() {
            return personalUrl;
        }

        public void setPersonalUrl(String personalUrl) {
            this.personalUrl = personalUrl;
        }
    }
}
