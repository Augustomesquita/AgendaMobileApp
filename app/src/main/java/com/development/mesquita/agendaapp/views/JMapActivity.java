package com.development.mesquita.agendaapp.views;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.development.mesquita.agendaapp.R;
import com.development.mesquita.agendaapp.fragments.JMapFragment;
import com.google.android.gms.maps.SupportMapFragment;

public class JMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mapFrame, new JMapFragment());
        fragmentTransaction.commit();
    }
}
