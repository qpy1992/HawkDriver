package com.bt.smart.truck_broker.util.UpApkDataFile;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bt.smart.truck_broker.R;


/**
 * @创建者 AndyYan
 * @创建时间 2018/10/29 15:37
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class ConfirmDialog extends Dialog {
    private OnClickListener listener;
    private TextView title;
    private TextView content;
    private TextView sureBtn;
    private TextView cancelBtn;

    public ConfirmDialog(@NonNull Context context, OnClickListener listener) {
        super(context, R.style.ConfirmDialogTheme);
        this.listener = listener;
        initConfirmDialog();
    }

    private void initConfirmDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm, null);
        sureBtn = (TextView) mView.findViewById(R.id.dialog_confirm_sure);
        cancelBtn = (TextView) mView.findViewById(R.id.dialog_confirm_cancle);
        title = (TextView) mView.findViewById(R.id.dialog_confirm_title);
        content = (TextView) mView.findViewById(R.id.dialog_confirm_cont);

        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.sureBtnClick();
                ConfirmDialog.this.cancel(); //关闭对话框
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.cancelBtnClick();
                ConfirmDialog.this.cancel(); //关闭对话框
            }
        });
        super.setContentView(mView);
    }

    public ConfirmDialog setTitle(String s) {
        title.setText(s);
        return this;
    }

    public ConfirmDialog setContent(String s) {
        content.setText(s);
        return this;
    }

    public interface OnClickListener {
        void sureBtnClick();

        void cancelBtnClick();
    }
}
