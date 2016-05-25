package com.dev.jim.freepublicwifi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import com.dev.jim.freepublicwifi.Models.AccessPoint;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by Jim on 25/5/2016.
 */


public class OwnRendring extends DefaultClusterRenderer<AccessPoint> {


    private static final String TAG = "com.dev.jim.freepublicwifi";
    private Resources resources;

    public OwnRendring(Context context, GoogleMap map, ClusterManager<AccessPoint> clusterManager, Resources resources) {
        super(context, map, clusterManager);
        this.resources = resources;
    }

    @Override
    protected void onBeforeClusterItemRendered(AccessPoint item, MarkerOptions markerOptions) {
        //  markerOptions.icon(item.getIcon());
        markerOptions.snippet(resources.getString(R.string.prefecture) +"-"+ item.getMunicipality());
        markerOptions.title(item.getDescription());
        super.onBeforeClusterItemRendered(item, markerOptions);
    }


}
