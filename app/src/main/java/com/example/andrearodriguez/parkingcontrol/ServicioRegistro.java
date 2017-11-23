package com.example.andrearodriguez.parkingcontrol;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by andrearodriguez on 11/16/17.
 */

public class ServicioRegistro {

    private ArrayList<Registro> registros;
    private final String nombreArchivo = "registros.txt";
    private static ServicioRegistro instance;
    private Context context;

    private ServicioRegistro(Context context) throws ClassNotFoundException, IOException {
        try {
            this.context = context;
            this.registros = new ArrayList<>();
            cargarDatos();
        }catch (IOException e){
            this.context = context;
        }
    }

    public static ServicioRegistro getInstance(Context context) throws IOException, ClassNotFoundException {
        if (instance == null)
            instance = new ServicioRegistro(context);
        return instance;
    }

    public void guardarRegistro(Registro registro) throws IOException {
        registros.add(registro);
        File archivo = new File(context.getExternalFilesDir(null), nombreArchivo);
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(archivo));
        output.writeObject(registros);
        output.close();

    }

    public ArrayList<Registro> cargarDatos() throws IOException, ClassNotFoundException {
        File archivo = new File(context.getExternalFilesDir(null), nombreArchivo);
        ObjectInputStream input = new ObjectInputStream(new FileInputStream(archivo));
        registros = (ArrayList<Registro>) input.readObject();

        input.close();
        return registros;

    }

    public ArrayList<Registro> eliminar() throws IOException{
        registros.removeAll(registros);
        File archivo = new File(context.getExternalFilesDir(null), nombreArchivo);
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(archivo));
        output.writeObject(registros);
        output.close();
        context.deleteFile(nombreArchivo);
        registros.clear();
        return registros;
    }

}
