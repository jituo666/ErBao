package com.xiaoyu.erbao.entries;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyu.erbao.ErBaoApplication;
import com.xiaoyu.erbao.R;
import com.xiaoyu.erbao.TemperatureRecord;
import com.xiaoyu.erbao.TemperatureRecordDao;
import com.xiaoyu.erbao.cctrls.TempView;
import com.xiaoyu.erbao.cctrls.TempViewLayout;
import com.xiaoyu.erbao.dialogs.TempInputDialog;
import com.xiaoyu.erbao.utils.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jituo on 15/12/27.
 */
public class TempratureActivity extends Activity implements TempView.ScrollListener,
        View.OnClickListener, TempInputDialog.TemperatureInputListener {

    private static final int TEMPERATURE_ARRAY_OFFSET = TempViewLayout.PRE_SHOW_DAYS + 1;
    private ImageView mBack;
    private TextView mTitle;
    private TextView mInput;
    private TextView mBatchInput;
    private ImageView mHelp;
    private TempView mTempView;
    private SimpleDateFormat mDateFormat;
    private int mTemperatures[];
    private int mOffsetToCurrentDay;


    private class UpdateDBTask extends AsyncTask<Integer, Void, Boolean> {
        private TemperatureRecordDao mDao;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDao = ((ErBaoApplication) getApplication()).mDBManager.getTemperatureRecordDao();
        }

        @Override
        protected Boolean doInBackground(Integer... values) {

            boolean ret = false;
            if (values[0] > 0) {
                TemperatureRecord r = new TemperatureRecord();
                r.setDate((System.currentTimeMillis() / Util.DAY_IN_MILLSECONDS) - mOffsetToCurrentDay);
                r.setTemperature(values[0]);
                mDao.insertOrReplaceInTx(r);
                ret = true;
            }

            List<TemperatureRecord> result = mDao.queryRaw("", new String[]{});
            for (int i = 0; i < result.size(); i++) {
                TemperatureRecord r = result.get(i);
                long day = r.getDate();
                int index = TEMPERATURE_ARRAY_OFFSET + (int) ((System.currentTimeMillis() / Util.DAY_IN_MILLSECONDS - day));
                mTemperatures[index] = r.getTemperature();
            }
            return ret;
        }

        @Override
        protected void onPostExecute(Boolean value) {
            super.onPostExecute(value);
            mTempView.setTemperature(mTemperatures);
            if (value) {
                mInput.setText(R.string.temp_update_tip);
            }
        }

    }


    private void initData() {
        mDateFormat = new SimpleDateFormat(getString(R.string.format_date));
        mTemperatures = new int[TempViewLayout.TOTAL_SHOW_DAYS + 1];
        for (int i = 0; i < mTemperatures.length; i++) {
            mTemperatures[i] = Util.INVALIDE_VALUE;
        }
    }

    private void initView() {

        mTitle = (TextView) findViewById(R.id.title_text);
        mTitle.setText(mDateFormat.format(new Date(System.currentTimeMillis())));
        mBack = (ImageView) findViewById(R.id.left_btn);
        mBack.setOnClickListener(this);

        mTempView = (TempView) findViewById(R.id.temp_view);
        mTempView.setmScrollListener(this);

        mInput = (TextView) findViewById(R.id.temperature_input);
        mInput.setOnClickListener(this);

        mBatchInput = (TextView) findViewById(R.id.temperature_batch_input);
        mBatchInput.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature_activity);
        initData();
        initView();
        new UpdateDBTask().execute(0);
    }


    @Override
    public void onTemperatureInput(int temperatureValue) {
        new UpdateDBTask().execute(temperatureValue);
    }

    @Override
    public void onScrollTo(int offsetToToday) {
        mOffsetToCurrentDay = offsetToToday;
        //设置当前日期
        String date = mDateFormat.format(new Date(System.currentTimeMillis() - offsetToToday * Util.DAY_IN_MILLSECONDS));
        mTitle.setText(date);
        //设置输入按钮文字
        if (mTemperatures[offsetToToday + TEMPERATURE_ARRAY_OFFSET] == Util.INVALIDE_VALUE)
            mInput.setText(R.string.temp_input_tip);
        else
            mInput.setText(R.string.temp_update_tip);
    }

    @Override
    public void onClick(View v) {
        if (v == mInput) {
            //温度输入对话框
            int resId;
            int temperatureValue = mTemperatures[mOffsetToCurrentDay + TEMPERATURE_ARRAY_OFFSET];
            if (temperatureValue == Util.INVALIDE_VALUE)
                resId = R.string.temp_input;
            else
                resId = R.string.temp_update;
            TempInputDialog dlg = new TempInputDialog(this, this, resId, temperatureValue,true);
            dlg.show();
        } else if (v == mBack) {
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
