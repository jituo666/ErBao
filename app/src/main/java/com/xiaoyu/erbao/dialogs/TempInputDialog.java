package com.xiaoyu.erbao.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyu.erbao.R;
import com.xiaoyu.erbao.entries.TemperatureBatchInputActivity;
import com.xiaoyu.erbao.utils.Util;

/**
 * Created by jituo on 16/1/17.
 */
public class TempInputDialog extends Dialog implements View.OnClickListener {


    private TextView mBatchInput;
    private EditText mTemperature;
    private Button mOk, mCancel;
    private int mTitleResId = R.string.temp_input_tip;
    private boolean mWithBatch;
    private TemperatureInputListener mTemperatureListener;
    private int mTemperatureValue = Util.INVALIDE_VALUE;

    public interface TemperatureInputListener {
        void onTemperatureInput(int temperatureValue);
    }


    /**
     *
     * @param c
     * @param l
     * @param titleRes  标题
     * @param oldTemperature 温度 修改前的值
     * @param withBatchTip 是否显示比例输入提示
     */
    public TempInputDialog(Context c, TemperatureInputListener l, int titleRes, int oldTemperature, boolean withBatchTip) {
        super(c);
        mTitleResId = titleRes;
        mTemperatureValue = oldTemperature;
        mWithBatch = withBatchTip;
        mTemperatureListener = l;
        mWithBatch = withBatchTip;
    }

    public TempInputDialog(Context c, TemperatureInputListener l) {
        super(c);
        mTemperatureListener = l;
        mWithBatch = true;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTitleResId);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.temperature_input_dialog);

        mOk = (Button) findViewById(R.id.btn_ok);
        mOk.setOnClickListener(this);
        mCancel = (Button) findViewById(R.id.btn_cancel);
        mCancel.setOnClickListener(this);
        mBatchInput = (TextView) findViewById(R.id.add_batch);
        mBatchInput.setText(R.string.temp_batch_input);
        mBatchInput.setOnClickListener(this);
        mTemperature = (EditText) findViewById(R.id.temperature_input);
        if (mTemperatureValue != Util.INVALIDE_VALUE) {
            mTemperature.setText(String.format("%2.1f", mTemperatureValue / 10f));
        }
        findViewById(R.id.add_batch).setVisibility(mWithBatch ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v == mCancel) {
            dismiss();
        } else if (v == mOk) {
            String txtInput = mTemperature.getText().toString();
            if (!TextUtils.isEmpty(txtInput) && txtInput.length() <= 5) {
                mTemperatureValue = Math.round(Float.valueOf(txtInput) * 10);
                if (mTemperatureValue >= 350 && mTemperatureValue <= 380) {
                    dismiss();
                    mTemperatureListener.onTemperatureInput(mTemperatureValue);

                } else {
                    Toast.makeText(getContext(), R.string.temp_format_input, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), R.string.temp_format_input, Toast.LENGTH_SHORT).show();
            }
        } else if (v == mBatchInput) {
            Intent it = new Intent();
            it.setClass(getContext(), TemperatureBatchInputActivity.class);
            getContext().startActivity(it);
            dismiss();
        }
    }
}
