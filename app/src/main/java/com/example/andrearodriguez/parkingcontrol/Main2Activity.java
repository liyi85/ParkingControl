package com.example.andrearodriguez.parkingcontrol;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by andrearodriguez on 11/9/17.
 */

class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    LinearLayout linearLayout;
    private SharedPreferences preferences;
    TextView textUsername;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new ParkingFragment())
                .commit();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null)
            navigationView.setNavigationItemSelectedListener(this);

        preferences = getSharedPreferences(
                PreferencesConstants.PREFERENCE_NAME, MODE_PRIVATE);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    public void updateView(String title, String subtitle) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        linearLayout = (LinearLayout) findViewById(R.id.header);

        if (toolbar != null)
            toolbar.setTitle(title);
        toolbar.setSubtitle(subtitle);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;

        switch (id) {
            case R.id.nav_parqueos:
                fragment = new ParkingFragment();
                break;
            case R.id.nav_cuenta:
                fragment = new CuentaFragment();
                break;
        }
        if (fragment != null)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit();

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }
}
