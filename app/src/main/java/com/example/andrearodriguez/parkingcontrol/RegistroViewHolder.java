package com.example.andrearodriguez.parkingcontrol;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by andrearodriguez on 11/12/17.
 */

public class RegistroViewHolder extends RecyclerView.ViewHolder {

    private TextView txtMatricula;
    private TextView txtCliente;

    public RegistroViewHolder(View itemView) {
        super(itemView);
        txtMatricula = (TextView) itemView.findViewById(R.id.txt_Matricula);
        txtCliente = (TextView) itemView.findViewById(R.id.txt_Cliente);
    }

    public void bindRegistro(String matricula, String cliente){
        txtMatricula.setText(matricula);
        txtCliente.setText(cliente);
    }
}
