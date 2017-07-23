package com.example.usuario.reciclernuevo.Model.DAO;


import android.os.AsyncTask;

import com.example.usuario.reciclernuevo.Model.POJO.Source;
import com.example.usuario.reciclernuevo.Model.POJO.SourceContainer;
import com.example.usuario.reciclernuevo.util.DAOException;
import com.example.usuario.reciclernuevo.util.HTTPConnectionManager;
import com.example.usuario.reciclernuevo.util.ResultListener;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TareaSource extends AsyncTask<String, Void, List<Source>> {

    private ResultListener<List<Source>> listenerDelControler;

    //protected para que solo la llame el dao, si no publico...
    protected TareaSource(ResultListener<List<Source>> listenerDelControler) {
        this.listenerDelControler = listenerDelControler;
    }

    @Override
    protected List<Source> doInBackground(String... params) {
        String url = params[0];
        List<Source> listaDeSources = new ArrayList<>();

        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        HTTPConnectionManager httpConnectionManager = new HTTPConnectionManager();

        try {
            inputStream = httpConnectionManager.getRequestStream(url);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            Gson gson = new Gson();
            SourceContainer sourceContainer = gson.fromJson(bufferedReader, SourceContainer.class);
            listaDeSources = sourceContainer.getListaSource();

        } catch (DAOException e){
            e.printStackTrace();
        } finally {
            // CLOSE BUFFERREADER Y INPUT STREAM READER
            try {
                bufferedReader.close();
            } catch (IOException e1){
                e1.printStackTrace();
                try{
                    inputStream.close();
                } catch (IOException e2){
                    e2.printStackTrace();
                }
            }
            // CERRAR LA CONEXION
            httpConnectionManager.closeConnection();
        }
        return listaDeSources;
    }

    @Override
    protected void onPostExecute(List<Source> sources) {
        //esto toma lo que devuelve el doInBackground
        listenerDelControler.finish(sources);
    }

}