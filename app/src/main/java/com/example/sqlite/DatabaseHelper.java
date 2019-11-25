package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String nombre_db = "estudiantes.db";
    private static final String nombre_tabla = "informacion";

    private static final String columa_1 = "ID";
    private static final String columa_2 = "Nombre";
    private static final String columa_3 = "Apellidos";
    private static final String columa_4 = "edad";
    private static final String columa_5 = "calificacion";

    // llamar para crear base de datos
    public DatabaseHelper(Context context) {

        super(context, nombre_db, null, 1);
    }

    // llamar para crear tablas
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE '" + nombre_tabla + "' (ID INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Apellidos TEXT, edad INTEGER, calificacion INTEGER)");
    }

    // elimina existente y genera otra nueva
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + nombre_tabla);
        onCreate(db);
    }

    // metodo para insercion
    public boolean insertarDatos (String id, String nombres, String apellidos, String edad, String promedio) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(columa_1, id);
        cv.put(columa_2, nombres);
        cv.put(columa_3, apellidos);
        cv.put(columa_4, edad);
        cv.put(columa_5, promedio);

        // el metodo .insert regresa un -1 si no funciona,
        // asi que comparamos para ver si si se inserto
        long seInserto = db.insert(nombre_tabla,null,cv);
        return (seInserto == -1);
    }

    // metodo usando cursor para obtener
    // acceso leer/escribir a la bd
    public Cursor obtenerTodo() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + nombre_tabla, null);
        return res;
    }

    // metodo para actualizar
    public boolean actualizarDato(String id, String nombres, String apellidos, String edad, String promedio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(columa_1, id);
        cv.put(columa_2, nombres);
        cv.put(columa_3, apellidos);
        cv.put(columa_4, edad);
        cv.put(columa_5, promedio);
        db.update(nombre_tabla, cv, "ID = ?", new String[]{id});
        return true;
    }

    // metodo para eliminar
    public Integer eliminarDato(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(nombre_tabla, "ID = ?", new String[]{id});
    }
}
