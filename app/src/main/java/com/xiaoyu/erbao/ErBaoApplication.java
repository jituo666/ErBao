package com.xiaoyu.erbao;

import android.app.Application;

import com.xiaoyu.erbao.core.DBManager;

public class ErBaoApplication extends Application {

    public DBManager mDBManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mDBManager = DBManager.getInstance(this);
    }

}
