package com.example.dimpy.perkey_dos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class list_frag extends Fragment {

    public list_frag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_frag, container, false);
        ListView lv = (ListView) view.findViewById(R.id.list);

        final ArrayList<List_Item_Location_History> lv_arr = new ArrayList<>();

        //List_Item_Location_History(String insti, String city, String duration, String cost, String exact){
        lv_arr.add(new List_Item_Location_History("Ambience Mall", "Gurgaon, NCR, India", "2 hours", "50", "B1/25"));
        lv_arr.add(new List_Item_Location_History("DLF Emporio", "Nelson Mandela Road, Vasant Kunj, New Delhi, Delhi", "2.4 hours", "75", "B0/18"));
        lv_arr.add(new List_Item_Location_History("Select CityWalk Mall", "A-3 District Centre, Saket, New Delhi", "2 hours", "10", "B4/32"));
        lv_arr.add(new List_Item_Location_History("Star City Mall", " Mayur Place, Mayur Vihar Phase I, New Delhi, Delhi", "5 hours", "80", "B2/56"));
        lv_arr.add(new List_Item_Location_History("Pinnacle Mall", "Sector 10 Dwarka, Dwarka, Delhi", "3 hours", "30", "B0/45"));


        list_item_adapter item_adapter = new list_item_adapter(getActivity(), lv_arr);
        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(item_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                List_Item_Location_History item = lv_arr.get(position);

                Toast.makeText(getActivity(), "You parked at " + item.getInsti() + " for " + item.getDuration() + "hours for " + item.getCost(), Toast.LENGTH_SHORT).show();


                /*
                Ride ride = ridesArrayList.get(position);
                Log.e("onItem Click ", " " + ride.getR_id());
                My_offered_rides_Frag frag = new My_offered_rides_Frag();
                Bundle args = new Bundle();
                args.putString("rideId", "" + ride.getR_id());
                frag.setArguments(args);

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.frameContainerTrack, frag,
                                BaseActivity.offered_rides_Frag).commit();


*/
            }
        });

        return view;
    }
}