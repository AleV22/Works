package com.example.usuario.reciclernuevo.Model.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    //solo quedan constantes propias de la base de datos
    public static final String DATABASE_NAME = "miBase_db";
    public static final Integer DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //el + es para poder poner enter, esta concatenando nada mas
        //aqui se pueden tener la creacion de todas las tablas, una bajo la otra
        String query = "CREATE TABLE "+ DAONewsDatabase.TABLE_TITULO +" (" +
                DAONewsDatabase.TITULO + " TEXT NOT NULL, " +
                DAONewsDatabase.AUTHOR + " TEXT, " +
                DAONewsDatabase.DESCRIPCION + " TEXT NOT NULL, " +
                DAONewsDatabase.PHOTO + " TEXT NOT NULL, " +
                DAONewsDatabase.URL + " TEXT PRIMARY KEY, " +
                DAONewsDatabase.CHANNEL + " TEXT NOT NULL, " +
                DAONewsDatabase.FAVORITED + " INTEGER NOT NULL, " +
                DAONewsDatabase.PUBLISHEDAT + " TEXT);";
        db.execSQL(query);

        String querySource = "CREATE TABLE " + DAOSourceDatabase.TABLE_SOURCE +" (" +
                DAOSourceDatabase.ID + " TEXT PRIMARY KEY, " +
                DAOSourceDatabase.NAME + " TEXT NOT NULL, " +
                DAOSourceDatabase.CATEGORY + " TEXT NOT NULL, " +
                DAOSourceDatabase.COUNTRY + " TEXT NOT NULL);";
        db.execSQL(querySource);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //aca se  updatean las tablas que se tengan que updatear
    }

}
