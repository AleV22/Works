package com.example.usuario.reciclernuevo.Model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.usuario.reciclernuevo.Model.POJO.News;
import com.example.usuario.reciclernuevo.Model.POJO.Source;

import java.util.ArrayList;
import java.util.List;


public class DAOSourceDatabase extends DatabaseHelper {

    public static final String TABLE_SOURCE = "source";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String CATEGORY = "category";
    public static final String COUNTRY = "country";

    public DAOSourceDatabase(Context context) {
        super(context);
    }

    public void addSource(Source source) {
        if (!isSourceInDB(source.getId())){
            SQLiteDatabase database = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(ID, source.getId());
            values.put(NAME,source.getName());
            values.put(CATEGORY, source.getCategory());
            values.put(COUNTRY, source.getCountry());

            database.insert(TABLE_SOURCE, null, values);
            //cerramos la base de datos
            database.close();
        }
    }

    public void addSourceList (List<Source> listaDeSource){
        for (Source source : listaDeSource) {
            addSource(source);
        }
    }

    public Boolean isSourceInDB(String id) {
        SQLiteDatabase database = getReadableDatabase();
        String query = "SELECT " + ID +
                " FROM " + TABLE_SOURCE +
                " WHERE " + ID + " = " + "'" + id + "'";

        Cursor cursor = database.rawQuery(query, null);

        Boolean isSource = cursor.moveToNext();
        cursor.close();
        database.close();

        return isSource;
    }

    public List<Source> getListSourceInDatabase(){
        List<Source> listSource = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_SOURCE;

        Cursor cursor = database.rawQuery(query, null);

        while(cursor.moveToNext()){

            String id = cursor.getString(cursor.getColumnIndex(ID));
            String name = cursor.getString(cursor.getColumnIndex(NAME));
            String category = cursor.getString(cursor.getColumnIndex(CATEGORY));
            String country = cursor.getString(cursor.getColumnIndex(COUNTRY));

            Source source = new Source(id, name, category, country);
            listSource.add(source);
        }

        cursor.close();
        database.close();

        return listSource;
    }



}
