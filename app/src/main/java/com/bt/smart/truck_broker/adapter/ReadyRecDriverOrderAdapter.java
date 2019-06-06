package com.bt.smart.truck_broker.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.messageInfo.ReadyRecOrderInfo;
import com.bt.smart.truck_broker.utils.MyAlertDialogHelper;
import com.bt.smart.truck_broker.utils.ShowCallUtil;
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

public class ReadyRecDriverOrderAdapter extends BaseQuickAdapter<ReadyRecOrderInfo.DataBean, BaseViewHolder> {
    private Context mContext;
    private String  selecPhone;//选择的电话

    public ReadyRecDriverOrderAdapter(int layoutResId, Context context, List<ReadyRecOrderInfo.DataBean> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ReadyRecOrderInfo.DataBean item) {
        //        (ImageView) helper.getView(R.id.img_call)
        helper.setText(R.id.tv_place, item.getOrigin() + "  →  " + item.getDestination());
        helper.setText(R.id.tv_goodsname, item.getGoods_name()+" "+item.getCar_type());
        helper.setText(R.id.tv_loadtime, "装货时间：" + item.getZh_time());
        helper.setText(R.id.tv_name, item.getFh_name() + "\n电话：" + item.getFh_telephone());
        helper.setText(R.id.tv_talk, "收货人：" + item.getSh_name() + "\n电话：" + item.getSh_telephone());
        ImageView img_call = (ImageView) helper.getView(R.id.img_call);
        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出dialog，让司机选择拨打发货还是收货人电话
                final MyAlertDialogHelper dialogHelper = new MyAlertDialogHelper();
                View diaView = View.inflate(mContext, R.layout.dialog_choice_phone, null);
                dialogHelper.setDIYView(mContext, diaView);
                dialogHelper.show();
                final RadioButton radbt_fh = diaView.findViewById(R.id.radbt_fh);
                final RadioButton radbt_sh = diaView.findViewById(R.id.radbt_sh);
                TextView tv_fhPhone = diaView.findViewById(R.id.tv_fhPhone);
                TextView tv_shPhone = diaView.findViewById(R.id.tv_shPhone);
                TextView tv_cancel = diaView.findViewById(R.id.tv_cancel);
                TextView tv_sure = diaView.findViewById(R.id.tv_sure);
                tv_fhPhone.setText(item.getFh_telephone());
                tv_shPhone.setText(item.getSh_telephone());
                radbt_fh.setChecked(true);
                selecPhone = item.getFh_telephone();
                radbt_fh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            selecPhone = item.getFh_telephone();
                            radbt_sh.setChecked(false);
                        }
                    }
                });
                radbt_sh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            selecPhone = item.getSh_telephone();
                            radbt_fh.setChecked(false);
                        }
                    }
                });

                tv_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogHelper.disMiss();
                        ShowCallUtil.showCallDialog(mContext, selecPhone);
                    }
                });
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogHelper.disMiss();
                    }
                });
            }
        });
    }

}
