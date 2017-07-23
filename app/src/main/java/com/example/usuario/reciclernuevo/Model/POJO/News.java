package com.example.usuario.reciclernuevo.Model.POJO;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.SerializedName;

@IgnoreExtraProperties
public class News {
    @SerializedName("title")
    private String titulo;
    @SerializedName("author")
    private String author;
    @SerializedName("description")
    private String descripcion;
    @SerializedName("urlToImage")
    private String imagen;
    private String channel;
    private String url;
    private Boolean favorited;
    private String publishedAt;

    public News(String titulo, String author, String descripcion, String imagen, String channel, String url, Boolean favorited, String publishedAt) {
        this.titulo = titulo;
        this.author = author;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.channel = channel;
        this.url = url;
        this.favorited = favorited;
        this.publishedAt = publishedAt;
    }

    public News(String titulo, String author, String descripcion, String imagen, String channel, String url, String publishedAt) {
        this.titulo = titulo;
        this.author = author;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.channel = channel;
        this.url = url;
        this.favorited = false;
        this.publishedAt = publishedAt;
    }

    //setters and getters
    public String getTitulo() {
        return titulo;
    }
    public String getAuthor() {
        return author;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getImagen() {
        return imagen;
    }
    public String getUrl() {
        return url;
    }
    public Boolean getFavorited() {
        return favorited;
    }
    public String getChannel() {
        return channel;
    }
    public String getPublishedAt() {
        return publishedAt;
    }

    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
}





