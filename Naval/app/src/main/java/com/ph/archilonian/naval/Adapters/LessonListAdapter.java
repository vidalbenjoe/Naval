package com.ph.archilonian.naval.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ph.archilonian.naval.Fragments.Lessons.LessonIntroActivity;
import com.ph.archilonian.naval.R;

import java.util.ArrayList;
import java.util.HashMap;

public class LessonListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>>
            data;
    Intent i;

    public LessonListAdapter(Context context,
                             ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lesson_list_layout, viewGroup, false);
            holder.lessonWeekTitle = (TextView) view.findViewById(R.id.lessonWeekTitle);
            holder.weekNumTxtV = (TextView) view.findViewById(R.id.weekNumTxtV);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.resultp = data.get(position);
        holder.weekNumTxtV.setText(holder.resultp.get("lessonweek"));
        holder.lessonWeekTitle.setText(holder.resultp.get("lessontitle"));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Get the position
                holder.resultp = data.get(position);

                i = new Intent(context, LessonIntroActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("lessonweek", holder.resultp.get("lessonweek"));
                bundle.putString("lessontitle", holder.resultp.get("lessontitle"));

                i.putExtras(bundle);
                context.startActivity(i);
            }
        });


        return view;

    }

    public class ViewHolder {
        TextView lessonWeekTitle;
        TextView weekNumTxtV;
        HashMap<String, String> resultp = new HashMap<String, String>();
    }
}