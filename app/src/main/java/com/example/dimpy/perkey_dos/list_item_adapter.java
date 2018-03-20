package com.example.dimpy.perkey_dos;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

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

        //TextView pricetag1 = (TextView) listItemView.findViewById(R.id.textView_Cost);
        //pricetag1.setText("Rs. " + loc.getCost());

        String cost_ = loc.getCost();

        final TextView shape_money = (TextView) listItemView.findViewById(R.id.shape_money);
        shape_money.setText("Rs." + cost_);

        GradientDrawable priceCircle = (GradientDrawable) shape_money.getBackground();
        int priceColor = 0;

        if (Integer.parseInt(cost_) > 50 && Integer.parseInt(cost_) < 100)
            priceColor = ContextCompat.getColor(getContext(), R.color.colorDarkest);
            //shape_money.setBackgroundResource(R.color.colorDarkest);
        else if (Integer.parseInt(cost_) < 50)
            priceColor = ContextCompat.getColor(getContext(), R.color.colorDark);
            /// /shape_money.setBackgroundResource(R.color.colorDark);
        else
            priceColor = ContextCompat.getColor(getContext(), R.color.colorLight);
        //shape_money.setBackgroundResource(R.color.colorLight);

        priceCircle.setColor(priceColor);

        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                shape_money.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.left_enter));
            }
        }, 200);
        shape_money.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.left_out));

        return listItemView;
    }
}