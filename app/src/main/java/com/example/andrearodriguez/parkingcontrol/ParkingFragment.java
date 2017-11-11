package com.example.andrearodriguez.parkingcontrol;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ParkingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_bar_main, container, false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof Main2Activity) {
            Main2Activity activity2 = (Main2Activity) getActivity();
            activity2.updateView(getString(R.string.titulo), (getString(R.string.parking)));
            activity2.navigationView.setCheckedItem(R.id.nav_parqueos);
        }
    }
}
