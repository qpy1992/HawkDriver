package com.bt.smart.truck_broker.messageInfo;

import android.telephony.SignalStrength;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MDetailInfo {
    private String message;
    private int      size;
    private List<DataBean> data;
    private String   respCode;
    private boolean  ok;

    public static class DataBean{
         private String addtime;
         private String driverid;
         private BigDecimal fmoney;
         private String fnote;
         private String forderno;
         private BigDecimal fremain;
         private int ftype;
         private String id;
         private String tradeno;

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getDriverid() {
            return driverid;
        }

        public void setDriverid(String driverid) {
            this.driverid = driverid;
        }

        public BigDecimal getFmoney() {
            return fmoney;
        }

        public void setFmoney(BigDecimal fmoney) {
            this.fmoney = fmoney;
        }

        public String getFnote() {
            return fnote;
        }

        public void setFnote(String fnote) {
            this.fnote = fnote;
        }

        public String getForderno() {
            return forderno;
        }

        public void setForderno(String forderno) {
            this.forderno = forderno;
        }

        public BigDecimal getFremain() {
            return fremain;
        }

        public void setFremain(BigDecimal fremain) {
            this.fremain = fremain;
        }

        public int getFtype() {
            return ftype;
        }

        public void setFtype(int ftype) {
            this.ftype = ftype;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTradeno() {
            return tradeno;
        }

        public void setTradeno(String tradeno) {
            this.tradeno = tradeno;
        }
    }

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
}
