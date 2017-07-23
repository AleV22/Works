package com.example.usuario.reciclernuevo.Model.DAO;


import com.example.usuario.reciclernuevo.Model.POJO.News;
import com.example.usuario.reciclernuevo.Model.POJO.Source;
import com.example.usuario.reciclernuevo.util.NewsHelper;
import com.example.usuario.reciclernuevo.util.ResultListener;

import java.util.List;

public class DAOSourcesInternet {

    //asincrono...
    public void obtenerSourceFromInternet(ResultListener<List<Source>> listenerDelController) {
        TareaSource tareaSource = new TareaSource(listenerDelController);
        tareaSource.execute(NewsHelper.getFuentes());
    }

    //asincrono...
    public void obtenerSourcesByCategory(ResultListener<List<Source>> listenerDelController) {
        TareaSource tareaSource = new TareaSource(listenerDelController);
        tareaSource.execute(NewsHelper.getFuentesPorCategoria ("sports"));
    }

}


