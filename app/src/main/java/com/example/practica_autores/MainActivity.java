package com.example.practica_autores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.coloromatic.ColorOMaticDialog;
import es.dmoral.coloromatic.IndicatorMode;
import es.dmoral.coloromatic.OnColorSelectedListener;
import es.dmoral.coloromatic.colormode.ColorMode;

public class MainActivity extends AppCompatActivity {

    private EditText mInputLibro;

    private TextView Titulo;
    private TextView Autor;
    private TextView Anio;
    private TextView Descripcion;
    private ImageView Imagen;

    public static List<Libro> libros;

    private void initializaData(){
        libros = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInputLibro = (EditText)findViewById(R.id.ingresoLibro);
        Titulo = (TextView)findViewById(R.id.titulo);
        Autor = (TextView)findViewById(R.id.Autor);
        Anio = (TextView)findViewById(R.id.Anio);
        Descripcion = (TextView)findViewById(R.id.Descripcion);
        Imagen = (ImageView) findViewById(R.id.imgLibro);

        initializaData();

        final RecyclerView rv = (RecyclerView)findViewById(R.id.RvLibros);
        rv.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(false);
        rv.setLayoutManager(linearLayoutManager);

        Adaptador adaptador = new Adaptador(libros,this);

        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Detalle.class);
                intent.putExtra("titulo", libros.get(rv.getChildAdapterPosition(v)).titulo);
                intent.putExtra("autor", libros.get(rv.getChildAdapterPosition(v)).autor);
                intent.putExtra("anio", libros.get(rv.getChildAdapterPosition(v)).anio);
                intent.putExtra("descripcion", libros.get(rv.getChildAdapterPosition(v)).descripcion);
                startActivity(intent);
            }
        });

        rv.setAdapter(adaptador);

    }

    public void buscarLibro(View view) {

        if (mInputLibro.getText().toString().equals("")) {
            libros.clear();
        } else {
            String Busqueda = mInputLibro.getText().toString();
            new ConseguirLibro(Titulo, Autor, Anio, Descripcion).execute(Busqueda);
            super.onRestart();

        }

    }

    /*private void cerrarteclado() {
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }

     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            cargarColor();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cargarColor() {
        new ColorOMaticDialog.Builder()
                .initialColor(Color.BLACK)
                .colorMode(ColorMode.ARGB)
                .indicatorMode(IndicatorMode.HEX)
                .onColorSelected(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color){
                        SharedPreferences sharedPreferences =
                                MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor =
                                sharedPreferences.edit();
                        editor.putInt(getString(R.string.sp_color_bar),color);
                        editor.commit();
                        cambiarColor(color,MainActivity.this);
                    }
                })
                .showColorIndicator(true)
                .create()
                .show(getSupportFragmentManager(), "ColorOMaticDialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        int color = sharedPreferences.getInt(getString(R.string.sp_color_bar),0);
        if (color != 0){
            cambiarColor(color, this);
        }
    }

    public void cambiarColor(int color, Activity activity){
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(color);
        window.setNavigationBarColor(color);
    }

}
