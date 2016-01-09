package com.xiaoyu.erbao.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaoyu.erbao.R;
import com.xiaoyu.erbao.cctrls.CircleDisplay;

import java.util.ArrayList;

/**
 * Created by jituo on 15/12/19.
 */
public class FirstPageAdapter extends BaseAdapter {

    private static final int ITEM_HEADER_VIEW = 0;
    private static final int ITEM_TOOL_BOX = 1;
    private static final int KNOWLEGE_BAR = 2;
    private static final int KNOWLEGE_COUNT = 2;
    private Activity mParentActivity;
    private LayoutInflater mInflater;
    private ArrayList<String> mKnowledgeData;
    private ArrayList<String> mNewsData;
    private View.OnClickListener mListner;
    private boolean mFirstRun;

    public FirstPageAdapter(Activity activity, ArrayList<String> knowledges, ArrayList<String> newsList,View.OnClickListener l) {
        mParentActivity = activity;
        mInflater = mParentActivity.getLayoutInflater();
        mKnowledgeData = knowledges;
        mNewsData = newsList;
        mListner = l;
        mFirstRun = true;
    }

    @Override
    public int getCount() {
        return mNewsData.size() + 5;
    }

    @Override
    public Object getItem(int i) {

        if (i >= (KNOWLEGE_COUNT +3)) {
            return mNewsData.get(i - (KNOWLEGE_COUNT +3));
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewGroup viewSimple;
        if (i == ITEM_HEADER_VIEW) {
            viewSimple = (ViewGroup) mInflater.inflate(R.layout.fp_list_item_current, null);
            initHeaderView(viewSimple);
            return viewSimple;
        } else if (i == ITEM_TOOL_BOX) {
            viewSimple = (ViewGroup) mInflater.inflate(R.layout.fp_list_item_toolbox, null);
            initToolboxView(viewSimple);
            return viewSimple;
        } else if (i == KNOWLEGE_BAR) {
            viewSimple = (ViewGroup) mInflater.inflate(R.layout.fp_list_item_knowlege_bar, null);
            //initToolboxView(viewSimple);
            return viewSimple;
        } else if (i < (KNOWLEGE_COUNT+3)) {
            viewSimple = (ViewGroup) mInflater.inflate(R.layout.fp_list_item_knowlege, null);
            TextView knowledgeTitle = (TextView) viewSimple.findViewById(R.id.knowledge_title);
            //knowledgeTitle.setText(mKnowledgeData.get(i - (ITEM_TOOL_BOX + 1)));
            //show divider
            viewSimple.findViewById(R.id.knowledge_divider).setVisibility(i%2==1?View.VISIBLE:View.INVISIBLE);
            return viewSimple;
        } else { // news
            if (view ==null || (view != null && (view.findViewById(R.id.news_title) == null))) {
                view = mInflater.inflate(R.layout.fp_list_item_news, null);
            }
            return view;
        }
    }

    private void initHeaderView(ViewGroup rootView){
        CircleDisplay mHaoYunLv = (CircleDisplay) rootView.findViewById(R.id.hy_lv);
        mHaoYunLv.setAnimDuration(2000);
        mHaoYunLv.setDimAlpha(80);
        mHaoYunLv.setSelectionListener(null);
        mHaoYunLv.setTouchEnabled(false);
        mHaoYunLv.setUnitString("%");
        mHaoYunLv.setStepSize(1f);
        mHaoYunLv.showValue(65f, 100f, mFirstRun);
        mFirstRun = false;
    }

    private void initToolboxView(ViewGroup rootView){
        View tool_1 = rootView.findViewById(R.id.tool_1);
        tool_1.setOnClickListener(mListner);
        View tool_2 = rootView.findViewById(R.id.tool_2);
        tool_2.setOnClickListener(mListner);
        View tool_3 = rootView.findViewById(R.id.tool_3);
        tool_3.setOnClickListener(mListner);
        View tool_4 = rootView.findViewById(R.id.tool_4);
        tool_4.setOnClickListener(mListner);
    }
}
