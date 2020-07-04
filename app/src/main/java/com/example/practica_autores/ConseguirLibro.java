package com.example.practica_autores;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class ConseguirLibro extends AsyncTask<String,Void,String> {

    private WeakReference<TextView>  mTextoTitulo;
    private WeakReference<TextView> mTextoAutor;

    ConseguirLibro(TextView tituloTexto, TextView autorTexto){
        this.mTextoTitulo = new WeakReference<>(tituloTexto);
        this.mTextoAutor = new WeakReference<>(autorTexto);
    }


    @Override
    protected String doInBackground(String... strings) {
        return UtilidadesRed.obtenerInformacionLibro(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemArray = jsonObject.getJSONArray("items");
            int i=0;
            String titulo = null;
            String autores = null;
            while (i < itemArray.length() && (autores == null && titulo == null)){
                JSONObject libro = itemArray.getJSONObject(i);
                JSONObject volumenInfo = libro.getJSONObject("volumeInfo");
                try{
                    titulo = volumenInfo.getString("title");
                    autores = volumenInfo.getString("authors");
                }catch (Exception e){
                    mTextoTitulo.get().setText("No existen resultados");
                    mTextoAutor.get().setText("");
                    e.printStackTrace();
                }
                i++;
            }

            if(titulo != null && autores != null){
                mTextoTitulo.get().setText(titulo);
                mTextoAutor.get().setText(autores);
            }else{
                mTextoTitulo.get().setText("No existen resultos");
                mTextoAutor.get().setText("");
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

}
