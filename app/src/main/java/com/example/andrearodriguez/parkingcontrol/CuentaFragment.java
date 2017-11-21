package com.example.andrearodriguez.parkingcontrol;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;


public class CuentaFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.config_preferences, rootKey);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof Main2Activity) {
            Main2Activity activity2 = (Main2Activity) getActivity();
            activity2.updateView(getString(R.string.titulo), (getString(R.string.cuenta)), false);
            activity2.navigationView.setCheckedItem(R.id.nav_cuenta);
        }
    }

}
