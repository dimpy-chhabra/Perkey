package com.example.dimpy.perkey_dos;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;


public class maps_frag extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "Map Fragment";
    public static int[] colors = {
            Color.parseColor("#EF5350"),
            Color.parseColor("#66BB6A"),
            Color.parseColor("#AB47BC"),
            Color.parseColor("#7E57C2"),
            Color.parseColor("#5C6BC0"),
            Color.parseColor("#26A69A"),
            Color.parseColor("#66BB6A"),
            Color.parseColor("#78909C"),
    };
    private GoogleMap mGoogleMap;
    private MapView mMapView;

    public maps_frag() {
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
        View rootView = inflater.inflate(R.layout.fragment_maps_frag, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.tripMapView);
        mMapView.onCreate(savedInstanceState);


        mMapView.getMapAsync(this);
        mMapView.onResume();

        return rootView;
    }

    public void addMarker(LatLng point, String placeName, int index) {

        TextView textView = new TextView(getContext());
        textView.setText(placeName);
        textView.setPadding(30, 10, 30, 10);
        textView.setTextColor(Color.WHITE);
        IconGenerator factory = new IconGenerator(getContext());
        factory.setContentView(textView);
        factory.setColor(colors[index % colors.length]);
        Bitmap icon = factory.makeIcon();
        mGoogleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(icon))
                .position(point)
                .title(placeName));

    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        ArrayList<LatLng> lats = new ArrayList<>();
        lats.add(new LatLng(24.5432, 74.3927));
        lats.add(new LatLng(21.5432, 74.9927));

        addMarker(lats.get(0), "Lala Land ", 3);
        addMarker(lats.get(1), "Yaba Land ", 7);

    }
}