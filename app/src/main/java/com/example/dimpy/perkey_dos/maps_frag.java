package com.example.dimpy.perkey_dos;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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


        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(28.5200, 77.0964), 10);
        mGoogleMap.animateCamera(cameraUpdate);

        ArrayList<LatLng> lats = new ArrayList<>();
        lats.add(new LatLng(28.5434, 77.1568));
        lats.add(new LatLng(28.5054, 77.0964));
        lats.add(new LatLng(28.5286, 77.2193));
        lats.add(new LatLng(28.5794, 77.0561));

        mGoogleMap.addMarker(new MarkerOptions()
                .position(lats.get(0))
                .title("DLF Emporio")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .snippet("PSpots = 50"));

        mGoogleMap.addMarker(new MarkerOptions()
                .position(lats.get(1))
                .title("Ambience Island")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                .snippet("PSpots = 30"));

        mGoogleMap.addMarker(new MarkerOptions()
                .position(lats.get(2))
                .title("Select CityWalk")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                .snippet("PSpots = 27"));

        mGoogleMap.addMarker(new MarkerOptions()
                .position(lats.get(3))
                .title("Pinnacle Mall")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                .snippet("PSpots = 40"));

    }
}