package com.bt.smart.truck_broker.messageInfo;

/**
 * @创建者 AndyYan
 * @创建时间 2018/11/23 20:54
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class NewApkInfo {

    /**
     * newAppVersion : {"apk_file":"upload/files/20181203/smartHox_1543816262559.apk","change_message":"上传测试。","show_code":"1","id":4}
     * message : 最新app版本查询成功
     * code : 1
     */

    private NewAppVersionBean newAppVersion;
    private String message;
    private int    code;

    public NewAppVersionBean getNewAppVersion() {
        return newAppVersion;
    }

    public void setNewAppVersion(NewAppVersionBean newAppVersion) {
        this.newAppVersion = newAppVersion;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class NewAppVersionBean {
        /**
         * apk_file : upload/files/20181203/smartHox_1543816262559.apk
         * change_message : 上传测试。
         * show_code : 1
         * id : 4
         */

        private String apk_file;
        private String change_message;
        private String show_code;
        private int    id;

        public String getApk_file() {
            return apk_file;
        }

        public void setApk_file(String apk_file) {
            this.apk_file = apk_file;
        }

        public String getChange_message() {
            return change_message;
        }

        public void setChange_message(String change_message) {
            this.change_message = change_message;
        }

        public String getShow_code() {
            return show_code;
        }

        public void setShow_code(String show_code) {
            this.show_code = show_code;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
