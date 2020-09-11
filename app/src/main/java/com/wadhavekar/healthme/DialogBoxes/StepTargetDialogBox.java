package com.wadhavekar.healthme.DialogBoxes;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.common.internal.DialogRedirect;
import com.wadhavekar.healthme.DatabaseManager;
import com.wadhavekar.healthme.DialogBoxes.SetupDialogBoxListener;
import com.wadhavekar.healthme.R;
import com.wadhavekar.healthme.Records;

import java.util.ArrayList;

public class StepTargetDialogBox extends AppCompatDialogFragment {

    DatabaseManager myDB;
    ArrayList<Records> weight_list = new ArrayList<>();
    NumberPicker height;
    private SetupDialogBoxListener listener;


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        myDB = new DatabaseManager(this.getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_step_target_dialog_box, null);
        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogRedirect() {
            @Override
            protected void redirect() {

            }
        });
        height = view.findViewById(R.id.np_step_target_dialog_box);

//        NumberPicker.Formatter formatter = new NumberPicker.Formatter() {
//            @Override
//            public String format(int value) {
//                int temp = value*1000;
//                return String.valueOf(temp);
//            }
//        };
//        height.setFormatter(formatter);
        String[] arr = new String[20];
        for (int i = 0 ; i<arr.length ; i++){
            arr[i] = ""+ ((i+1)*1000);
        }
        height.setMinValue(1);
        height.setMaxValue(20);
       height.setDisplayedValues(arr);

       Cursor data = myDB.getTargetSteps();
       if (data.moveToLast()){
           height.setValue(Integer.parseInt(data.getString(1).substring(0,1)));
       }
       else{
           height.setValue(6);
       }



        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String heightRec = height.getValue()+"";
                listener.applyStepDialogBox(heightRec);

            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (SetupDialogBoxListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogBoxListener");
        }
    }
}



