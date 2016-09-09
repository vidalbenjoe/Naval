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


public class PrelimTopicListFragment extends Fragment {
    ArrayList<HashMap<String, String>> aList;
    LessonListAdapter adapter;
    ListView lessonslistV;

    String[] prelimList = new String[]{
            "Displacement and Buoyancy",
            "Fresh Water Allowance and Statically Stability",
            "Load lines and Draught Marks",
            "Initial of Stability and Angle of Loll",
            "Curves of Statically Stability and movement of the Centre of the gravity",
            "Movement of the Centre of the gravity and its correction"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.lessons_week_layout, viewGroup, false);
        lessonslistV = (ListView) mainView.findViewById(R.id.lessonslistV);

        aList = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < prelimList.length; i++) {
            int start = 1;
            int index = start += i;
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("lessontitle", "" + prelimList[i]);
            hm.put("lessonweek", "Week " + index  );
            aList.add(hm);
        }

        adapter = new LessonListAdapter(getActivity(), aList);
        lessonslistV.setAdapter(adapter);
        return mainView;
    }



}
