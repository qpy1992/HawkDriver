package com.bt.smart.truck_broker;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/28 8:48
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class NetConfig {
    //服务器总地址
//    public static String ROOT     = "http://116.62.171.244:8082/yingsu/rest/";
        public static String ROOT     = "http://192.168.43.63:8080/rest/";
    //图片地址
    public static String IMG_HEAD = "http://116.62.171.244:8082/yingsu/";
    //    public static String IMG_HEAD = "http://205.168.1.100/yingsu_war_exploded/";


    //获取最新版本apk信息
    public static String GETNEWAPPVERSION = ROOT + "getNewAppVersion";

    //base64上传图片
    public static String PHOTO                 = ROOT + "registerDriverController/photo";
    //获取验证码
    public static String CHECKMESSAGE          = ROOT + "tokens/SMScode";
    //注册用户
    public static String REGISTERDRIVER        = ROOT + "tokens/registerdriver/reg";
    //修改密码(记得原密码)
    public static String BACKFPASSWORD         = ROOT + "backFpassword";
    //修改密码(忘记原密码)
    public static String BACKFPASSWORDBYMOBILE = ROOT + "backFpasswordByMobile";
    //用户登录
    public static String LOGINURL              = ROOT + "tokens/registerdriver";
    //验证码登录
    public static String CodeLOGINURL          = ROOT + "tokens/registerdriver/code";
    //获取用户信息
    public static String REGISTERINFO          = ROOT + "registerInfo";

    //提交司机认证信息
    public static String DRIVERGDCONTROLLER       = ROOT + "driverGdController";
    public static String REGISTERDRIVERCONTROLLER = ROOT + "registerDriverController/{id}";

    //获取当天货源信息列表
    public static String ALL_ORDER_LIST                  = ROOT + "orderController/list";
    //获取当天货源条目详情
    public static String ALL_ORDER_DETAIL                = ROOT + "orderController";
    //司机接单
    public static String DRIVERORDERCONTROLLER           = ROOT + "driverOrderController";
    //根据ID司机订单表信息
    public static String DRIVERORDERCONTROLLER_ORDER     = ROOT + "driverOrderController/order";
    public static String DRIVERORDERCONTROLLER_ADDRECORD = ROOT + "driverOrderController/addRecord";


    //根据司机id获取个人线路
    public static String DRIVERJOURNEYCONTROLLER = ROOT + "driverJourneyController";

    //获取省市区
    public static String REGIONSELECT = ROOT + "registerDriverController/regionSelect";

    //新增订单轨迹
    public static String ADDTRAIL = ROOT + "orderController/addTrail";

    //查询绑定的银行卡
    public static String BANKCARD = ROOT + "registerDriverController/bindlist";

    //绑定银行卡
    public static String BIND = ROOT + "registerDriverController/bind";
}
