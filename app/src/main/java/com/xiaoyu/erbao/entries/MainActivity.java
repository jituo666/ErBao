package com.xiaoyu.erbao.entries;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import com.xiaoyu.erbao.R;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private FragmentManager mFragmentManager;

    private TextView mBtnFirstPage;
    private TextView mBtnMyRecord;
    private TextView mBtnMyClassRoom;
    private TextView mBtnMyCenter;
    //
    private FragmentFirstPage mFragmentFirstPage;
    private FragmentMyRecord mFragmentMyRecord;
    private FragmentMyClassroom mFragmentMyClassroom;
    private FragmentMyCenter mFragmentMyCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getFragmentManager();
        //
        mBtnFirstPage = (TextView) findViewById(R.id.firstPage);
        mBtnFirstPage.setOnClickListener(this);
        mBtnMyRecord = (TextView) findViewById(R.id.myRecord);
        mBtnMyRecord.setOnClickListener(this);
        mBtnMyClassRoom = (TextView) findViewById(R.id.myClassroom);
        mBtnMyClassRoom.setOnClickListener(this);
        mBtnMyCenter = (TextView) findViewById(R.id.myCenter);
        mBtnMyCenter.setOnClickListener(this);
        //初始化,加载首页
        mFragmentFirstPage = new FragmentFirstPage();
        mFragmentMyRecord = new FragmentMyRecord();
        mFragmentMyClassroom = new FragmentMyClassroom();
        mFragmentMyCenter = new FragmentMyCenter();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.fgContainer, mFragmentFirstPage);
        transaction.commit();
        mBtnFirstPage.setSelected(true);
    }

    private void showFragment(Fragment f,View selectedView) {
        if (!f.isAdded()) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.add(R.id.fgContainer, f);
            transaction.commit();
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.hide(mFragmentFirstPage).hide(mFragmentMyRecord).hide(mFragmentMyClassroom).hide(mFragmentMyCenter)
                .show(f).commit();
        //
        mBtnFirstPage.setSelected(false);
        mBtnMyRecord.setSelected(false);
        mBtnMyClassRoom.setSelected(false);
        mBtnMyCenter.setSelected(false);
        selectedView.setSelected(true);
    }


    @Override
    public void onClick(View view) {
        if (view == mBtnFirstPage) {
            showFragment(mFragmentFirstPage, mBtnFirstPage);
        } else if (view == mBtnMyRecord) {
            showFragment(mFragmentMyRecord, mBtnMyRecord);
        } else if (view == mBtnMyClassRoom) {
            showFragment(mFragmentMyClassroom, mBtnMyClassRoom);
        } else if (view == mBtnMyCenter) {
            showFragment(mFragmentMyCenter,mBtnMyCenter);
        }
    }
}
