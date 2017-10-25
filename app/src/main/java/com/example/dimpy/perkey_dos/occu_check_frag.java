package com.example.dimpy.perkey_dos;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.security.PublicKey;

public class occu_check_frag extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RequestQueue requestQueue;
    StringRequest stringRequest;
    ProgressDialog progressDialog;
    String res;
    FragmentManager fragmentManager;
    String occu_time = "", adrs = "", basicVal = "", perVal = "";
    TextView adressTextView, entryTimeTextView, chargesTextView, chargesHourlyTextView;
    Button button_Cancelit, button_Parkit;
    private String mParam1;
    private String mParam2;
    private String DataParseUrl = "http://impycapo.esy.es/check_occu.php";

    public occu_check_frag() {
        // Required empty public constructor
    }

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.e(" IN OCCU_CHECK_FRAG", " ON DESTROY VIEW");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_occu_check_frag, container, false);

        ((MainActivity) getActivity()).navigation.setVisibility(View.GONE);

        Log.e(" in occu_check_frag ", " In On Create View");
        Log.e(" onCreateView  : ", " argument 1>" + mParam1 + "< second>" + mParam1.substring(0, mParam1.length() - 3));
        volleyToFetchResponse(view);

        button_Cancelit = (Button) view.findViewById(R.id.button_Cancelit);
        button_Parkit = (Button) view.findViewById(R.id.button_Parkit);

        button_Cancelit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).navigation.setVisibility(View.VISIBLE);
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.content,
                                new scanner_frag(),
                                "scanner_frag").commit();
            }
        });

        button_Parkit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("animate_bit", true); //Storing Boolean
                editor.commit(); // commit changes

                scanner_frag2 fragment = scanner_frag2.newInstance(mParam1, res);

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.content, fragment, "scanner_frag2")
                        .commit();
            }
        });

        return view;
    }

    private void extractDataAndAdapt(String res, View view) {

        //[{"occu_time":"2017-09-28 22:40:47"}][{"adrs":"Basement 1, Pacific Mall, East Delhi, Delhi, India","basic_val":"20","val_per":"5"}]
        occu_time = res.substring(15, 34);
        res = res.substring(37);

        try {
            JSONArray baseArray = new JSONArray(res);
            for (int i = 0; i < baseArray.length(); i++) {
                JSONObject currentRide = baseArray.getJSONObject(i);
                adrs = currentRide.getString("adrs");
                basicVal = currentRide.getString("basic_val");
                perVal = currentRide.getString("val_per");
            }
            Log.e("# # occu_time", " >" + occu_time);
            Log.e("# # adress", " >" + adrs);
            Log.e("# # basic value base", " >" + basicVal);
            Log.e("# # fare per hour", " >" + perVal);

        } catch (JSONException e) {
            Log.e("in ExtractRides : ", "JSON TRY CATCH ERR!");
        }
        adressTextView = (TextView) view.findViewById(R.id.adressTextView);
        adressTextView.setText(adrs);

        entryTimeTextView = (TextView) view.findViewById(R.id.entryTimeTextView);
        entryTimeTextView.setText(occu_time);

        chargesTextView = (TextView) view.findViewById(R.id.chargesTextView);
        chargesTextView.setText("Base Value :" + basicVal);

        chargesHourlyTextView = (TextView) view.findViewById(R.id.chargesHourlyTextView);
        chargesHourlyTextView.setText("Hourly Charges :" + perVal);

    }

    private void volleyToFetchResponse(final View view) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching The Requests");
        progressDialog.show();

        stringRequest = new StringRequest(Request.Method.POST, DataParseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response != null && response.length() > 0) {
                    Log.e("in occu_check_frag ", " " + response);
                    res = response;
                    Log.e("in occu_check_frag ", "@#$ >" + res);

                    Log.e(" res is empty?", " > " + (res != null));
                    if (res != null) {

                        extractDataAndAdapt(res, view);

                    } else {
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.content,
                                        new scanner_frag(),
                                        "scanner_frag").commit();
                    }
                } else {
                    Log.e("in occu_check_frag ", " for response is null :>" + response);
                    Toast.makeText(getContext(), "Please Try again Later after you have parked your car", Toast.LENGTH_LONG).show();
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.content,
                                    new scanner_frag(),
                                    "scanner_frag").commit();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if (error != null && error.toString().length() > 0) {
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                            Log.e(" in occu_check_frag", " error in parsing data>" + error);
                        } else
                            Toast.makeText(getContext(), "Something went terribly wrong! ", Toast.LENGTH_LONG).show();
                        Log.e(" in occu_check_frag ", " error = null ");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("park_id", mParam1);
                params.put("adr", mParam1.substring(0, mParam1.length() - 3));
                /*
                $r_id = $_POST['park_id'];
                $r_id_adr = $_POST['adr'];
                 */
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
}