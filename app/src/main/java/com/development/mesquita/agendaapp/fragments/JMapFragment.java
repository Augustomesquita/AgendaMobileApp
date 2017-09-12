package com.development.mesquita.agendaapp.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by augusto on 28/08/17.
 */

public class JMapFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng position = getLocationFromAddress("Rua Vergueiro 3185, Vila Mariana, Sao Paulo");
        if (position != null) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, 17);
            googleMap.moveCamera(cameraUpdate);
        }

    }

    private LatLng getLocationFromAddress(String location) {
        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> resultFromLocationName = geocoder.getFromLocationName(location, 1);
            if (!resultFromLocationName.isEmpty()) {
                LatLng position = new LatLng(resultFromLocationName.get(0).getLatitude(), resultFromLocationName.get(0).getLongitude());
                return position;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
