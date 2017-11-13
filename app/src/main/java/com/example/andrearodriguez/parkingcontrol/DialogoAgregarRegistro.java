package com.example.andrearodriguez.parkingcontrol;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by andrearodriguez on 11/12/17.
 */

public class DialogoAgregarRegistro extends DialogFragment {

    private EditText editMatricula, editCliente;
    private Button btnCancelar, btnAgregarRegistro;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialogo_agregar_registro, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), Gravity.CENTER);

        editMatricula = (EditText) view.findViewById(R.id.edit_matricula);
        editCliente = (EditText) view.findViewById(R.id.edit_cliente);
        btnAgregarRegistro = (Button) view.findViewById(R.id.btn_agregar_registro);
        btnCancelar = (Button) view.findViewById(R.id.btn_cancelar);

        builder.setView(view);



        btnAgregarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editMatricula.getText().toString().equals("") &&
                        !editMatricula.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Registrado "+ editMatricula.getText().toString()+" "+ editCliente.getText().toString(), Toast.LENGTH_SHORT).show();

                    dismiss();
                }else {
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

}
