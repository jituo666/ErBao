package com.xiaoyu.erbao.entries;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiaoyu.erbao.R;
import com.xiaoyu.erbao.adapters.FirstPageAdapter;

import java.util.ArrayList;

/**
 * Created by jituo on 15/12/18.
 */
public class FragmentFirstPage extends Fragment {

    private PullToRefreshListView mNewsList;
    private FirstPageAdapter mAdapter;

    private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler" };

    private ArrayList<String> mKnowledgeData = new ArrayList<String>();
    private ArrayList<String> mNewsData = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for(int i =0;i< 20;i++) {
            mNewsData.add("News " + i);
            if (i < 3)
                mKnowledgeData.add("Knowledge" + i);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView =(ViewGroup) inflater.inflate(R.layout.fragment_frist_page, null, false);
        mNewsList = (PullToRefreshListView)rootView.findViewById(R.id.first_page_listview);
        mAdapter = new FirstPageAdapter(getActivity(), mKnowledgeData,mNewsData);
        mNewsList.setAdapter(mAdapter);
        //
        return rootView;
    }
}
