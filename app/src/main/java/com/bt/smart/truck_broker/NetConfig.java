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
//    public static String ROOT = "http://116.62.171.244:8082/yingsu/rest/";
//    public static String ROOT = "http://120.27.3.205/rest/";
    public static String ROOT = "http://172.16.52.63/rest/";
    //    public static String ROOT = "http://2o1b417794.51mypc.cn:39437/rest/";
    //图片地址
//    public static String IMG_HEAD = "http://120.27.3.205/";
    public static String IMG_HEAD = "http://172.16.52.63/";
//    public static String IMG_HEAD = "http://205.168.1.100/yingsu_war_exploded/";

    public static String HEAD = "X-AUTH-TOKEN";

    //获取最新版本apk信息
    public static String GETNEWAPPVERSION = ROOT + "getNewAppVersion";

    //base64上传图片
    public static String PHOTO = ROOT + "registerDriverController/photo";
    public static String FACE = ROOT + "orderController/face";
    public static String PHOTO1 = ROOT + "orderController/photo";
    //获取验证码
    public static String CHECKMESSAGE = ROOT + "tokens/SMScode";
    //注册用户
    public static String REGISTERDRIVER = ROOT + "tokens/registerdriver/reg";
    //修改密码(记得原密码)
    public static String BACKFPASSWORD = ROOT + "backFpassword";
    //修改密码(忘记原密码)
    public static String BACKFPASSWORDBYMOBILE = ROOT + "backFpasswordByMobile";
    //用户登录
    public static String LOGINURL = ROOT + "tokens/registerdriver";
    //验证码登录
    public static String CodeLOGINURL = ROOT + "tokens/registerdriver/code";
    //获取用户信息
    public static String REGISTERINFO = ROOT + "registerInfo";

    //提交司机认证信息
    public static String DRIVERGDCONTROLLER = ROOT + "driverGdController";
    public static String PERSONAUTH = ROOT + "eSignController/personAuth";
    public static String REGISTERDRIVERCONTROLLER = ROOT + "registerDriverController/{id}";
    //平台合同内容
    public static String CONTENT = ROOT + "eSignController/content";
    //用户与平台签署合同
    public static String SIGNWITHPLATFORM = ROOT + "eSignController/signWithPlatform";


    //获取当天货源信息列表
    public static String ALL_ORDER_LIST = ROOT + "orderController/list1";
    //获取当天货源条目详情
    public static String ALL_ORDER_DETAIL = ROOT + "orderController";
    //司机接单
    public static String DRIVERORDERCONTROLLER = ROOT + "driverOrderController";
    //司机签署协议
    public static String DRIVER = ROOT + "eSignController/driver";
    //根据ID司机订单表信息
    public static String DRIVERORDERCONTROLLER_ORDER = ROOT + "driverOrderController/order";
    public static String DRIVERORDERCONTROLLER_ADDRECORD = ROOT + "driverOrderController/addRecord";


    //根据司机id获取个人线路
    public static String DRIVERJOURNEYCONTROLLER = ROOT + "driverJourneyController";

    //获取省市区
    public static String REGIONSELECT = ROOT + "registerDriverController/regionSelect";

    //新增订单轨迹
    public static String ADDTRAIL = ROOT + "orderController/addTrail";

    //查询绑定的银行卡
    public static String BANKCARD = ROOT + "yqzlController/bindlist";

    //提现到银行卡
    public static String WITHDRAW = ROOT + "yqzlController/withdraw";

    //银行卡三要素校验
    public static String B_C_CHECK = ROOT + "yqzlController/bind";

    //银行卡删除
    public static String BCDEL = ROOT + "yqzlController/cancelBind";

    //银行卡绑定手机验证
    public static String CODEVALID = ROOT + "yqzlController/codevalid";//未做校验的绑定

    //微信支付统一下单
    public static String WX = ROOT + "wxController/wxOrder";

    //    public static String WX_APPID = "wxd97fbc0c709a9bed";
    public static String WX_APPID = "wx6ae170b6547a71c4";

    //支付宝参数拼接
    public static String ALIPAY = ROOT + "alipayController/alipayOrder";

    public static String PAYACCOUNTDRIVER_LIST = ROOT + "pADriverController/listbyid";

    //获取认证uri
    public static String URI = ROOT + "eSignController/uri";

    //更换头像
    public static String UPDATEHEADPIC = ROOT + "zRegisterController/updateHeadPic";
}
