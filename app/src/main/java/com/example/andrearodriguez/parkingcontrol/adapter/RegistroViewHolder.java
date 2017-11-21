package com.example.andrearodriguez.parkingcontrol.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.andrearodriguez.parkingcontrol.R;

/**
 * Created by andrearodriguez on 11/15/17.
 */

public class RegistroViewHolder extends RecyclerView.ViewHolder {

    TextView txtMatricula, txtCliente;

    public RegistroViewHolder(View itemView) {
        super(itemView);

        txtMatricula = (TextView) itemView.findViewById(R.id.txt_Matricula);
        txtCliente = (TextView) itemView.findViewById(R.id.txt_Cliente);
    }
}
