package com.example.andrearodriguez.parkingcontrol;

import java.io.Serializable;

/**
 * Created by andrearodriguez on 11/12/17.
 */

public class Registro implements Serializable{

    private String matricula;
    private String cliente;

    public Registro(String matricula, String cliente) {
        this.matricula = matricula;
        this.cliente = cliente;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
}
