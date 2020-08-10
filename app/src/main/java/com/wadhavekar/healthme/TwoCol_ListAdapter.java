package com.wadhavekar.healthme;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TwoCol_ListAdapter extends ArrayAdapter<Records> {
    private LayoutInflater mInflater;
    private ArrayList<Records> weight_recs;
    private int mViewResourceId;
    InitialSetup ini = new InitialSetup();

    public TwoCol_ListAdapter(Context context, int TextViewResourceId, ArrayList<Records> wt_recs){
        super(context,TextViewResourceId,wt_recs);
        this.weight_recs=wt_recs;
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = TextViewResourceId;
    }


    public View getView(int position, View ConvertView , ViewGroup parent){
        ConvertView = mInflater.inflate(mViewResourceId,null);
        Records records = weight_recs.get(position);
        if (records != null){
            TextView text_wt = (TextView) ConvertView.findViewById(R.id.textwt);
            TextView text_dt = (TextView) ConvertView.findViewById(R.id.textdate);
            TextView text_diff = (TextView) ConvertView.findViewById(R.id.diff);



                if (text_dt!=null){
                    text_dt.setText(records.getDate());
//                    text_dt.setTextColor(Color.parseColor("#FFFFFF"));
                }
                if (text_wt!=null){
                    text_wt.setText(records.getWeight()+" kg");
//                    text_wt.setTextColor(Color.parseColor("#FFFFFF"));

                }
                if (position != 0){
                    DecimalFormat df = new DecimalFormat("0.00");
                    Double d1 = Double.parseDouble(weight_recs.get(position).getWeight());
                    Double d2 = Double.parseDouble(weight_recs.get(position - 1).getWeight());
                    Double total = d1 - d2;


                    if (ini.getX() == 0)
                    {
                        //Toast.makeText(this.getContext(), "" +ini.getX(), Toast.LENGTH_SHORT).show();
                        if (total > 0){
                            text_diff.setText("+"+df.format(total));
                            text_diff.setTextColor(Color.parseColor("#FF0000"));
                        }
                        else if(total < 0){
                            text_diff.setText(""+df.format(total));
                            text_diff.setTextColor(Color.parseColor("#024905"));
                        }
                        else{
                            text_diff.setText(""+df.format(total));
                            text_diff.setTextColor(Color.parseColor("#FFFFFF"));
                        }

                    }
//                    else if (ini.getX() == 2){
//                        if (total < 0){
//                            text_diff.setText("+"+df.format(total));
//                            text_diff.setTextColor(Color.parseColor("#FF0000"));
//                        }
//                        else if(total > 0){
//                            text_diff.setText(""+df.format(total));
//                            text_diff.setTextColor(Color.parseColor("#008000"));
//                        }
//                        else{
//                            text_diff.setText(""+df.format(total));
//                            text_diff.setTextColor(Color.parseColor("#FFFFFF"));
//                        }
//                    }
                }


        }

        return ConvertView;
    }
}
