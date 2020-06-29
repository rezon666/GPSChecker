package com.example.gpschecker;


import android.Manifest;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class LocationFragment extends Fragment {

    public String lat, lon;
    private TextView tv_lon, tv_lat, tv_acc, tv_alt;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_location, container, false);
        touchListener(v);

        tv_lat = v.findViewById(R.id.value_latitude);
        tv_lon = v.findViewById(R.id.value_longitude);
        tv_acc = v.findViewById(R.id.value_accuracy);
        tv_alt = v.findViewById(R.id.value_altitude);

        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                    1337);

        return v;
    }

    private void touchListener(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    GPSClass g = new GPSClass(getActivity().getApplicationContext());
                    Location l = g.getLocation();
                    if(l != null) {
                        double x = l.getLatitude();
                        double y = l.getLongitude();

                        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
                        DecimalFormat df = new DecimalFormat("###.000000", otherSymbols);
                        lat = df.format(x);
                        lon = df.format(y);

                        tv_lat.setText(lat);
                        tv_lon.setText(lon);
                        tv_acc.setText(" " + l.getAccuracy());
                        tv_alt.setText(" " + l.getAltitude());

                        if (l.getAccuracy() < 5) {
                            tv_acc.setTextColor(Color.GREEN);
                        } else {
                            tv_acc.setTextColor(Color.RED);
                        }
                    }
                }
                return true;
            }
        });
    }
}