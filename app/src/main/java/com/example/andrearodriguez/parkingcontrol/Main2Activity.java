package com.example.andrearodriguez.parkingcontrol;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrearodriguez.parkingcontrol.adapter.RegistroAdapter;

import java.io.IOException;

import static com.example.andrearodriguez.parkingcontrol.PreferencesConstants.RECORDAR_BOOLEAN;

/**
 * Created by andrearodriguez on 11/9/17.
 */

class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                                        DialogoAgregarRegistro.OnAgregarRegistroListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    LinearLayout linearLayout;
    private SharedPreferences pref;
    private SharedPreferences configuraciones;
    private RegistroAdapter adapter;

    TextView textUsername;
    TextView textUseremail;

    View headerView;
    RecyclerView recyclerView;

    private final int REQUEST_CODE = 1;
    private final String[] PERMISOS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        int escribir = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int leer = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (escribir != PackageManager.PERMISSION_GRANTED || leer != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISOS, REQUEST_CODE);
        }

        pref = getSharedPreferences(PreferencesConstants.PREFERENCE_NAME, MODE_PRIVATE);

        drawerLayout = findViewById(R.id.drawer_layout);

        Fragment fragment = new ParkingFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commit();

        navigationView = findViewById(R.id.nav_view);
        if (navigationView != null)
            navigationView.setNavigationItemSelectedListener(this);

        headerView = navigationView.getHeaderView(0);

    }

    public void updateView(String title, String subtitle, Boolean tipo) {

        Toolbar toolbar = findViewById(R.id.toolbar);
        linearLayout = findViewById(R.id.header);

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
        configuraciones = PreferenceManager.getDefaultSharedPreferences(this);
        String nombre = configuraciones.getString("nombre", null);

        if(nombre == null){
            usuarioHeader(username);
        }else{
            usuarioHeader(nombre);
        }

        if (tipo) {

            recyclerView = findViewById(R.id.recycler_view);
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

    private void usuarioHeader(String usuario) {
        textUsername = headerView.findViewById(R.id.text_name);
        textUseremail = headerView.findViewById(R.id.text_email);

        textUsername.setText(usuario);
        textUseremail.setText(usuario+"@example.com");

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
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

        if (adapter == null)
            updateView("ParkingControl", "Paruqeos", true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.exportar) {
            crearDialogoExportar();
            return true;
        } else if (id == R.id.cerrar) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void crearDialogoExportar() {
        final String[] items = {getString(R.string.op1_export),getString(R.string.op2_export)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Exportar Registros");

        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView lista = ((AlertDialog) dialog).getListView();
                int selec=lista.getCheckedItemPosition();

                if (selec==0) {
                    eliminarRegistros();
                    updateView(getString(R.string.titulo),getString(R.string.parqueos),true);
                    adapter.notifyDataSetChanged();
                    dialog.cancel();
                    Toast.makeText(getApplicationContext(), "Aún no existe el archivo", Toast.LENGTH_LONG).show();
                } else {
                    dialog.cancel();
                    Toast.makeText(getApplicationContext(), "Archivo guardado en dispositivo", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }


    private void logout() {
        SharedPreferences.Editor edit = pref.edit();
        SharedPreferences.Editor edit2 = configuraciones.edit();

        edit.putBoolean(RECORDAR_BOOLEAN, false);
        edit.clear();
        edit.apply();

        edit2.clear();
        edit2.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void eliminarRegistros() {
        try {
            ServicioRegistro.getInstance(Main2Activity.this).eliminar();
        } catch (IOException e) {
            Toast.makeText(Main2Activity.this, "Error al actualizar el archivo", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            Toast.makeText(Main2Activity.this, "Error al eliminar el elemento", Toast.LENGTH_SHORT).show();
        }
    }

}
