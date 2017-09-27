package com.example.dimpy.perkey_dos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dimpy on 27/9/17.
 */

public class list_item_adapter extends ArrayAdapter<List_Item_Location_History> {

    public list_item_adapter(Context context, ArrayList<List_Item_Location_History> location) {
        super(context, 0, location);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        //getting the view into a variable listItemView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_layout, parent, false);
        }
        //This is to ensure the reusablity of a view

        List_Item_Location_History loc = getItem(position);

        TextView fromTo = (TextView) listItemView.findViewById(R.id.textView_Mall);
        fromTo.setText(loc.getInsti());

        TextView price = (TextView) listItemView.findViewById(R.id.textView_Adress);
        price.setText(loc.getExact() + " " + loc.getCity());

        TextView pricetag = (TextView) listItemView.findViewById(R.id.textView_Duration);
        pricetag.setText(loc.getDuration());

        TextView pricetag1 = (TextView) listItemView.findViewById(R.id.textView_Cost);
        pricetag1.setText("Rs. " + loc.getCost());


        return listItemView;
    }
}