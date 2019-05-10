package com.bt.smart.truck_broker.activity.userAct;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bt.smart.truck_broker.R;

public class RechargeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView re_back;
    EditText re_et;
    Button btn_re;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        setViews();
        setListeners();
    }

    protected void setViews(){
        re_back = findViewById(R.id.re_back);
        re_et = findViewById(R.id.re_et);
        btn_re = findViewById(R.id.btn_re);
    }

    protected void setListeners(){
        re_back.setOnClickListener(this);
        btn_re.setOnClickListener(this);
        re_et.setFilters(new InputFilter[]{lengthFilter});
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.re_back:
            finish();
            break;
            case R.id.btn_re:
            //选择支付方式
            break;
        }
    }

    private InputFilter lengthFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            // source:当前输入的字符
            // start:输入字符的开始位置
            // end:输入字符的结束位置
            // dest：当前已显示的内容
            // dstart:当前光标开始位置
            // dent:当前光标结束位置
            //Log.e("", "source=" + source + ",start=" + start + ",end=" + end + ",dest=" + dest.toString() + ",dstart=" + dstart + ",dend=" + dend);
            if (dest.length() == 0 && source.equals(".")) {
                return "0.";
            }
            String dValue = dest.toString();
            String[] splitArray = dValue.split("\\.");
            if (splitArray.length > 1) {
                String dotValue = splitArray[1];
//                if (dotValue.length() == 2) {//输入框小数的位数是2的情况，整个输入框都不允许输入
//                    return "";
//                }
                if (dotValue.length() == 2 && dest.length() - dstart < 3){ //输入框小数的位数是2的情况时小数位不可以输入，整数位可以正常输入
                    return "";
                }
            }
            return null;
        }
    };
}
