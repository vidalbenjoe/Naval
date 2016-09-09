package com.ph.archilonian.naval.Fragments.Lessons.TopicList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ph.archilonian.naval.Adapters.LessonListAdapter;
import com.ph.archilonian.naval.R;

import java.util.ArrayList;
import java.util.HashMap;


public class MidtermTopicListFragment extends Fragment {
    ArrayList<HashMap<String, String>> aList;
    LessonListAdapter adapter;
    ListView lessonslistV;


    String[] midtermList = new String[]{
            "Effects of Slack Tanks",
            "Trim and draught calculations and draught trim tables",
            "Ship dimentions form 1",
            "Ship dimentions form 2"
    };

    String[] midtermWeek = new String[]{
            "7",
            "8",
            "9 - 10",
            "11"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.lessons_week_layout, viewGroup, false);
        lessonslistV = (ListView) mainView.findViewById(R.id.lessonslistV);

        aList = new ArrayList<HashMap<String, String>>();
        loadMidtermWeek();
        adapter = new LessonListAdapter(getActivity(), aList);
        lessonslistV.setAdapter(adapter);
        return mainView;
    }
    void loadMidtermWeek() {

        for (int i = 0; i < midtermList.length; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("lessontitle", "" + midtermList[i]);
            hm.put("lessonweek", "Week " + midtermWeek[i]);
            aList.add(hm);

        }
    }


}
