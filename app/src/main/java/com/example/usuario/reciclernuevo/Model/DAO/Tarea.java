package com.example.usuario.reciclernuevo.Model.DAO;

import android.os.AsyncTask;

import com.example.usuario.reciclernuevo.Model.POJO.News;
import com.example.usuario.reciclernuevo.Model.POJO.NewsContainer;
import com.example.usuario.reciclernuevo.util.DAOException;
import com.example.usuario.reciclernuevo.util.HTTPConnectionManager;
import com.example.usuario.reciclernuevo.util.NewsHelper;
import com.example.usuario.reciclernuevo.util.ResultListener;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Tarea extends AsyncTask<String, Void, List<News>> {

    private ResultListener<List<News>> listenerDelControler;

    //protected para que solo la llame el dao, si no publico...
    protected Tarea(ResultListener<List<News>> listenerDelControler) {
        this.listenerDelControler = listenerDelControler;
    }

    @Override
    protected List<News> doInBackground(String[] params) {
        String channel = params[0];
        String sortedBy = params[1];
        String url = null;

        if (sortedBy.equals("top")) {
            url = NewsHelper.getArticulosTop(channel);
        } else {
            url = NewsHelper.getArticulosLatest(channel);
        }

        //se deberia intentar chequear el favoritos aqui, y  asi llegaria definiddo al viewHolder

        List<News> listaDeNoticia = new ArrayList<>();

        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        HTTPConnectionManager httpConnectionManager = new HTTPConnectionManager();

        try {
            inputStream = httpConnectionManager.getRequestStream(url);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            Gson gson = new Gson();
            NewsContainer newsContainer = gson.fromJson(bufferedReader, NewsContainer.class);
            listaDeNoticia = newsContainer.getListaNews();

            //includes the corresponding channel name in each news
            for (News news : listaDeNoticia) {
                news.setChannel(channel);
            }

        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            // CLOSE BUFFERREADER Y INPUT STREAM READER
            try {
                bufferedReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            // CERRAR LA CONEXION
            httpConnectionManager.closeConnection();
        }
        return listaDeNoticia;
    }

    @Override
    protected void onPostExecute(List<News> noticias) {
        listenerDelControler.finish(noticias);
    }

}
