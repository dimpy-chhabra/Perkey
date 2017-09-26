package com.example.dimpy.perkey_dos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.PublicKey;

public class occu_check_frag extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static occu_check_frag newInstance(String param1, String param2) {

        //Log.e(" on New Instance  ",": CALLED! With :>"+param1);
        occu_check_frag FRAG = new occu_check_frag();
        //Log.e(" on New Instance  ",": new frag :>"+FRAG );
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        FRAG.setArguments(args);
        //Log.e(" on New Instance  ",": new frag :>"+FRAG.getArguments() );
        return FRAG;
    }


    public occu_check_frag() {
        // Required empty public constructor
    }

    TextView adressTextView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_occu_check_frag, container, false);

        adressTextView = (TextView)view.findViewById(R.id.adressTextView);

        adressTextView.setText(mParam1);

        return view;
    }
}