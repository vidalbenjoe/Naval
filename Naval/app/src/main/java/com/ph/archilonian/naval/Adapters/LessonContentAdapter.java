package com.ph.archilonian.naval.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.ph.archilonian.naval.Fragments.Lessons.FragmentContent;

import java.util.ArrayList;
import java.util.HashMap;



public class LessonContentAdapter extends FragmentStatePagerAdapter {
    public static int PAGE_COUNT;
    ArrayList<HashMap<String, String>> arrayList;
    FragmentContent fc = new FragmentContent();
    /**
     * Constructor of the class
     */
    public LessonContentAdapter(FragmentManager fm, ArrayList<HashMap<String, String>> arrayList) {
        super(fm);
        this.arrayList = arrayList;
        PAGE_COUNT = this.arrayList.size();
    }

    /**
     * 0
     * This method will be invoked when a page is requested to create
     */
    @Override
    public Fragment getItem(int arg0) {
        FragmentContent myFragment = new FragmentContent();
        Bundle data = new Bundle();
        try {
            data.putString("Semester", arrayList.get(arg0).get("Semester"));
            data.putString("Week", arrayList.get(arg0).get("Week"));
            data.putString("Title", arrayList.get(arg0).get("Title"));
            data.putString("Content", arrayList.get(arg0).get("Content"));
            data.putString("position", String.valueOf(arg0));
            Log.i("GetItemPos:", String.valueOf(arg0));
            fc.currentPage = arg0;
//            CustomUtils.setQuestionCategory(arrayList.get(arg0).get("category"));
        } catch (Exception e) {

            data.putString("Semester","");
            data.putString("Week", "");
            data.putString("Title", "");
            data.putString("Content", "");
            data.putString("position", String.valueOf(arg0));
        }

        myFragment.setArguments(data);
        return myFragment;
    }

    /**
     * Returns the number of pages
     */
    @Override
    public int getCount() {
        return PAGE_COUNT + 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page #" + (position +1);
    }


}