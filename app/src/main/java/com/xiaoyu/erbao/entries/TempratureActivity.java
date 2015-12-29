package com.xiaoyu.erbao.entries;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyu.erbao.R;
import com.xiaoyu.erbao.cctrls.TempView;
import com.xiaoyu.erbao.utils.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jituo on 15/12/27.
 */
public class TempratureActivity extends Activity implements TempView.ScrollListener{
    private TextView mTitle;
    private ImageView mInput;
    private ImageView mAlarm;
    private ImageView mHelp;
    private  TempView mTempView;
    private SimpleDateFormat mDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temprature_layout);
        mTitle = (TextView)findViewById(R.id.title_text);
        mTempView = (TempView)findViewById(R.id.temp_view);
        mTempView.setmScrollListener(this);
        mDateFormat = new SimpleDateFormat(getString(R.string.format_date));
        mTitle.setText(mDateFormat.format(new Date(System.currentTimeMillis())));
    }

    @Override
    public void onScrollTo(int index) {

        String date = mDateFormat.format(new Date(System.currentTimeMillis() -index * Util.DAY_IN_MILLSECONDS));
        mTitle.setText(date);

    }

    @Override
    public void onBackPressed() {
       finish();
    }
}
