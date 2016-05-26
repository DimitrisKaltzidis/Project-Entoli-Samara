package com.dev.jim.freepublicwifi;

import android.content.Context;
import android.content.res.Resources;

import com.dev.jim.freepublicwifi.Models.AccessPoint;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by Jim on 25/5/2016.
 */


public class OwnRendring extends DefaultClusterRenderer<AccessPoint> {


    private static final String TAG = "com.dev.jim.freepublicwifi";
    private Resources resources;
    private Context context;

    public OwnRendring(Context context, GoogleMap map, ClusterManager<AccessPoint> clusterManager, Resources resources) {
        super(context, map, clusterManager);
        this.resources = resources;
        this.context = context;
    }

    @Override
    protected void onBeforeClusterItemRendered(AccessPoint item, MarkerOptions markerOptions) {
        if (Preferences.loadPrefsInt("DEFAULT_MARKER", 0, context) == 0) {
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.antenna);
            markerOptions.icon(icon);
        }
        markerOptions.snippet(resources.getString(R.string.tap_for_more) + "\n-" + item.getMunicipality());
        markerOptions.title(item.getDescription());
        super.onBeforeClusterItemRendered(item, markerOptions);
    }


}
