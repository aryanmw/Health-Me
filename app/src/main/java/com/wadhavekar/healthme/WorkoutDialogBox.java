package com.wadhavekar.healthme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.internal.DialogRedirect;

public class WorkoutDialogBox extends AppCompatDialogFragment  {
    WorkoutDialogBoxListener listener;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_workout_dialog_box, null);
        builder.setView(view);

       final Spinner spinner = (Spinner) view.findViewById(R.id.spinner1);
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item);
       getResources().getStringArray(R.array.SpinnerItems);
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinner.setAdapter(adapter);

        builder.setNegativeButton("Cancel", new DialogRedirect() {
            @Override
            protected void redirect() {


            }
        });


        builder.setPositiveButton("Add Workout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!spinner.getSelectedItem().toString().equalsIgnoreCase("Select day")){
                    listener.applyText(spinner.getSelectedItem().toString());
                }
                else{
                    Toast.makeText(getActivity(), "Please select a day", Toast.LENGTH_LONG).show();
                }


            }
        });

        return builder.create();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (WorkoutDialogBoxListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement WorkoutDialogBoxListener");
        }
    }

}


