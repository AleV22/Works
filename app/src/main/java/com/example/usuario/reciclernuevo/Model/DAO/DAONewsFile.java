package com.example.usuario.reciclernuevo.Model.DAO;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.usuario.reciclernuevo.Model.POJO.News;
import com.example.usuario.reciclernuevo.Model.POJO.NewsContainer;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



public class DAONewsFile {

    //Creo un método para pedir la lista de productos del archivo Json
    public List<News> getListaDeNewsFromArchivo(Context context){
        List<News> listaNews = new ArrayList<>();

        //Pedimos el assetManager para acceder a los archivos dentro de la carpeta assets
        AssetManager assetManager = context.getAssets();
        InputStream archivoJsonNews = null;
        BufferedReader bufferedReader = null;

        try {
            //Creamos un stream para leer el archivo Json.
            archivoJsonNews = assetManager.open("news.json");
            bufferedReader = new BufferedReader(new InputStreamReader(archivoJsonNews));

            //Creamos un Objeto de la clase Gson que me permitirá sencillamente parsear el Json.
            Gson gson = new Gson();

            //Utilizando el objeto gson y el método fromJson, realizamos el parsing el archivo
            // que tenemos en el bufferReaderIn y utilizando como “molde” la clase ProductoContainer.
            NewsContainer newsContainer = gson.fromJson(bufferedReader, NewsContainer.class);

            // Devuelvo la lista
            listaNews = newsContainer.getListaNews();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Cierro el Buffer
                if (bufferedReader != null) {
                    bufferedReader.close();
                } else if (archivoJsonNews != null) {
                    archivoJsonNews.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        //Devuelvo null si no se pudo leer el archivo
        return listaNews;
    }
}
