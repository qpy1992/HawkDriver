package com.bt.smart.truck_broker.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.messageInfo.ChioceAdapterContentInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/9 21:07
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class RecyPlaceAdapter extends BaseQuickAdapter<ChioceAdapterContentInfo, BaseViewHolder> {

    private Context mContext;

    public RecyPlaceAdapter(int layoutResId, Context context, List data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ChioceAdapterContentInfo item) {
        //(ImageView) helper.getView(R.id.img_kind)
        helper.setText(R.id.tv_place, item.getCont());
        TextView tv_place = helper.getView(R.id.tv_place);
        LinearLayout linear_bg = helper.getView(R.id.linear_bg);
        if (item.isChioce()) {
            linear_bg.setBackgroundColor(mContext.getResources().getColor(R.color.blue_10));
            tv_place.setTextColor(mContext.getResources().getColor(R.color.blue_87));
        } else {
            linear_bg.setBackgroundColor(mContext.getResources().getColor(R.color.transplant));
            tv_place.setTextColor(mContext.getResources().getColor(R.color.word_gray));
        }
    }
}
