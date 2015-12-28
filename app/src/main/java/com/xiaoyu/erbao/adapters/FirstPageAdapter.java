package com.xiaoyu.erbao.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaoyu.erbao.R;

import java.util.ArrayList;

/**
 * Created by jituo on 15/12/19.
 */
public class FirstPageAdapter extends BaseAdapter {
    private static final int ITEM_HEADER_VIEW = 0;
    private static final int ITEM_TOOL_BOX = 1;
    private static final int KNOWLEGE_COUNT = 2;
    private Activity mParentActivity;
    private LayoutInflater mInflater;
    private ArrayList<String> mKnowledgeData;
    private ArrayList<String> mNewsData;
    private View.OnClickListener mListner;

    public FirstPageAdapter(Activity activity, ArrayList<String> knowledges, ArrayList<String> newsList,View.OnClickListener l) {
        mParentActivity = activity;
        mInflater = mParentActivity.getLayoutInflater();
        mKnowledgeData = knowledges;
        mNewsData = newsList;
        mListner = l;
    }

    @Override
    public int getCount() {
        return mNewsData.size() + 4;
    }

    @Override
    public Object getItem(int i) {

        if (i >= (KNOWLEGE_COUNT +2)) {
            return mNewsData.get(i - (KNOWLEGE_COUNT +2));
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
        } else if (i < (KNOWLEGE_COUNT+2)) {
            viewSimple = (ViewGroup) mInflater.inflate(R.layout.fp_list_item_knowlege, null);
            TextView knowledgeTitle = (TextView) viewSimple.findViewById(R.id.knowledge_title);
            knowledgeTitle.setText(mKnowledgeData.get(i - (ITEM_TOOL_BOX + 1)));
            return viewSimple;
        } else {
            if (view ==null || (view != null && (view.findViewById(R.id.news_title) == null))) {
                view = mInflater.inflate(R.layout.fp_list_item_news, null);
            }
            return view;
        }
    }

    private void initHeaderView(ViewGroup rootView){

    }

    private void initToolboxView(ViewGroup rootView){
        ViewGroup tool_1 = (ViewGroup)rootView.findViewById(R.id.tool_1);
        tool_1.setOnClickListener(mListner);
        ViewGroup tool_2 = (ViewGroup)rootView.findViewById(R.id.tool_2);
        tool_2.setOnClickListener(mListner);
        ViewGroup tool_3 = (ViewGroup)rootView.findViewById(R.id.tool_3);
        tool_3.setOnClickListener(mListner);
        ViewGroup tool_4 = (ViewGroup)rootView.findViewById(R.id.tool_4);
        tool_4.setOnClickListener(mListner);
    }
}
