package com.xiaoyu.erbao.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.xiaoyu.erbao.R;

import java.util.List;

/**
 * Created by jituo on 16/1/12.
 */
public class ClassrommAdapter extends BaseExpandableListAdapter {
    List<List<String>> mChildren;
    List<String> mGroups;
    LayoutInflater mLayoutInfalter;
    Context mContext;

    public ClassrommAdapter(Activity c, List<String> groups, List<List<String>> children) {
        mChildren = children;
        mGroups = groups;
        mLayoutInfalter = c.getLayoutInflater();
        mContext = c;
    }

    @Override
    public Object getChild(int i, int i1) {
        return mChildren.get(i).get(i1);
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        view = mLayoutInfalter.inflate(R.layout.knowlege_list_item_child, null);
        TextView t = (TextView) view.findViewById(R.id.child_title);
        t.setText(mChildren.get(i).get(i1));

        return view;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        view = mLayoutInfalter.inflate(R.layout.knowlege_list_item_group, null);
        TextView t = (TextView) view.findViewById(R.id.group_title);
        t.setText(mGroups.get(i));
        if (b) {
            Drawable d = mContext.getResources().getDrawable(R.drawable.list_group_expand_up);
            d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
            t.setCompoundDrawables(null, null, d, null);
        } else {
            Drawable d = mContext.getResources().getDrawable(R.drawable.list_group_expand_down);
            d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
            t.setCompoundDrawables(null, null, d, null);
        }
        return view;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mChildren.size();
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public Object getGroup(int i) {
        return mGroups.get(i);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
