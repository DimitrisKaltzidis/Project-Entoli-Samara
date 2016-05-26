package com.dev.jim.freepublicwifi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.maps.GoogleMap;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch sMapMarker = (Switch) findViewById(R.id.sDefaultMarker);
        Switch sSatelliteMap = (Switch) findViewById(R.id.sSatellite);

        if (Preferences.loadPrefsInt("DEFAULT_MARKER", 0, getApplicationContext()) == 1) {
            sMapMarker.setChecked(true);
        }

        if (Preferences.loadPrefsInt("MAP_TYPE", GoogleMap.MAP_TYPE_NORMAL, getApplicationContext()) == GoogleMap.MAP_TYPE_SATELLITE) {
            sSatelliteMap.setChecked(true);
        }

        sMapMarker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Preferences.savePrefsInt("DEFAULT_MARKER", 1, getApplicationContext());
                else
                    Preferences.savePrefsInt("DEFAULT_MARKER", 0, getApplicationContext());
            }
        });

        sSatelliteMap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Preferences.savePrefsInt("MAP_TYPE", GoogleMap.MAP_TYPE_SATELLITE, getApplicationContext());
                else
                    Preferences.savePrefsInt("MAP_TYPE", GoogleMap.MAP_TYPE_NORMAL, getApplicationContext());

            }
        });

    }
}
