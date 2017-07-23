package com.example.usuario.reciclernuevo.Controller;


import android.content.Context;
import android.widget.Toast;

import com.example.usuario.reciclernuevo.Model.DAO.DAONewsDatabase;
import com.example.usuario.reciclernuevo.Model.DAO.DAONewsInternet;
import com.example.usuario.reciclernuevo.Model.DAO.DAOSourceDatabase;
import com.example.usuario.reciclernuevo.Model.DAO.DAOSourcesInternet;
import com.example.usuario.reciclernuevo.Model.POJO.News;
import com.example.usuario.reciclernuevo.Model.POJO.Source;
import com.example.usuario.reciclernuevo.util.HTTPConnectionManager;
import com.example.usuario.reciclernuevo.util.ResultListener;

import java.util.List;

public class ControllerSource {
    public void obtenerSource(final Context context, final ResultListener<List<Source>> listenerDeLaView2) {
        //(aqui el controler termino lo que debia terminar)
        if (HTTPConnectionManager.isNetworkingOnline(context)) {

            DAOSourcesInternet daoSourcesInternet = new DAOSourcesInternet();
            daoSourcesInternet.obtenerSourceFromInternet(new ResultListener<List<Source>>() {
                @Override
                public void finish(List<Source> resultado2) {

                    if (resultado2 != null) {
                        DAOSourceDatabase daoSourceDatabase = new DAOSourceDatabase(context);
                        daoSourceDatabase.addSourceList(resultado2);
                        listenerDeLaView2.finish(resultado2);
                    } else {
                        Toast.makeText(context, "resultado es null", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            DAOSourceDatabase daoSourceDatabase = new DAOSourceDatabase(context);
            List<Source> listaSource = daoSourceDatabase.getListSourceInDatabase();
            Toast.makeText(context, listaSource.toString(), Toast.LENGTH_LONG).show();
            listenerDeLaView2.finish(listaSource);
        }
    }


}
