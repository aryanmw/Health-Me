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

public class DialogBox extends AppCompatDialogFragment {

    DatabaseManager myDB;
    ArrayList<Records> weight_list = new ArrayList<>();
    NumberPicker weightKG,weightGrams;
    private DialogBoxListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        myDB = new DatabaseManager(this.getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.MyDialogTheme);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialogbox, null);
        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogRedirect() {
            @Override
            protected void redirect() {

            }
        });
        Cursor data = myDB.getLastEnteredWeight();
        data.moveToLast();
        weightKG = view.findViewById(R.id.np_new_entry);
        weightKG.setMaxValue(250);
        weightKG.setMinValue(1);


        weightGrams = view.findViewById(R.id.np_newEntry_grams);
        weightGrams.setMaxValue(9);
        weightGrams.setMinValue(0);
        if (data.moveToLast()) {
            weightKG.setValue(Integer.parseInt(data.getString(1).substring(0,2)));
            weightGrams.setValue(Integer.parseInt(data.getString(1).substring(data.getString(1).length()-1)));
        } else {
            weightKG.setValue(60);
            weightGrams.setValue(0);
        }


        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String weightrec = weightKG.getValue() + "." + weightGrams.getValue();
                listener.applyText(weightrec);
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DialogBoxListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogBoxListener");
        }
    }
}

