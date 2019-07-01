package com.bt.smart.truck_broker.fragment.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.activity.LoginActivity;
import com.bt.smart.truck_broker.activity.userAct.AcceptOrderListActivity;
import com.bt.smart.truck_broker.activity.userAct.AllOrderListActivity;
import com.bt.smart.truck_broker.activity.userAct.AuthenticationActivity;
import com.bt.smart.truck_broker.activity.userAct.BCardActivity;
import com.bt.smart.truck_broker.activity.userAct.MoneyActivity;
import com.bt.smart.truck_broker.messageInfo.LoginInfo;
import com.bt.smart.truck_broker.utils.GlideLoaderUtil;
import com.bt.smart.truck_broker.utils.HttpOkhUtils;
import com.bt.smart.truck_broker.utils.RequestParamsFM;
import com.bt.smart.truck_broker.utils.SpUtils;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Request;


/**
 * @创建者 AndyYan
 * @创建时间 2018/5/22 16:42
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class User_F extends Fragment implements View.OnClickListener {
    private View mRootView;
    private TextView tv_title;
    private SwipeRefreshLayout swiperefresh;
    private ImageView img_head;//头像
    private TextView tv_phone;
    private TextView tv_isCheck;//认证进度
    private TextView tv_checked;//已认证
    private TextView tv_warn;//未通过认证前提示
    private TextView tv_submit;//认证
    private TextView tv_money;//余额
    private LinearLayout linear_money;
    private LinearLayout linear_order;
    private TextView tv_orderNum;//运单数
    private RelativeLayout rtv_address;
    private RelativeLayout rtv_phone;
    private RelativeLayout rtv_serv;
    private RelativeLayout rtv_about;
    private RelativeLayout rtv_exit;//退出登录
    private RelativeLayout rlt_allOrder;//更多订单
    private int REQUEST_MONEY_CODE = 10015;
    private int RESULT_MONEY_CODE = 10016;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.user_f, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        tv_title = mRootView.findViewById(R.id.tv_title);
        swiperefresh = mRootView.findViewById(R.id.swiperefresh);
        img_head = mRootView.findViewById(R.id.img_head);
        tv_phone = mRootView.findViewById(R.id.tv_phone);
        tv_isCheck = mRootView.findViewById(R.id.tv_isCheck);
        tv_checked = mRootView.findViewById(R.id.tv_checked);
        tv_warn = mRootView.findViewById(R.id.tv_warn);
        tv_submit = mRootView.findViewById(R.id.tv_submit);
        tv_orderNum = mRootView.findViewById(R.id.tv_orderNum);
        linear_money = mRootView.findViewById(R.id.linear_money);
        tv_money = mRootView.findViewById(R.id.tv_money);
        linear_order = mRootView.findViewById(R.id.linear_order);
        rtv_address = mRootView.findViewById(R.id.rtv_address);
        rtv_phone = mRootView.findViewById(R.id.rtv_phone);
        rtv_serv = mRootView.findViewById(R.id.rtv_serv);
        rtv_about = mRootView.findViewById(R.id.rtv_about);
        rtv_exit = mRootView.findViewById(R.id.rtv_exit);
        rlt_allOrder = mRootView.findViewById(R.id.rlt_allOrder);
        tv_orderNum.setText(MyApplication.userOrderNum + "单");
        tv_money.setText(MyApplication.money + "元");
    }

    private void initData() {
        tv_title.setText("个人资料");
        if ("".equals(MyApplication.userName)) {
            tv_phone.setText(MyApplication.userPhone);
        } else {
            tv_phone.setText(MyApplication.userName);
        }
        tv_submit.setOnClickListener(this);
        linear_money.setOnClickListener(this);
        linear_order.setOnClickListener(this);
        rtv_address.setOnClickListener(this);
        rtv_phone.setOnClickListener(this);
        rtv_serv.setOnClickListener(this);
        rtv_about.setOnClickListener(this);
        rtv_exit.setOnClickListener(this);
        rlt_allOrder.setOnClickListener(this);
        GlideLoaderUtil.showImgWithIcon(getContext(), NetConfig.IMG_HEAD + MyApplication.userHeadPic, R.drawable.iman, R.drawable.iman, img_head);
        swiperefresh.setColorSchemeColors(getResources().getColor(R.color.blue_icon), getResources().getColor(R.color.yellow_40), getResources().getColor(R.color.red_160));
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //重新登录下获取最新的认证状态
                getNewCheckStatue();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                //跳转身份认证界面
                toSubmitPersonInfo();
                break;
            case R.id.linear_money:
                //跳转余额详情页
//              startActivityForResult(new Intent(getContext(), MoneyActivity.class), REQUEST_MONEY_CODE);
                startActivity(new Intent(getContext(), BCardActivity.class));
                break;
            case R.id.linear_order://查看运输单列表
                Intent intent = new Intent(getContext(), AcceptOrderListActivity.class);
                startActivity(intent);
                break;
            case R.id.rtv_address:
                //跳转收货地址界面
                toCompleteAddress();
                break;
            case R.id.rtv_phone:
                //修改手机号
                changePhone();
                break;
            case R.id.rtv_serv:
                //电话联系客服
                contactService();
                break;
            case R.id.rtv_about:
                //关于我们
                aboutUs();
                break;
            case R.id.rtv_exit:
                //退出登录
                exitLogin();
                break;
            case R.id.rlt_allOrder://跳转订单分类界面
                Intent intentAllOrder = new Intent(getContext(), AllOrderListActivity.class);
                startActivity(intentAllOrder);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        tv_orderNum.setText(MyApplication.userOrderNum + "单");
        //根据认证状态判断
        checkCheckStatues();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_MONEY_CODE == requestCode && RESULT_MONEY_CODE == resultCode) {
            //重新登录下获取最新的认证状态
            getNewCheckStatue();
        }
    }

    private void checkCheckStatues() {
        if ("1".equals(MyApplication.checkStatus)) {
            tv_isCheck.setText("待审核");
            tv_isCheck.setTextColor(getResources().getColor(R.color.yellow));
            tv_warn.setText("您的认证信息已提交，请等待客服审核。");
            tv_submit.setVisibility(View.GONE);
        } else if ("2".equals(MyApplication.checkStatus)) {
            tv_isCheck.setText("退回");
            tv_isCheck.setTextColor(getResources().getColor(R.color.red_30));
            tv_warn.setText("您提交的认证信息被驳回，具体失败原因，我们会通过短信通知您。");
            tv_submit.setVisibility(View.VISIBLE);
        } else if ("3".equals(MyApplication.checkStatus)) {//审核通过
            tv_checked.setVisibility(View.VISIBLE);
            tv_isCheck.setVisibility(View.GONE);
            tv_warn.setVisibility(View.GONE);
            tv_submit.setVisibility(View.GONE);
        } else {
            tv_isCheck.setText("未认证");
            tv_isCheck.setTextColor(getResources().getColor(R.color.red_30));
            tv_submit.setVisibility(View.VISIBLE);
        }
    }

    private void getNewCheckStatue() {
        swiperefresh.setRefreshing(true);
        RequestParamsFM params = new RequestParamsFM();
        params.put("fmobile", MyApplication.userPhone);
        HttpOkhUtils.getInstance().doPostBean(NetConfig.CodeLOGINURL, params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                swiperefresh.setRefreshing(false);
                ToastUtils.showToast(getContext(), "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                swiperefresh.setRefreshing(false);
                if (code != 200) {
                    ToastUtils.showToast(getContext(), "网络错误" + code);
                    return;
                }
                Gson gson = new Gson();
                LoginInfo loginInfo = gson.fromJson(resbody, LoginInfo.class);
                ToastUtils.showToast(getContext(), loginInfo.getMessage());
                if (loginInfo.isOk()) {
                    MyApplication.userToken = loginInfo.getData().getToken();
                    MyApplication.userID = loginInfo.getData().getRegisterDriver().getId();
                    MyApplication.userName = loginInfo.getData().getRegisterDriver().getFname();
                    MyApplication.userPhone = loginInfo.getData().getRegisterDriver().getFmobile();
                    MyApplication.checkStatus = loginInfo.getData().getRegisterDriver().getCheckStatus();
                    MyApplication.userHeadPic = loginInfo.getData().getRegisterDriver().getFphoto();
                    MyApplication.userOrderNum = loginInfo.getData().getOrderno();
                    MyApplication.money = loginInfo.getData().getRegisterDriver().getFaccount();
                    //更改界面UI
                    changeUFUI();
                }
            }
        });
    }

    private void changeUFUI() {
        //根据认证状态判断
        checkCheckStatues();
        if ("".equals(MyApplication.userName)) {
            tv_phone.setText(MyApplication.userPhone);
        } else {
            tv_phone.setText(MyApplication.userName);
        }
        GlideLoaderUtil.showImgWithIcon(getContext(), NetConfig.IMG_HEAD + MyApplication.userHeadPic, R.drawable.iman, R.drawable.iman, img_head);
        tv_orderNum.setText(MyApplication.userOrderNum + "单");
        tv_money.setText(MyApplication.money + "元");
    }

    private void exitLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("温馨提示");
        builder.setMessage("您确定要退出当前登录帐号吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SpUtils.putBoolean(getContext(), "isRem", false);
                MyApplication.isLogin = 0;
                MyApplication.needLocationService = false;
                Intent intent = new Intent();
                intent.setClass(getContext(), LoginActivity.class);
                startActivity(intent);
                ((Activity) getContext()).finish();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();
    }

    private void toSubmitPersonInfo() {
        Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
        startActivity(intent);
    }

    private void toCompleteAddress() {

    }

    private void changePhone() {

    }

    private void contactService() {

    }

    private void aboutUs() {

    }
}
