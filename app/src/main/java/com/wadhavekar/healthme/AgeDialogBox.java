package com.wadhavekar.healthme;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.common.internal.DialogRedirect;

import java.util.ArrayList;

public class AgeDialogBox extends AppCompatDialogFragment {

    DatabaseManager myDB;
    ArrayList<Records> weight_list = new ArrayList<>();
    NumberPicker age;
    private SetupDialogBoxListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        myDB = new DatabaseManager(this.getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.MyDialogTheme);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_age_dialog_box, null);
        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogRedirect() {
            @Override
            protected void redirect() {

            }
        });
        age = view.findViewById(R.id.np_step_target_dialog_box);



        age.setMinValue(1);
        age.setMaxValue(100);
        Cursor data = myDB.getAge();
        if (data.moveToLast()){
            age.setValue(Integer.parseInt(data.getString(1)));
        }
        else{
            age.setValue(25);
        }




        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String ageRec = age.getValue()+"";
                listener.applyAgeDialogBox(ageRec);

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
            throw new ClassCastException(context.toString() + "must implement SetupDialogBoxListener");
        }
    }
}

