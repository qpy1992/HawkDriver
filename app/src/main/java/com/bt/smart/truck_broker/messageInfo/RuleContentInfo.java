package com.bt.smart.truck_broker.messageInfo;

public class RuleContentInfo {

    /**
     * message : 成功
     * size : 0
     * data : {"fname":"上海鹰速物流有限公司","fcontent":"上海鹰速国际货物运输有限公司是一家以危险品物流为特色服务，公司主要经营道路危险货物运输和物流配送业务有（第二类：易燃气体、非易燃无毒气体；第三类：易燃液体；第四类：易燃固体第六类：毒性物质；第八类：腐蚀性物质（强腐蚀性、弱腐蚀性（除剧毒品））；特种化工。公司自主拥有各种车型的长短途货车400余辆，可承接全国各地的整车运输、危险品运输、仓储、配送业务。 公司以长三角、珠三角为依托,已形成辐射全国的运输服务网络，已在苏州、上海、无锡、昆山、东莞、广州、深圳、惠州、北京、天津、南京、长沙、南昌等各大中城市设立分公司或办事处，长途运输通达80多个城市、短途配送覆盖珠三角、长三角等各大区域，线路覆盖全国300多个城市和地区；完善的服务网络确保为客户提供准确、高效、安全的\\\"门对门\\\"运输服务。公司具有完善的内部组织机构和管理制度，其中市场开发与管理调度人员多名。公司秉承客户至上的服务宗旨，借助先进的物流服务理念和丰富的物流操作经验，为不同客户量身定做提供专业物流方案和优质、高效的物流服务；从而帮助客户降低成本，提升市场竞争力。公司以雄厚的实力、完善的服务网络、优质的服务质量、专业的服务水平赢得了客户的赞誉。成为了众多外资企业，国内大型生产企业优秀的物流供应商，值得信赖的合作伙伴。"}
     * respCode : 0
     * ok : true
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

    public static class DataBean {
        /**
         * fname : 上海鹰速物流有限公司
         * fcontent : 上海鹰速国际货物运输有限公司是一家以危险品物流为特色服务，公司主要经营道路危险货物运输和物流配送业务有（第二类：易燃气体、非易燃无毒气体；第三类：易燃液体；第四类：易燃固体第六类：毒性物质；第八类：腐蚀性物质（强腐蚀性、弱腐蚀性（除剧毒品））；特种化工。公司自主拥有各种车型的长短途货车400余辆，可承接全国各地的整车运输、危险品运输、仓储、配送业务。 公司以长三角、珠三角为依托,已形成辐射全国的运输服务网络，已在苏州、上海、无锡、昆山、东莞、广州、深圳、惠州、北京、天津、南京、长沙、南昌等各大中城市设立分公司或办事处，长途运输通达80多个城市、短途配送覆盖珠三角、长三角等各大区域，线路覆盖全国300多个城市和地区；完善的服务网络确保为客户提供准确、高效、安全的\"门对门\"运输服务。公司具有完善的内部组织机构和管理制度，其中市场开发与管理调度人员多名。公司秉承客户至上的服务宗旨，借助先进的物流服务理念和丰富的物流操作经验，为不同客户量身定做提供专业物流方案和优质、高效的物流服务；从而帮助客户降低成本，提升市场竞争力。公司以雄厚的实力、完善的服务网络、优质的服务质量、专业的服务水平赢得了客户的赞誉。成为了众多外资企业，国内大型生产企业优秀的物流供应商，值得信赖的合作伙伴。
         */

        private String fname;
        private String fcontent;

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getFcontent() {
            return fcontent;
        }

        public void setFcontent(String fcontent) {
            this.fcontent = fcontent;
        }
    }
}
