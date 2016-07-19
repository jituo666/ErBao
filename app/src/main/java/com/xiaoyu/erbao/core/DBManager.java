package com.xiaoyu.erbao.core;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

import com.xiaoyu.erbao.DaoMaster;
import com.xiaoyu.erbao.DaoSession;
import com.xiaoyu.erbao.DayRecordDao;
import com.xiaoyu.erbao.TemperatureRecordDao;


/**
 * Created by jituo on 16/1/28.
 */
public class DBManager {

    private static DBManager mInstance;
    private SQLiteDatabase mDB;
    private EditText editText;
    private DaoMaster mDAOMaster;
    private DaoSession mDAOSession;
    private Cursor cursor;


    private DBManager(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "erbao", null);
        mDB = helper.getWritableDatabase();
        mDAOMaster = new DaoMaster(mDB);
        mDAOSession = mDAOMaster.newSession();
    }

    public static DBManager  getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBManager(context);
        }
        return mInstance;
    }

    public SQLiteDatabase getDataBase() {
        return mDB;
    }

    public DayRecordDao getDayRecordDao() {
        return mDAOSession.getDayRecordDao();
    }

    public TemperatureRecordDao getTemperatureRecordDao() {
        return mDAOSession.getTemperatureRecordDao();
    }

}
