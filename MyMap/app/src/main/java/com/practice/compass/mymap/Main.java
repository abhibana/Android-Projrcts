package com.practice.compass.mymap;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class Main extends FragmentActivity implements LocationListener{

    private GoogleMap mMap;
    private Location myLocation;// Might be null if Google Play services APK is not available.
    private LocationManager locationManager;
    private LocationRequest locRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER );
        locRequest = LocationRequest.create();
        locRequest.setInterval(500);
        setUpMapIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {

            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            myLocation = mMap.getMyLocation();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
        else   if (mMap != null) {
            setUpMap();
        }
    }

    private void setUpMap() {

        if (myLocation != null) {

            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(myLocation.getLatitude(), myLocation.getLongitude())));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(12.9667, 77.5667)));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            try{
                Log.v(myLocation.toString(),myLocation.getProvider());
            }catch (Exception e) { Log.v("Error",e.toString()); }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
    }
}
