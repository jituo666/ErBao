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
    private static final int ITEM_KNOWLEGE_VIEW = 4;
    private Activity mParentActivity;
    private LayoutInflater mInflater;
    private ArrayList<String> mKnowledgeData;
    private ArrayList<String> mNewsData;

    public FirstPageAdapter(Activity activity, ArrayList<String> knowledges, ArrayList<String> newsList) {
        mParentActivity = activity;
        mInflater = mParentActivity.getLayoutInflater();
        mKnowledgeData = knowledges;
        mNewsData = newsList;
    }

    @Override
    public int getCount() {
        return mNewsData.size() + 5;
    }

    @Override
    public Object getItem(int i) {
        if (i > 4) {
            return mNewsData.get(i - (ITEM_KNOWLEGE_VIEW + 1));
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
            return viewSimple;
        } else if (i == ITEM_TOOL_BOX) {
            viewSimple = (ViewGroup) mInflater.inflate(R.layout.fp_list_item_toolbox, null);
            return viewSimple;
        } else if (i <= ITEM_KNOWLEGE_VIEW) {
            viewSimple = (ViewGroup) mInflater.inflate(R.layout.fp_list_item_knowlege, null);
            TextView knowledgeTitle = (TextView) viewSimple.findViewById(R.id.knowledge_title);
            knowledgeTitle.setText(mKnowledgeData.get(i - (ITEM_TOOL_BOX + 1)));
            return viewSimple;
        } else {
            if (view ==null || (view != null && (view.findViewById(R.id.news_title) == null))) {
                view = mInflater.inflate(R.layout.fp_list_item_news, null);
            }

            TextView newsTitle = (TextView) view.findViewById(R.id.news_title);
            newsTitle.setText(mNewsData.get(i - (ITEM_KNOWLEGE_VIEW + 1)));
            return view;
        }
    }
}
