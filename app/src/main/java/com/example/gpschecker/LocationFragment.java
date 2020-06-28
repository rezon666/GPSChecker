package com.example.gpschecker;


import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class LocationFragment extends Fragment {

    private static final int REQUEST_LOCATION = 1337;

    public LocationManager lm = null;
    public String lat, lon;
    private TextView tv_lon, tv_lat, tv_acc;
    public double x1=0,x2=0;
    public float x3=0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_location, container, false);
        touchListener(v);

        tv_lat = v.findViewById(R.id.latitude);
        tv_lon = v.findViewById(R.id.longitude);
        tv_acc = v.findViewById(R.id.accuracy);


        lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION);

        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                x1 = location.getLatitude();
                x2 = location.getLongitude();
                x3 = location.getAccuracy();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
        return v;
    }

    private void touchListener(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    double x = x1;
                    double y = x2;

                    DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
                    DecimalFormat df = new DecimalFormat("###.000000", otherSymbols);
                    lat = df.format(x);
                    lon = df.format(y);

                    tv_lat.setText("Широта: " + lat);
                    tv_lon.setText("Долгота: " + lon);
                    tv_acc.setText("Точность: " + x3);
                    if (x3 < 5) {
                        tv_acc.setTextColor(Color.GREEN);
                    } else {
                        tv_acc.setTextColor(Color.RED);
                    }
                }
                return true;
            }
        });
    }
}