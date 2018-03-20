package com.example.dimpy.perkey_dos;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
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

import java.util.HashMap;
import java.util.Map;

public class scanner_frag2 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String occu_time = "", adrs = "", basicVal = "", perVal = "";
    int fare, trans_id;
    int animateBit = -1;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    ProgressDialog progressDialog;
    FragmentManager fragmentManager;
    SharedPreferences pref;
    FloatingActionButton floatingActionButton;
    TextView textView_Adress, textView_Information, textView_EntryTime;
    private String mParam1;
    private String mParam2;
    private String DataParseUrl = "http://impycapo.esy.es/add_trans.php";
    private String DataParseUrl1 = "http://impycapo.esy.es/end_check_trans.php";
    private String DataParseUrl2 = "http://impycapo.esy.es/end_trans.php";

    public scanner_frag2() {
        // Required empty public constructor
    }

    public static scanner_frag2 newInstance(String param1, String param2) {

        //Log.e(" on New Instance  ",": CALLED! With :>"+param1);
        scanner_frag2 FRAG = new scanner_frag2();
        //Log.e(" on New Instance  ",": new frag :>"+FRAG );
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        FRAG.setArguments(args);
        //Log.e(" on New Instance  ",": new frag :>"+FRAG.getArguments() );
        return FRAG;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


        Log.e(" in SCANNER FRAG 2 ", " ON DESTROY VIEW");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        fragmentManager = getActivity().getSupportFragmentManager();

        Log.e(" in SCANNER FRAG 2 ", " ON CREATE VIEW");
        pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        Boolean animate = pref.getBoolean("animate_bit", false);


        if (animate)
            animateBit = 1;
        else
            animateBit = 0;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_scanner_deux, container, false);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        Log.e(" in SCANNER FRAG 2 ", " ON CREATE VIEW");
        volleyToFetchResponse(view);

        if (animateBit == 1) {
            Chronometer chronometer = (Chronometer) view.findViewById(R.id.textView_Chrono);
            if (!chronometer.isActivated())
                chronometer.start();
        }
        //Toast.makeText(getActivity(), " >>"+mParam2, Toast.LENGTH_SHORT).show();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitModule(view);
            }
        });
        return view;
    }

    private void exitModule(View view) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Exit process Initiated");
        progressDialog.show();

        volleyToExit(view);

    }

    private void volleyToExit(final View view) {
        stringRequest = new StringRequest(Request.Method.POST, DataParseUrl1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response != null && response.length() > 0 && response != "[{\"FARE\":null}]" &&
                        !response.equals("NOT") && !response.equalsIgnoreCase("[{\"FARE\":null}]")) {
                    Log.e("in scanner_frag2 ", " SSUUCCEESSS : " + response);
                    //[{"FARE":"25"}]
                    fare = Integer.parseInt(response.substring(10, 12));
                    Toast.makeText(getContext(), " Your fare as calculated is Rs. " + fare, Toast.LENGTH_LONG).show();

                    wannaExit(view);

                } else {
                    Log.e("in scanner_frag2  ", " for response is null :>" + response);
                    Toast.makeText(getContext(), "Please Try again Later after you have VACCATED THE PARKING SPOT", Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if (error != null && error.toString().length() > 0) {
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                            Log.e(" in  scanner_frag2 ", " error in parsing data");
                        } else
                            Toast.makeText(getContext(), "Something went terribly wrong! ", Toast.LENGTH_LONG).show();
                        Log.e("in scanner_frag2  ", " error = null ");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
                String usr = pref.getString("phone", "0000000000");
                String trans = String.valueOf(trans_id);
                params.put("park_id", mParam1);
                params.put("usr_id", usr);
                params.put("trans_id", trans);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void wannaExit(View view) {

        new AlertDialog.Builder(view.getContext())
                .setTitle("Let's Exit")
                .setMessage("Your generated bill is of " + fare + ". Click on exit to continue.")
                .setPositiveButton("EXIT!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Exit process Initiated");
                        progressDialog.show();

                        Toast.makeText(getActivity(), "ok bye! OnLine Payment Platform coming soon!!", Toast.LENGTH_SHORT).show();
                        volleyToExitAndBill();
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity().getApplicationContext(), " cancelled!! though it doesnt really make much sense", Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(R.drawable.common_full_open_on_phone)
                .show();
    }

    private void volleyToExitAndBill() {

        stringRequest = new StringRequest(Request.Method.POST, DataParseUrl2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response != null && !response.equalsIgnoreCase(" Data Not updated!")) {
                    Log.e("in scanner_frag2 ", " volleyToExitAndBill " + response);
                    Toast.makeText(getActivity(), "Hope you had a happy stay!", Toast.LENGTH_SHORT).show();

                    SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("animate_bit", false); //Storing Boolean
                    editor.putInt("tans_id", -1); //Storing Integer
                    editor.commit(); // commit changes

                    ((MainActivity) getActivity()).navigation.setVisibility(View.VISIBLE);

                    fragmentManager
                            .beginTransaction()
                            .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                            .replace(R.id.content,
                                    new scanner_frag(),
                                    "scanner_frag").commit();

                } else {
                    Log.e("in scanner_frag2 ", " volleyToExitAndBill " + response);
                    Toast.makeText(getActivity(), "ERR! DATA NOT UPDATED", Toast.LENGTH_SHORT).show();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if (error != null && error.toString().length() > 0) {
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                            Log.e(" in  scanner_frag2 ", " error in parsing data");
                        } else
                            Toast.makeText(getContext(), "Something went terribly wrong! ", Toast.LENGTH_LONG).show();
                        Log.e("in scanner_frag2  ", " error = null ");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
                String usr = pref.getString("phone", "0000000000");
                String fa = String.valueOf(fare);
                params.put("park_id", mParam1);
                params.put("usr_id", usr);
                params.put("fare", fa);
                params.put("trans_id", String.valueOf(trans_id));

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);


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
                    Log.e("in scanner_frag2 ", " " + response);
                    trans_id = Integer.parseInt(response.substring(0, 2));
                    Toast.makeText(getActivity(), " with trans_id " + response, Toast.LENGTH_SHORT).show();
                    populateUI(view);
                } else {
                    Log.e("in scanner_frag2  ", " for response is null :>" + response);
                    Toast.makeText(getContext(), "Please Try again Later after you have parked your car", Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if (error != null && error.toString().length() > 0) {
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                            Log.e(" in  scanner_frag2 ", " error in parsing data");
                        } else
                            Toast.makeText(getContext(), "Something went terribly wrong! ", Toast.LENGTH_LONG).show();
                        Log.e("in scanner_frag2  ", " error = null ");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
                String usr = pref.getString("phone", "0000000000");

                params.put("park_id", mParam1);
                params.put("adr", mParam1.substring(0, mParam1.length() - 3));
                params.put("usr_id", usr);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void populateUI(View view) {
        occu_time = mParam2.substring(15, 34);
        mParam2 = mParam2.substring(37);

        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("tans_id", trans_id); //Storing Integer
        editor.commit(); // commit changes


        try {
            JSONArray baseArray = new JSONArray(mParam2);
            for (int i = 0; i < baseArray.length(); i++) {
                JSONObject currentRide = baseArray.getJSONObject(i);
                adrs = currentRide.getString("adrs");
                basicVal = currentRide.getString("basic_val");
                perVal = currentRide.getString("val_per");
            }
            //Log.e("# # occu_time"," >"+occu_time); Log.e("# # adress"," >"+adrs); Log.e("# # basic value base"," >"+basicVal); Log.e("# # fare per hour"," >"+perVal);

        } catch (JSONException e) {
            Log.e("in ExtractRides : ", "JSON TRY CATCH ERR!");
        }
        textView_Adress = (TextView) view.findViewById(R.id.textView_Adress);
        textView_Adress.setText(mParam1.substring(7, 9) + "/" + mParam1.substring(9) + " " + adrs);

        textView_Information = (TextView) view.findViewById(R.id.textView_Information);
        textView_Information.setText("Base Fare =Rs. " + basicVal + " and hourly : Rs." + perVal);

        textView_EntryTime = (TextView) view.findViewById(R.id.textView_EntryTime);
        textView_EntryTime.setText(occu_time);

    }


}