package com.xiaoyu.erbao.entries;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.xiaoyu.erbao.R;
import com.xiaoyu.erbao.adapters.ClassrommAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jituo on 15/12/18.
 */
public class FragmentMyClassroom extends Fragment {


    private ExpandableListView mKnowledgeList;
    private BaseExpandableListAdapter mKnowlegeAdapter;
    private ArrayList<String> mGroups;

    private List<List<String>> mChilddren;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void loadKnowlegeList() {
        mGroups = new ArrayList<String>();
        mChilddren = new ArrayList<List<String>>();
        ;
        for (int i = 0; i < 3; i++) {
            ArrayList<String> child = new ArrayList<String>();
            for (int j = 0; j < 15; j++) {
                child.add("child" + j);
            }
            mGroups.add("group" + i);
            mChilddren.add(child);
        }
        mKnowlegeAdapter = new ClassrommAdapter(getActivity(),mGroups,mChilddren);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_classroom, null);
        mKnowledgeList = (ExpandableListView) rootView.findViewById(R.id.knowlege_list);
        mKnowledgeList.setCacheColorHint(0);
        mKnowledgeList.setGroupIndicator(null);
//        mKnowledgeList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
//                return true;
//            }
//        });
        loadKnowlegeList();
        mKnowledgeList.setAdapter(mKnowlegeAdapter);
        for(int i=0 ; i < mKnowlegeAdapter.getGroupCount();i++) {
            mKnowledgeList.expandGroup(i);
        }
        return rootView;
    }
}
