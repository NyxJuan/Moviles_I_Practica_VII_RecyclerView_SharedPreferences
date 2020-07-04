package com.example.practica_autores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mInputLibro;
    private TextView mTextoTitulo;
    private TextView mTextoAutor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInputLibro = (EditText)findViewById(R.id.ingresoLibro);
        mTextoTitulo = (TextView)findViewById(R.id.titulo);
        mTextoAutor = (TextView)findViewById(R.id.autorLibro);
    }

    public void buscarLibro(View view){
        String cadenaBusqueda = mInputLibro.getText().toString();
        new ConseguirLibro(mTextoTitulo, mTextoAutor).execute(cadenaBusqueda);
    }


}
