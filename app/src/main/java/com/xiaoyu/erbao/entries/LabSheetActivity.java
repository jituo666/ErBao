package com.xiaoyu.erbao.entries;

import android.app.Activity;
import android.os.Bundle;

import com.xiaoyu.erbao.R;

/**
 * Created by jituo on 15/12/27.
 */
public class LabSheetActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temprature_layout);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
