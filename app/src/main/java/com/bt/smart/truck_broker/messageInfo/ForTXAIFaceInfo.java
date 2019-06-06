package com.bt.smart.truck_broker.messageInfo;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/4 11:17
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class ForTXAIFaceInfo {

    /**
     * ret : 0
     * msg : ok
     * data : {"similarity":18,"fail_flag":0}
     */

    private int ret;
    private String   msg;
    private DataBean data;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * similarity : 18
         * fail_flag : 0
         */

        private int similarity;
        private int fail_flag;

        public int getSimilarity() {
            return similarity;
        }

        public void setSimilarity(int similarity) {
            this.similarity = similarity;
        }

        public int getFail_flag() {
            return fail_flag;
        }

        public void setFail_flag(int fail_flag) {
            this.fail_flag = fail_flag;
        }
    }
}
