package com.xiaoyu.erbao.entries;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaoyu.erbao.ErBaoApplication;
import com.xiaoyu.erbao.R;
import com.xiaoyu.erbao.TemperatureRecord;
import com.xiaoyu.erbao.TemperatureRecordDao;
import com.xiaoyu.erbao.dialogs.TempInputDialog;
import com.xiaoyu.erbao.utils.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by jituo on 16/1/28.
 */
public class TemperatureBatchInputActivity extends Activity implements TempInputDialog.TemperatureInputListener,View.OnClickListener {

    private static final int BATCH_COUNT = 30;
    private ImageView mBack;
    private TextView mTitle;
    private Button mBatchCommit;
    private ListView mListView;
    private DataAdapter mAdapter;
    private List<TemperatureRecord> mListData;
    private TextView mCurrentText;
    private SimpleDateFormat mDateFormat;


    private class UpdateDBTask extends AsyncTask<Void, Void, Void> {

        private TemperatureRecordDao mDao;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDao = ((ErBaoApplication) getApplication()).mDBManager.getTemperatureRecordDao();
            mListData = new ArrayList<>();
            for (int i = 0; i < BATCH_COUNT; i++) {
                TemperatureRecord r = new TemperatureRecord();
                r.setDate(Util.TODAY_VALUE - i);
                r.setTemperature(Util.INVALIDE_VALUE);
                mListData.add(r);
            }
        }

        @Override
        protected Void doInBackground(Void... values) {
            QueryBuilder qb = mDao.queryBuilder();
            List<TemperatureRecord> oldvalues = qb.where(TemperatureRecordDao.Properties.Date.ge(Util.TODAY_VALUE - BATCH_COUNT)).list();
            for (int i = 0; i < oldvalues.size(); i++) {
                for (int j = 0; j < BATCH_COUNT; j++) {
                    if (mListData.get(j).getDate().equals(oldvalues.get(i).getDate())) {
                        mListData.get(j).setTemperature(oldvalues.get(i).getTemperature());
                        break;
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void value) {
            super.onPostExecute(value);
            mAdapter = new DataAdapter();
            mListView.setAdapter(mAdapter);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature_batch_input_activity);
        mTitle = (TextView) findViewById(R.id.title_text);
        mTitle.setText(R.string.temp_batch_input);
        mBack = (ImageView) findViewById(R.id.left_btn);
        mBack.setOnClickListener(this);
        mBatchCommit = (Button) findViewById(R.id.batch_commit);
        mBatchCommit.setOnClickListener(this);
        mDateFormat = new SimpleDateFormat(getString(R.string.format_date));
        mListView = (ListView) findViewById(R.id.data_list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int resId = R.string.temp_input;
                TempInputDialog dlg = new TempInputDialog(TemperatureBatchInputActivity.this,
                        TemperatureBatchInputActivity.this, resId, mListData.get(i).getTemperature(), false);
                dlg.show();
                mCurrentText = (TextView) view.findViewById(R.id.temperature_value);
            }
        });
        new UpdateDBTask().execute();
    }


    private class DataAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int i) {
            return mAdapter.getItem(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.temperature_batch_input_item, null);
            }
            TextView tvDate = (TextView) view.findViewById(R.id.txt_date);
            tvDate.setText(String.valueOf(mDateFormat.format(new Date(mListData.get(i).getDate() * Util.DAY_IN_MILLSECONDS))));
            TextView tvValue = (TextView) view.findViewById(R.id.temperature_value);
            String txt = String.format("%2.1f ℃", mListData.get(i).getTemperature() / 10f);
            tvValue.setText(String.valueOf(mListData.get(i).getTemperature() == Util.INVALIDE_VALUE ? "" : txt));
            return view;
        }
    }

    @Override
    public void onTemperatureInput(int temperatureValue) {
        mCurrentText.setText(String.valueOf(temperatureValue));
        //new UpdateDBTask().execute(temperatureValue);
    }


    public void onClick(View v) {
        if (v == mBack) {
            finish();
        } else if (v ==mBatchCommit) {
            //
            //finish();
        }
    }
}
