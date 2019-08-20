package com.bt.smart.truck_broker.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bt.smart.truck_broker.R;
import com.bt.smart.truck_broker.messageInfo.ChioceAdapterContentInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

public class TagAdapter extends BaseQuickAdapter<ChioceAdapterContentInfo, BaseViewHolder> {
    private Context mContext;

    public TagAdapter(int layoutResId, Context context, List data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ChioceAdapterContentInfo item) {
        helper.setText(R.id.tv_place, item.getCont());
        LinearLayout linear_bg = helper.getView(R.id.linear_bg);
        if (item.isChioce()) {
            linear_bg.setBackground(mContext.getResources().getDrawable(R.drawable.rect_solid_border));
        } else {
            linear_bg.setBackground(mContext.getResources().getDrawable(R.drawable.rect_dashed_border));
        }
    }
}
