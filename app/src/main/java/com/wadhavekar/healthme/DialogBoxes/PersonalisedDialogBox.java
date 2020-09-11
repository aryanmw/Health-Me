package com.wadhavekar.healthme.DialogBoxes;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.common.internal.DialogRedirect;
import com.wadhavekar.healthme.DatabaseManager;
import com.wadhavekar.healthme.R;
import com.wadhavekar.healthme.Records;

import java.util.ArrayList;

public class PersonalisedDialogBox extends AppCompatDialogFragment {

    DatabaseManager myDB = new DatabaseManager(getContext());
    ArrayList<Records> weight_list = new ArrayList<>();
    EditText weight;
    private PersonalisedWorkoutDialogBoxListener listener;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.personalised_workout_dialog_box, null);
        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogRedirect() {
            @Override
            protected void redirect() {

            }
        });
        //weight = view.findViewById(R.id.edittext1);


        builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.doWork();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (PersonalisedWorkoutDialogBoxListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogBoxListener");
        }
    }
}


