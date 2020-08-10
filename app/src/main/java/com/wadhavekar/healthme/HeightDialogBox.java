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

public class HeightDialogBox extends AppCompatDialogFragment {

    DatabaseManager myDB;
    ArrayList<Records> weight_list = new ArrayList<>();
    NumberPicker height;
    private SetupDialogBoxListener listener;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        myDB = new DatabaseManager(this.getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.MyDialogTheme);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.height_dialog_box, null);
        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogRedirect() {
            @Override
            protected void redirect() {

            }
        });
        height = view.findViewById(R.id.np_height_dialog);
        height.setMinValue(30);
        height.setMaxValue(270);
        Cursor data = myDB.getMyHeight();
        data.moveToLast();
        height.setValue(Integer.parseInt(data.getString(1)));



        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String heightRec = height.getValue()+"";
                listener.applyDialogBox(heightRec);

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


