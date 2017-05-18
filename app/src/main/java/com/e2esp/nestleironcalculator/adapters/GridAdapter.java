package com.e2esp.nestleironcalculator.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import java.util.ArrayList;

import com.e2esp.nestleironcalculator.R;
import com.e2esp.nestleironcalculator.models.AgeSelection;


/**
 * Created by farooq on 05-May-17.
 */

public class GridAdapter extends BaseAdapter {

    //private Context context;
    private ArrayList<AgeSelection> ageSelections;

    //public GridAdapter(Context context, ArrayList<Event> eventsList) {
    public GridAdapter(ArrayList<AgeSelection> ageSelections) {
        // this.context = context;
        this.ageSelections = ageSelections;
    }



    @Override
    public int getCount() {
        return ageSelections.size();
    }

    @Override
    public Object getItem(int position) {
        return ageSelections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);

            //v.setTag(R.id.picture, v.findViewById(R.id.picture));
            //v.setTag(R.id.text, v.findViewById(R.id.text));
        }


        RadioButton rbtnAge= (RadioButton) view.findViewById(R.id.rbtnSelectAge);

        rbtnAge.setText(ageSelections.get(position).getText().toString());


        return view;
    }
}

