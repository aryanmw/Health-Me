package com.wadhavekar.healthme.WorkoutPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.wadhavekar.healthme.CheckBoxCheckedListener;
import com.wadhavekar.healthme.R;

import java.util.ArrayList;

public class WorkoutListAdapter extends ArrayAdapter<String> {
    TextView textView;
    EditText et_sets,et_reps;
    CheckBox chk;
    CheckBoxCheckedListener checkedListener;

    private LayoutInflater mInflater;
    private ArrayList<String> workout_info;
    private int mViewResourceId;

    public WorkoutListAdapter(Context context, int TextViewResourceId, ArrayList<String> workout_info){
        super(context,TextViewResourceId,workout_info);
        this.workout_info=workout_info;
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = TextViewResourceId;
    }

    public View getView(final int position, View ConvertView , ViewGroup parent){
        ConvertView = mInflater.inflate(mViewResourceId,null);
        String info = workout_info.get(position);
        if (info != null){
            TextView w = (TextView) ConvertView.findViewById(R.id.chest_workout_name);

                w.setText(info);
        }

        chk = (CheckBox) ConvertView.findViewById(R.id.checkbox1);
        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (checkedListener != null) {
                    checkedListener.getCheckBoxCheckedListener(position);
                }
            }
        });
        return ConvertView;


        }
        public void setCheckedListener (CheckBoxCheckedListener checkedListener){
            this.checkedListener = checkedListener;
        }




    }

