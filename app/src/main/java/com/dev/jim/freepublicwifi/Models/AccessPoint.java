package com.dev.jim.freepublicwifi.Models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Jim on 25/5/2016.
 */

public class AccessPoint implements ClusterItem {

    private String fID , gID , province , prefecture , id , ip , description, 	municipality, theGeom;
    private Double latitude, longitude;

    public AccessPoint(String fID, String gID, String province, String prefecture, String id, String ip, String description, String municipality, String theGeom, Double latitude, Double longitude) {
        this.fID = fID;
        this.gID = gID;
        this.province = province;
        this.prefecture = prefecture;
        this.id = id;
        this.ip = ip;
        this.description = description;
        this.municipality = municipality;
        this.theGeom = theGeom;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getfID() {
        return fID;
    }

    public void setfID(String fID) {
        this.fID = fID;
    }

    public String getgID() {
        return gID;
    }

    public void setgID(String gID) {
        this.gID = gID;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getTheGeom() {
        return theGeom;
    }

    public void setTheGeom(String theGeom) {
        this.theGeom = theGeom;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(latitude,longitude);
    }
}
