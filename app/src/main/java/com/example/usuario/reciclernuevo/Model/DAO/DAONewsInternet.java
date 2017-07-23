package com.example.usuario.reciclernuevo.Model.DAO;

import android.content.Context;

import com.example.usuario.reciclernuevo.Controller.ControllerNews;
import com.example.usuario.reciclernuevo.Model.POJO.News;
import com.example.usuario.reciclernuevo.util.NewsHelper;
import com.example.usuario.reciclernuevo.util.ResultListener;

import java.util.ArrayList;
import java.util.List;

public class DAONewsInternet{


    public void obtenerNewsFromInternet (ResultListener<List<News>> listenerDelController,String source, String sortedBy){
        obtenerNews(listenerDelController, source, sortedBy);
    }

    //asincrono...
    public void obtenerNews(ResultListener<List<News>> listenerDelController,String source, String sortedBy) {
        //Creamos la tarea para que haga el pedido a internet
        Tarea tarea = new Tarea(listenerDelController);
        tarea.execute(source, sortedBy);
    }
}