package com.wadhavekar.healthme;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DayAdapter extends ArrayAdapter<WorkoutCheckedList> {

    private LayoutInflater mInflater;
    private ArrayList<WorkoutCheckedList> workoutCheckedLists;
    private int mViewResourceId;

    public DayAdapter(Context context, int TextViewResourceId, ArrayList<WorkoutCheckedList> wcl){
        super(context,TextViewResourceId,wcl);
        this.workoutCheckedLists=wcl;
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = TextViewResourceId;
    }


    public View getView(int position, View ConvertView , ViewGroup parent){
        ConvertView = mInflater.inflate(mViewResourceId,null);
        WorkoutCheckedList wcl = workoutCheckedLists.get(position);
        if (wcl != null){
            TextView tv_name = (TextView) ConvertView.findViewById(R.id.tv_name);
            TextView tv_sets = (TextView) ConvertView.findViewById(R.id.tv_sets);
            TextView tv_reps = (TextView) ConvertView.findViewById(R.id.tv_reps);



            if (tv_name!=null){
                tv_name.setText(wcl.getWorkoutName());
                //tv_name.setTextColor(Color.parseColor("#FFFFFF"));
            }
            if (tv_sets!=null){
                tv_sets.setText("     " +wcl.getWorkoutSets());
                //tv_sets.setTextColor(Color.parseColor("#FFFFFF"));

            }
            if (tv_reps!=null){
                tv_reps.setText("     "+ wcl.getWorkoutReps());
                //tv_reps.setTextColor(Color.parseColor("#FFFFFF"));

            }


        }

        return ConvertView;
    }
}
