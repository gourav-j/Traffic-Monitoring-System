package com.example.bhavesh.roadtraffic;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Bhavesh on 20-02-2018.
 */
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Show_map extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        // For searching the location
        String source= getIntent().getStringExtra("source");
        String dest= getIntent().getStringExtra("destination");

        List<Address> sourceList = null;
        List<Address> destList = null;
            Geocoder geocoder = new Geocoder(this);
            try {
                sourceList = geocoder.getFromLocationName(source, 1);
                destList = geocoder.getFromLocationName(dest, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address_Source = sourceList.get(0);
            LatLng latLngSource = new LatLng(address_Source.getLatitude(), address_Source.getLongitude());
            Address address_Dest = destList.get(0);
            LatLng latLngDest = new LatLng(address_Dest.getLatitude(), address_Dest.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLngSource).title("Marker").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngSource));
            mMap.addMarker(new MarkerOptions().position(latLngDest).title("Marker").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngDest));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
            // Add a thin red line from source to destination
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(latLngSource, latLngDest)
                    .width(5)
                    .color(Color.BLUE));
            CalculationByDistance(latLngSource, latLngDest);


    }
    // Calculate the distance between two points

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        TextView distance = (TextView)findViewById(R.id.textDistance);
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);
        distance.setVisibility(View.VISIBLE);
        distance.setText("Distance : "+kmInDec +"KM");
        return Radius * c;
    }

}
