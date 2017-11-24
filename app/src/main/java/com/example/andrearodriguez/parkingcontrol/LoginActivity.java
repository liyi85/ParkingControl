package com.example.andrearodriguez.parkingcontrol;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.andrearodriguez.parkingcontrol.PreferencesConstants.KEY_PASSWORD;
import static com.example.andrearodriguez.parkingcontrol.PreferencesConstants.KEY_USERNAME;
import static com.example.andrearodriguez.parkingcontrol.PreferencesConstants.RECORDAR_BOOLEAN;

public class LoginActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private SharedPreferences pref;
    SharedPreferences.Editor editor;

    TextInputLayout editUsername;
    TextInputLayout editPassword;
    Button buttonLogin;
    private CheckBox saveLoginCheckBox;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pref = getSharedPreferences(PreferencesConstants.PREFERENCE_NAME, MODE_PRIVATE);
        editor = pref.edit();

        String username = pref.getString(PreferencesConstants.KEY_USERNAME, null);

        if (username != null) {
            Intent intent = new Intent(this,Main2Activity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.ingresar_datos, Toast.LENGTH_SHORT).show();
        }

        editUsername = (TextInputLayout) findViewById(R.id.edit_username);
        editPassword = (TextInputLayout) findViewById(R.id.edit_password);
        buttonLogin = (Button) findViewById(R.id.btn_login);
        saveLoginCheckBox = (CheckBox) findViewById(R.id.checkbox_remember);

        if(pref.getBoolean(RECORDAR_BOOLEAN, false))
            saveLoginCheckBox.setChecked(true);
        else
            saveLoginCheckBox.setChecked(false);

        editUsername.getEditText().setText(pref.getString(KEY_USERNAME, ""));
        editPassword.getEditText().setText(pref.getString(KEY_PASSWORD, ""));

        saveLoginCheckBox.setOnCheckedChangeListener(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();
    }

    private void managePrefs() {
        if (saveLoginCheckBox.isChecked()){
            editor.putString(KEY_USERNAME, editUsername.getEditText().getText().toString().trim());
            editor.putString(KEY_PASSWORD, editPassword.getEditText().getText().toString().trim());
            editor.putBoolean(RECORDAR_BOOLEAN, true);
            editor.apply();


        }else{
            editor.putBoolean(RECORDAR_BOOLEAN, false);
            editor.remove(KEY_PASSWORD);//editor.putString(KEY_PASS,"");
            editor.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }
    }

    private void login() {
        EditText editTextUsername = editUsername.getEditText();
        String username = null;
        if (editTextUsername != null && editTextUsername.getText() != null) {
            username = editTextUsername.getText().toString();
        }

        EditText editTextPassword = editPassword.getEditText();
        String password = null;
        if (editTextPassword != null && editTextPassword.getText() != null) {
            password = editTextPassword.getText().toString();
        }

        boolean login = true;
        if ("".equals(username)) {
            login = false;
            editUsername.setError(getString(R.string.text_error_username));
        }

        if ("".equals(password)) {
            login = false;
            editPassword.setError(getString(R.string.text_error_password));
        }
        if (login) {
            Intent intent = new Intent(this,Main2Activity.class);
            intent.putExtra(KEY_USERNAME, username);
            startActivity(intent);
        }
    }
}
