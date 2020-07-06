package com.example.practica_autores;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Detalle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.detalle);

        String titulo = "";
        String autor = "";
        String anio = "";
        String descripcion = "";

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            titulo = extras.getString("titulo");
            autor = extras.getString("autor");
            anio = extras.getString("anio");
            descripcion = extras.getString("descripcion");
        }

        TextView txttitulo = (TextView)findViewById(R.id.titulo);
        TextView txtautor = (TextView)findViewById(R.id.autor);
        TextView txtanio = (TextView)findViewById(R.id.anio);
        TextView txtdescripcion = (TextView)findViewById(R.id.descripcion);

        txttitulo.setText("Titulo: " +titulo);
        txtautor.setText("Autor: " +autor);
        txtanio.setText("Publicacion: " +anio);
        txtdescripcion.setText(descripcion);
    }

}
