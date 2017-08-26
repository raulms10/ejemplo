package com.example.rantoniomartinez.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String TAG = "TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void guardarRegistro(View view){
        Toast.makeText(this, "Guardando", Toast.LENGTH_SHORT).show();

        DbHelper dbHelper = new DbHelper(this); //Instancia de DbHelper
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //Obtenemos la instancia de la BD

        ContentValues values = new ContentValues(); //Creamos el contenedor
        values.put(StatusContract.Column.ID, "1"); //Se pasan pares clave-valor
        values.put(StatusContract.Column.USER, "student");
        values.put(StatusContract.Column.MESSAGE, "mensaje de prueba");
        values.put(StatusContract.Column.CREATED_AT, "15/10/2016 08:00:00");
        db.insertWithOnConflict(StatusContract.TABLE, null, values,
                SQLiteDatabase.CONFLICT_IGNORE); //Se guarda la fila en la base de datos


    }

    public void consultarRegistro(View view){
        Toast.makeText(this, "Consultando", Toast.LENGTH_SHORT).show();

        DbHelper dbHelper = new DbHelper(this); //Instancia de DbHelper
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //Obtenemos instancia de la BD

        //Creamos el String de la consulta SQL
        String consultaSQL = "select * from " + StatusContract.TABLE;

        //Hacemos la consulta a la BD y guardamos los resultados en el cursor
        Cursor cursor = db.rawQuery(consultaSQL, null); //Hacemos la consulta

        //Recorremos los datos en el cursor
        while (cursor.moveToNext()){
            //Obtenemos cada dato del cursor
            Log.d(TAG, "ID: "+cursor.getString(cursor.getColumnIndex(StatusContract.Column.ID)));
            Log.d(TAG, "User: "+cursor.getString(cursor.getColumnIndex(StatusContract.Column.USER)));
            Log.d(TAG, "Message: "+cursor.getString(cursor.getColumnIndex(StatusContract.Column.MESSAGE)));
            Log.d(TAG, "Created At: "+cursor.getString(cursor.getColumnIndex(StatusContract.Column.CREATED_AT)));
        }

        Log.d(TAG,"Hay: "+cursor.getCount());

    }



    public void eliminarRegistro(View view){
        Toast.makeText(this, "Eliminando", Toast.LENGTH_SHORT).show();

        DbHelper dbHelper = new DbHelper(this); //Instancia de DbHelper
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //Obtenemos instancia de la BD

        //Creamos el String con el criterio para hacer la eliminación
        String criterio = StatusContract.Column.ID + "=" + "1";

        //Hacemos el borrado
        db.delete(StatusContract.TABLE, criterio, null);

    }

    public void actualizarRegistro(View view){
        Toast.makeText(this, "Actualizando", Toast.LENGTH_SHORT).show();

        DbHelper dbHelper = new DbHelper(this); //Instancia de DbHelper
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //Obtenemos instancia de la BD

        //Creamos el String con el criterio para hacer la actualización
        String criterio = StatusContract.Column.ID + "=" + "1";

        ContentValues values = new ContentValues(); //Creamos el contenedor
        //Agregamos los valores a actualizar clave-valor
        values.put(StatusContract.Column.MESSAGE, "mensaje de prueba actualizado");

        //Hacemos la actualización
        db.updateWithOnConflict(StatusContract.TABLE, values, criterio, null, SQLiteDatabase.CONFLICT_IGNORE);

    }
}
