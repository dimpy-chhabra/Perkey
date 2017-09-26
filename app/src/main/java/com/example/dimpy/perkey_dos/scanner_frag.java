package com.example.dimpy.perkey_dos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class scanner_frag extends Fragment {

    public scanner_frag() {
        // Required empty public constructor
    }
    ImageButton scan_button;
    String code;
    int animateBit = 0 ;
    FragmentManager fragmentManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        if(animateBit==0)
            view = inflater.inflate(R.layout.fragment_scanner_frag, container, false);
        else
            view = inflater.inflate(R.layout.fragment_scanner_deux, container, false);

        scan_button = (ImageButton) view.findViewById(R.id.scan_button);
        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanFromFragment();
             }
        });

        return view;
    }

    private void scanFromFragment() {

        IntentIntegrator.forSupportFragment(this).initiateScan();

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String toast;
        if(result != null) {
            if(result.getContents() == null) {
                toast = "Cancelled from fragment";
            } else {
                toast = "Scanned from fragment: " + result.getContents();
            }
            code = toast;
            Log.e(" in onactivityres","  "+toast);
            // At this point we may or may not have a reference to the activity
            if(getActivity() != null && toast != null) {
                Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
                toast = null;
                gotoOccFrag();
            }
        }
    }

    private void gotoOccFrag() {
        occu_check_frag fragment = occu_check_frag.newInstance(code, "nada");

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                .replace(R.id.content,fragment,"occu_check_frag").commit();

    }
}