package com.example.usuario.reciclernuevo.Model.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SourceContainer {

    @SerializedName("sources")
    private List<Source> listaSource = new ArrayList<>();

    //setters and getters
    public List<Source> getListaSource() {
        return listaSource;
    }
}