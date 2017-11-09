package com.example.andrearodriguez.parkingcontrol;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
                            implements LoginFragment.OnLoginListener{

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences(PreferencesConstants.PREFERENCE_LOGIN, MODE_PRIVATE);
        String username = pref.getString(PreferencesConstants.PREF_KEY_USERNAME, null);

        Fragment fragment = new Fragment();
        if (username != null) {
            Intent  intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
        } else {
            fragment = new LoginFragment();
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,fragment)
                .commit();
    }

    @Override
    public void login(String username) {
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(PreferencesConstants.PREF_KEY_USERNAME, username);
        edit.apply();

        Intent  intent = new Intent(this, Main2Activity.class);
        startActivity(intent);

        Toast.makeText(MainActivity.this, "nombre de usuario: " + username, Toast.LENGTH_SHORT).show();
    }
}
