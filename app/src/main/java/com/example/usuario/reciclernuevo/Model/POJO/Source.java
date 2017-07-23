package com.example.usuario.reciclernuevo.Model.POJO;

public class Source {

    private String id;
    private String name;
    private String category;
    private String country;

    public Source(String id, String name, String category, String country) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getCountry() {
        return country;
    }

}
