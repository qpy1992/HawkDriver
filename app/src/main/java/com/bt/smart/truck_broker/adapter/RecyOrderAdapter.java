package com.bt.smart.truck_broker.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.smart.truck_broker.MyApplication;
import com.bt.smart.truck_broker.NetConfig;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.messageInfo.AllOrderListInfo;
import com.bt.smart.truck_broker.utils.CommonUtil;
import com.bt.smart.truck_broker.utils.GlideLoaderUtil;
import com.bt.smart.truck_broker.utils.MyAlertDialogHelper;
import com.bt.smart.truck_broker.utils.ShowCallUtil;
import com.bt.smart.truck_broker.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/11/20 14:07
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class RecyOrderAdapter extends BaseQuickAdapter<AllOrderListInfo.PageListBean, BaseViewHolder> {
    private Context mContext;

    public RecyOrderAdapter(int layoutResId, Context context, List data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final AllOrderListInfo.PageListBean item) {
        //        (ImageView) helper.getView(R.id.img_call)
        helper.setText(R.id.tv_place, item.getFh().replaceAll("市"," ").replaceAll("区"," ").replaceAll("县"," ") + "  →  " + item.getSh().replaceAll("市"," ").replaceAll("区"," ").replaceAll("县"," "));
        helper.setText(R.id.tv_goodsname, item.getGoodsName()+" "+item.getCar_type()+"|"+item.getCar_length());
        helper.setText(R.id.tv_loadtime, "装货时间：" + item.getZh_time().substring(0,10)+" "+item.getZhperiod().substring(0,2));
        helper.setText(R.id.tv_xhtime, "卸货时间：" + item.getXh_time().substring(0,10)+" "+item.getXhperiod().substring(0,2));
        helper.setText(R.id.tv_name, item.getFhName());
        helper.setText(R.id.tv_interval, item.getTime_interval());
        helper.setText(R.id.tv_mark, item.getFnote());
        if(item.getDistance().equals("")){
            helper.setText(R.id.tv_distance, "");
        }else {
            helper.setText(R.id.tv_distance, "约" + item.getDistance()+"km");
        }
        ImageView img_call = helper.getView(R.id.img_call);
        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"3".equals(MyApplication.checkStatus)) {
                    ToastUtils.showToast(mContext, "请先提交资料，认证通过才能联系货主哦！");
                    //弹出dialog提示
                    showCheckWarning();
                    return;
                }
                ShowCallUtil.showCallDialog(mContext, item.getFhTele());
            }
        });
        ImageView iv_man = helper.getView(R.id.iv_man);
        GlideLoaderUtil.showImgWithIcon(mContext, NetConfig.IMG_HEAD + item.getFheadpic(), R.drawable.iman, R.drawable.iman, iv_man);
        ImageView iv_type = helper.getView(R.id.iv_type);
        if(item.getIs_box().equals("0")){
            iv_type.setImageResource(R.drawable.pinche);
        }else{
            iv_type.setImageResource(R.drawable.zhengche);
        }
    }

    private void showCheckWarning() {
        final MyAlertDialogHelper dialogHelper = new MyAlertDialogHelper();
        View view = View.inflate(mContext, R.layout.dialog_check_warning, null);
        dialogHelper.setDIYView(mContext, view);
        dialogHelper.show();
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_sure = view.findViewById(R.id.tv_sure);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHelper.disMiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHelper.disMiss();
            }
        });
    }
}
