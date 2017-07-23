package com.example.usuario.reciclernuevo.Controller;

import android.content.Context;
import android.widget.Toast;

import com.example.usuario.reciclernuevo.Model.DAO.DAONewsDatabase;
import com.example.usuario.reciclernuevo.Model.DAO.DAONewsInternet;
import com.example.usuario.reciclernuevo.Model.POJO.News;
import com.example.usuario.reciclernuevo.util.HTTPConnectionManager;
import com.example.usuario.reciclernuevo.util.ResultListener;

import java.util.List;

public class ControllerNews {

    public void obtenerNews(final Context context, final ResultListener<List<News>> listenerDeLaView,String source, String sortedBy) {
        //(aqui el controler termino lo que debia terminar)
        if (HTTPConnectionManager.isNetworkingOnline(context)) {

            DAONewsInternet daoNoticiaInternet = new DAONewsInternet();
            daoNoticiaInternet.obtenerNewsFromInternet(new ResultListener<List<News>>() {
                @Override
                public void finish(List<News> resultado) {
                    if (resultado != null) {
                        DAONewsDatabase daoNewsDatabase = new DAONewsDatabase(context);
                        daoNewsDatabase.addNewsList(resultado);
                        listenerDeLaView.finish(resultado);
                    } else {
//                        Toast.makeText(context, "resultado es null", Toast.LENGTH_SHORT).show();
                    }
                }
            },source,sortedBy);

        } else {
            DAONewsDatabase daoNewsDatabase = new DAONewsDatabase(context);
            List<News> listaNews = daoNewsDatabase.getListNewsInDatabase();
//            Toast.makeText(context, listaNews.toString(), Toast.LENGTH_LONG).show();
            listenerDeLaView.finish(listaNews);
        }
    }

    //metodo que reciba la noticia y le cambie el favorito
    public void favoriteador(Context context, News news) {
        DAONewsDatabase daoNewsDatabase = new DAONewsDatabase(context);
        daoNewsDatabase.toggleIsFavoritedInDB(news);
    }

    public Boolean chequeadorFavoritos(Context context, News news) {
        DAONewsDatabase daoNewsDatabase = new DAONewsDatabase(context);
        return daoNewsDatabase.isFavoriteInDB(news.getUrl());
    }

    public List<News> getListDeFavoritos(Context context){
        DAONewsDatabase daoNewsDatabase = new DAONewsDatabase(context);
        return daoNewsDatabase.getListNewsFavoritedInDatabase();
    }
}

