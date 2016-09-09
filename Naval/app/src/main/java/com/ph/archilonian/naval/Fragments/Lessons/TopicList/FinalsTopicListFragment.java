package com.ph.archilonian.naval.Fragments.Lessons.TopicList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ph.archilonian.naval.Adapters.LessonListAdapter;
import com.ph.archilonian.naval.R;

import java.util.ArrayList;
import java.util.HashMap;

public class FinalsTopicListFragment extends Fragment {
    ArrayList<HashMap<String, String>> aList;
    LessonListAdapter adapter;
    ListView lessonslistV;

    String[] finalsList = new String[]{
            "Ship Stress Lessons",
            "Hull Structure",
            "Hull Structure and Bow and Stern Region",
            "Fittings and Rudder",
            "Proper Use of Knots, Splices and Stopper objectives"
    };

    String[] finalWeek = new String[]{
            "12 - 13",
            "14",
            "15",
            "16 -17",
            "18"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.lessons_week_layout, viewGroup, false);
        lessonslistV = (ListView) mainView.findViewById(R.id.lessonslistV);

        aList = new ArrayList<HashMap<String, String>>();
        loadFinalWeek();
        adapter = new LessonListAdapter(getActivity(), aList);
        lessonslistV.setAdapter(adapter);
        return mainView;
    }

    void loadFinalWeek() {

        for (int i = 0; i < finalsList.length; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("lessontitle", "" + finalsList[i]);
            hm.put("lessonweek", "Week " + finalWeek[i]);
            aList.add(hm);
            Log.i("HAM:", String.valueOf(hm));
        }


    }

}
