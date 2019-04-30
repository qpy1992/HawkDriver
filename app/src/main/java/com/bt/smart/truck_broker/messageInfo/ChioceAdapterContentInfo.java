package com.bt.smart.truck_broker.messageInfo;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/9 22:49
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class ChioceAdapterContentInfo {

    /**
     * cont : 长度
     * chioce : true
     */

    private String cont;
    private boolean chioce;
    /**
     * id : 10
     */

    private String id;

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public boolean isChioce() {
        return chioce;
    }

    public void setChioce(boolean chioce) {
        this.chioce = chioce;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
