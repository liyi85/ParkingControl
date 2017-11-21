package com.example.andrearodriguez.parkingcontrol;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by andrearodriguez on 11/12/17.
 */

public class DialogoAgregarRegistro extends DialogFragment {

    public static final String TAG = "agregar registro";

    public interface OnAgregarRegistroListener{
        void onAgregarRegistro(Registro registro);
    }

    OnAgregarRegistroListener listener;

    private EditText editMatricula, editCliente;
    private Button btnCancelar, btnAgregarRegistro;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo_agregar_registro, null);
        builder.setView(view);
        init(view);

        btnAgregarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matricula = editMatricula.getText().toString();
                String cliente = editCliente.getText().toString();
                if (!matricula.equals("") &&
                        !cliente.equals("")) {

                    Registro registro = new Registro(editMatricula.getText().toString(),
                            editCliente.getText().toString());
                    listener.onAgregarRegistro(registro);
                    Toast.makeText(getActivity(), "registro "+matricula+" "+ cliente, Toast.LENGTH_SHORT).show();
                dismiss();

                } else {
                    Toast.makeText(getActivity(), "Por favor llenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }

    private void init(View view) {
        editMatricula = (EditText) view.findViewById(R.id.edit_matricula);
        editCliente = (EditText) view.findViewById(R.id.edit_cliente);

        btnAgregarRegistro = (Button) view.findViewById(R.id.btn_agregar_registro);
        btnCancelar = (Button) view.findViewById(R.id.btn_cancelar);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (OnAgregarRegistroListener) getActivity();
        } catch (ClassCastException ex) {
            throw new ClassCastException("El contexto debe implementar la interfaz OnAgregarRegistroListener");
        }
    }


}
