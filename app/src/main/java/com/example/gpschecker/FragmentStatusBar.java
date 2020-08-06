package com.example.gpschecker;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class FragmentStatusBar extends Fragment {
    private TextView tv_v_acc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_status_bar, container, false);
        tv_v_acc = v.findViewById(R.id.value_acc);
        final Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getAccuracy();
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(runnable, 2000);
        return v;
    }
    private void getAccuracy() {
        GPSClass g = new GPSClass(getActivity().getApplicationContext());
        Location l = g.getLocation();
        if(l != null) {

            tv_v_acc.setText(" " + l.getAccuracy());

            if (l.getAccuracy() < 5) {
                tv_v_acc.setTextColor(Color.GREEN);
            } else {
                tv_v_acc.setTextColor(Color.RED);
            }
        }
    }

}
