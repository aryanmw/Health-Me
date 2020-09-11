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

public class TargetDialogBox extends AppCompatDialogFragment {

    DatabaseManager myDB;
    ArrayList<Records> weight_list = new ArrayList<>();
    NumberPicker height,grams;
    private SetupDialogBoxListener listener;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        myDB = new DatabaseManager(this.getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.target_dialog_box, null);
        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogRedirect() {
            @Override
            protected void redirect() {

            }
        });
        Cursor data = myDB.getTarget();
        data.moveToLast();
        height = view.findViewById(R.id.et_target_dialog);
        height.setMinValue(1);
        height.setMaxValue(250);
        height.setValue(Integer.parseInt(data.getString(1).substring(0,2)));


        grams = view.findViewById(R.id.np_target_grams);
        grams.setMinValue(0);
        grams.setMaxValue(9);

        grams.setValue(Integer.parseInt(data.getString(1).substring(data.getString(1).length()-1)));


        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String heightRec = height.getValue()+"."+grams.getValue();
                listener.appTarText(heightRec);
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


