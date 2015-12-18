package com.xiaoyu.erbao.entries;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.xiaoyu.erbao.R;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private FragmentManager mFragmentManager;
    private TextView mTitleView;
    private TextView mBtnFirstPage;
    private TextView mBtnMyRecord;
    private TextView mBtnMyClassRoom;
    private TextView mBtnMyCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getFragmentManager();
        mTitleView = (TextView) findViewById(R.id.main_title);
        //
        mBtnFirstPage = (TextView) findViewById(R.id.firstPage);
        mBtnFirstPage.setOnClickListener(this);
        mBtnMyRecord = (TextView) findViewById(R.id.myRecord);
        mBtnMyRecord.setOnClickListener(this);
        mBtnMyClassRoom = (TextView) findViewById(R.id.myClassroom);
        mBtnMyClassRoom.setOnClickListener(this);
        mBtnMyCenter = (TextView) findViewById(R.id.myCenter);
        mBtnMyCenter.setOnClickListener(this);
        //

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.fgContainer, new FragmentFirstPage());
        transaction.commit();
        mTitleView.setText(R.string.fragment_first_page);
    }


    private void setSelectedPage(int title, View slelctedView) {
        mTitleView.setText(title);
        mBtnFirstPage.setSelected(false);
        mBtnMyRecord.setSelected(false);
        mBtnMyClassRoom.setSelected(false);
        mBtnMyCenter.setSelected(false);
        slelctedView.setSelected(true);
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnFirstPage) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.fgContainer, new FragmentFirstPage());
            transaction.commit();
            setSelectedPage(R.string.fragment_first_page, mBtnFirstPage);
        } else if (view == mBtnMyRecord) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.fgContainer, new FragmentMyRecord());
            transaction.commit();
            setSelectedPage(R.string.fragment_my_record, mBtnMyRecord);
        } else if (view == mBtnMyClassRoom) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.fgContainer, new FragmentMyClassromm());
            transaction.commit();
            setSelectedPage(R.string.fragment_my_classroom, mBtnMyClassRoom);
        } else if (view == mBtnMyCenter) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.fgContainer, new FragmentMyCenter());
            transaction.commit();
            setSelectedPage(R.string.fragment_my_center, mBtnMyCenter);
        }
    }
}
