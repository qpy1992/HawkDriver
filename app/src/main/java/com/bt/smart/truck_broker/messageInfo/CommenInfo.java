package com.bt.smart.truck_broker.messageInfo;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/10 15:14
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class CommenInfo {

    /**
     * message : 成功
     * data : null
     * ok : true
     * respCode : 0
     */

    private String message;
    private Object  data;
    private boolean ok;
    private String  respCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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
}
