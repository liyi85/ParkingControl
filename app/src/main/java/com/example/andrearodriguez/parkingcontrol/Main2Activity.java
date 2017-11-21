package com.example.andrearodriguez.parkingcontrol;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrearodriguez.parkingcontrol.adapter.RegistroAdapter;

import java.io.IOException;

/**
 * Created by andrearodriguez on 11/9/17.
 */

class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                                        DialogoAgregarRegistro.OnAgregarRegistroListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    LinearLayout linearLayout;
    private SharedPreferences pref;
    TextView textUsername;
    TextView textUseremail;
    private RegistroAdapter adapter;
    View header;

    private final int REQUEST_CODE = 1;
    private final String[] PERMISOS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        int escribir = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int leer = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (escribir != PackageManager.PERMISSION_GRANTED || leer != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, PERMISOS, REQUEST_CODE);
        }

        pref = getSharedPreferences(PreferencesConstants.PREFERENCE_NAME, MODE_PRIVATE);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Fragment fragment = new ParkingFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,fragment)
                .commit();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null)
            navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);

    }

    public void updateView(String title, String subtitle, Boolean tipo) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        linearLayout = (LinearLayout) findViewById(R.id.header);

        if (toolbar != null) {
            toolbar.setTitle(title);
            toolbar.setSubtitle(subtitle);
        }
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        String username = pref.getString(PreferencesConstants.KEY_USERNAME, null);

        updateUsername(username);

        if (tipo) {

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            try {
                adapter = new RegistroAdapter(this, ServicioRegistro.getInstance(this).cargarDatos());

            } catch (IOException e) {
                Toast.makeText(this, "Error al cargar el archivo", Toast.LENGTH_SHORT).show();
            } catch (ClassNotFoundException e) {
                Toast.makeText(this, "Error al cargar la lista", Toast.LENGTH_SHORT).show();
            }

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setAdapter(adapter);
        }
    }

    private void updateUsername(String usuario) {
        textUsername = (TextView) header.findViewById(R.id.text_name);
        textUsername.setText(usuario);

        textUseremail = (TextView) header.findViewById(R.id.text_email);
        textUseremail.setText(usuario+"@example.com");

        SharedPreferences configuraciones = PreferenceManager.getDefaultSharedPreferences(this);
        String nombre = configuraciones.getString("nombre_de_usuario", null);
        String email = configuraciones.getString("nombre_de_usuario", null);

        if (textUsername != null && textUseremail !=null) {
            textUsername.setText(nombre);
            textUseremail.setText(email + "@ejemplo.com");
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }

    @SuppressWarnings("StatementWithEmptyBody")
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

    @Override
    public void onAgregarRegistro(Registro registro) {
        try {
            ServicioRegistro.getInstance(this).guardarRegistro(registro);
            registro.getCliente();
        } catch (IOException e) {
            Toast.makeText(this, "Error al actualizar el archivo", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            Toast.makeText(this, "Error al guardar elemento en la lista", Toast.LENGTH_SHORT).show();
        }

        if(adapter==null)
            updateView("ParkingControl", "Paruqeos", true);
        adapter.notifyDataSetChanged();
    }

}
