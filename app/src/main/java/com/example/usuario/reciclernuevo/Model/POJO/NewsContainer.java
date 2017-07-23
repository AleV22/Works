package com.example.usuario.reciclernuevo.Model.POJO;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NewsContainer {

    @SerializedName("articles")
    private List<News> listaNews = new ArrayList<>();

    //setters and getters
    public List<News> getListaNews() {
        return listaNews;
    }
}
