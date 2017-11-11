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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.andrearodriguez.parkingcontrol.PreferencesConstants.KEY_USERNAME;

/**
 * Created by andrearodriguez on 11/9/17.
 */

class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    LinearLayout linearLayout;
    private SharedPreferences pref;
    TextView textUsername;
    TextView textUseremail;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new ParkingFragment())
                .commit();

        pref = getSharedPreferences(PreferencesConstants.PREFERENCE_NAME, MODE_PRIVATE);

        Bundle datos = this.getIntent().getExtras();
        String currenUser = datos.getString(KEY_USERNAME);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null)
            navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        textUsername = (TextView) headerView.findViewById(R.id.text_name);
        textUsername.setText(currenUser);

        textUseremail = (TextView) headerView.findViewById(R.id.text_email);
        textUseremail.setText(currenUser+"@example.com");

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
