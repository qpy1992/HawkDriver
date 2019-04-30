package com.bt.smart.truck_broker.messageInfo;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/10 9:47
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class ShengDataInfo {

    /**
     * message : 成功
     * data : [{"id":"13","name":"安徽省","pid":"1"},{"id":"2","name":"北京市","pid":"1"},{"id":"23","name":"重庆市","pid":"1"},{"id":"14","name":"福建省","pid":"1"},{"id":"29","name":"甘肃省","pid":"1"},{"id":"20","name":"广东省","pid":"1"},{"id":"21","name":"广西壮族自治区","pid":"1"},{"id":"25","name":"贵州省","pid":"1"},{"id":"22","name":"海南省","pid":"1"},{"id":"4","name":"河北省","pid":"1"},{"id":"9","name":"黑龙江省","pid":"1"},{"id":"17","name":"河南省","pid":"1"},{"id":"18","name":"湖北省","pid":"1"},{"id":"19","name":"湖南省","pid":"1"},{"id":"11","name":"江苏省","pid":"1"},{"id":"15","name":"江西省","pid":"1"},{"id":"8","name":"吉林省","pid":"1"},{"id":"7","name":"辽宁省","pid":"1"},{"id":"6","name":"内蒙古自治区","pid":"1"},{"id":"31","name":"宁夏回族自治区","pid":"1"},{"id":"30","name":"青海省","pid":"1"},{"id":"16","name":"山东省","pid":"1"},{"id":"10","name":"上海市","pid":"1"},{"id":"28","name":"陕西省","pid":"1"},{"id":"5","name":"山西省","pid":"1"},{"id":"24","name":"四川省","pid":"1"},{"id":"3","name":"天津市","pid":"1"},{"id":"32","name":"新疆维吾尔自治区","pid":"1"},{"id":"27","name":"西藏自治区","pid":"1"},{"id":"26","name":"云南省","pid":"1"},{"id":"12","name":"浙江省","pid":"1"}]
     * respCode : 0
     * ok : true
     */

    private String message;
    private String         respCode;
    private boolean        ok;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 13
         * name : 安徽省
         * pid : 1
         */

        private String id;
        private String name;
        private String pid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }
    }
}
