package com.bt.smart.truck_broker.messageInfo;

public class SignInfo {

    /**
     * message : 成功
     * size : 0
     * data : 签署成功!
     * respCode : 0
     * ok : true
     */

    private String message;
    private int size;
    private String data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
}
