package com.dev.jim.freepublicwifi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.dev.jim.freepublicwifi.Models.AccessPoint;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import me.drakeet.materialdialog.MaterialDialog;

public class NavigationMap extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = "com.dev.jim.freepublicwifi";
    private GoogleMap mMap;
    private CSVReader csv;
    private ArrayList<AccessPoint> accessPoints = new ArrayList<>();
    private ClusterManager<AccessPoint> mClusterManager;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        try {
            AssetManager mng = getApplicationContext().getAssets();
            InputStream is = mng.open("wifi.csv");
            csv = new CSVReader(new InputStreamReader(is));
            String[] nextLine;
            while ((nextLine = csv.readNext()) != null) {
                // nextLine[] is an array of values from the line
                if (!nextLine[6].equals("lat"))
                    accessPoints.add(new AccessPoint(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], nextLine[5], nextLine[8], nextLine[9], nextLine[10], Double.parseDouble(nextLine[6]), Double.parseDouble(nextLine[7])));
                Log.d(TAG, "LOADING WIFI ACCESS POINTS FROM CSV : DONE");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_rate) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getApplicationContext().getPackageName())));
            } catch (Exception e) {
                Snackbar.make(findViewById(R.id.fab), R.string.error_message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        } else if (id == R.id.nav_info) {
            startActivity(new Intent(NavigationMap.this, Information.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(NavigationMap.this, Settings.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(NavigationMap.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            return;
        }

        mMap.setOnInfoWindowClickListener(mClusterManager);


        //addAccessPointToMap(accessPoints);
        setUpClusterer();
    }

    private void setUpClusterer() {
        mMap.clear();
        // Add a marker in GREECE and move the camera
        LatLng focus = new LatLng(38.63368, 24.69053);
        // Position the map.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(focus, 5));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<AccessPoint>(this, mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(this);
        //  mMap.setOnMarkerClickListener(mClusterManager);
        mClusterManager.setRenderer(new OwnRendring(getApplicationContext(), mMap, mClusterManager, getResources()));
        // Add cluster items (markers) to the cluster manager.
        addAccessPointToMap(accessPoints);
    }

    private void addAccessPointToMap(ArrayList<AccessPoint> accessPoints) {

        for (AccessPoint accessPoint : accessPoints) {
            // mMap.addMarker(new MarkerOptions().position(new LatLng(accessPoint.getLatitude(), accessPoint.getLongitude())).title("Public free WiFi Access point"));
            mClusterManager.addItem(accessPoint);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        AccessPoint selected = findAccessPoint(marker.getTitle(), marker.getSnippet(), accessPoints);
        final MaterialDialog mMaterialDialog = new MaterialDialog(this);

        if (selected != null) {
            mMaterialDialog
                    .setTitle(getString(R.string.free_access_point))
                    .setMessage(getString(R.string.province) + selected.getProvince() + getString(R.string.prefecture_v2) + selected.getPrefecture() + getString(R.string.municipality) + selected.getMunicipality() + getString(R.string.location) + selected.getDescription() + getString(R.string.ip) + selected.getIp())
                    .setPositiveButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();
                        }
                    })
                    .show();


        }
        // Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
    }

    private AccessPoint findAccessPoint(String title, String snippet, ArrayList<AccessPoint> accessPoints) {

        String[] snippetParts = snippet.split("-");

        for (AccessPoint accessPoint : accessPoints) {
            if (accessPoint.getDescription().equals(title) && accessPoint.getMunicipality().equals(snippetParts[1])) {
                return accessPoint;
            }
        }

        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                    setUpClusterer();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.


                } else {
                    setUpClusterer();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
