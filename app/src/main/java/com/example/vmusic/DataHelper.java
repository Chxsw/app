package com.example.vmusic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "musica.db";
    private static final int DATABASE_VERSION = 1;

    // Constructor de DataHelper
    public DataHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla "musica" en la base de datos
        db.execSQL("CREATE TABLE musica (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombreCancion TEXT NOT NULL, " +
                "nombreAlbum TEXT, " +
                "direccion TEXT, " +
                "genero TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Actualizar la base de datos eliminando y recreando la tabla
        db.execSQL("DROP TABLE IF EXISTS musica");
        onCreate(db);
    }
}
