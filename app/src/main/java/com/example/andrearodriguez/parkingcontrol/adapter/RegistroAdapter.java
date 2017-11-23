package com.example.andrearodriguez.parkingcontrol.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrearodriguez.parkingcontrol.R;
import com.example.andrearodriguez.parkingcontrol.Registro;

import java.util.List;

/**
 * Created by andrearodriguez on 11/15/17.
 */

public class RegistroAdapter extends  RecyclerView.Adapter<RegistroAdapter.RegistroViewHolder>{


    public class RegistroViewHolder extends RecyclerView.ViewHolder{

        TextView txtMatricula, txtcliente;

        RegistroViewHolder(View itemView) {
            super(itemView);

            txtMatricula = (TextView) itemView.findViewById(R.id.txt_Matricula);
            txtcliente = (TextView) itemView.findViewById(R.id.txt_Cliente);

        }

    }

    private List<Registro> registros;
    private Activity activity;

    public RegistroAdapter(Activity activity, List<Registro> registros) {
        this.registros = registros;
        this.activity = activity;
    }


    private List<Registro> getRegistros() {
        return registros;
    }

    @Override
    public RegistroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.registro_item, parent, false);

        return new RegistroViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(RegistroViewHolder holder, int position) {
        Registro registro = registros.get(position);
        holder.txtMatricula.setText(registro.getMatricula());
        holder.txtcliente.setText(registro.getCliente());
    }

    @Override
    public int getItemCount() {
        return registros.size();
    }

    public void limpiar() {
        registros.clear();
        notifyItemRangeRemoved(0, registros.size());
    }

}
