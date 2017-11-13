package com.example.andrearodriguez.parkingcontrol;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by andrearodriguez on 11/12/17.
 */

public class RegistroAdapter extends RecyclerView.Adapter<RegistroViewHolder>  {

    private List<String> matriculas;
    private List<String> clientes;
    private Activity activity;

    public RegistroAdapter(List<String> matriculas, List<String> clientes, Activity activity) {
        this.matriculas = matriculas;
        this.clientes = clientes;
        this.activity = activity;
    }

    public RegistroAdapter(FragmentActivity activity, List<String> strings) {

    }

    @Override
    public RegistroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.registro_item,parent,false);

        return new RegistroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RegistroViewHolder holder, int position) {
        holder.bindRegistro(matriculas.get(position), clientes.get(position));
    }

    @Override
    public int getItemCount() {
        return matriculas.size();
    }

    public Activity getActivity() {
        return activity;
    }

    public List<String> getMatriculas() {
        return matriculas;
    }

    public List<String> getClientes() {
        return clientes;
    }

    public void setMatriculas(List<String> matriculas) {
        this.matriculas = matriculas;
    }

    public void setClientes(List<String> clientes) {
        this.clientes = clientes;
    }
}
