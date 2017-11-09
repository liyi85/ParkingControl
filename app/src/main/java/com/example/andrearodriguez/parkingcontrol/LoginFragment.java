package com.example.andrearodriguez.parkingcontrol;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class LoginFragment extends Fragment {

    private OnLoginListener listener;

    public interface OnLoginListener {
        void login(String username);
    }

    TextInputLayout editUsername;
    TextInputLayout editPassword;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (OnLoginListener) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException("El contexto debe implementar la interfaz OnLoginListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editUsername = (TextInputLayout) view.findViewById(R.id.edit_username);
        editPassword = (TextInputLayout) view.findViewById(R.id.edit_password);

        view.findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLogin();
            }
        });
    }

    private void onClickLogin() {
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
            listener.login(username);
        }
    }

}
