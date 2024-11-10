package com.example.vmusic;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

public class patioActivity extends AppCompatActivity {

    // Declaración de variables para los elementos del layout
    EditText edtNombreCancion, edtNombreAlbum, edtDireccion;
    Spinner spSpinnerGenero;
    ListView lstListaCanciones;
    DataHelper dataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patio);

        // Inicialización de los elementos del formulario
        edtNombreCancion = findViewById(R.id.edtNombreCancion);
        edtNombreAlbum = findViewById(R.id.edtNombreAlbum);
        edtDireccion = findViewById(R.id.edtDireccion);
        spSpinnerGenero = findViewById(R.id.spSpinnerGenero);
        lstListaCanciones = findViewById(R.id.lstListaCanciones);

        // Inicialización de DataHelper
        dataHelper = new DataHelper(this);

        // Llenado del Spinner con los años
        String[] años = new String[]{"Año 2023", "Año 2024", "Año 2025"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, años);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSpinnerGenero.setAdapter(spinnerAdapter);

        // Cargar la lista de canciones al iniciar
        cargarListaCanciones();
    }


    // Método para cargar la lista de canciones desde la base de datos
    public void cargarListaCanciones() {
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nombreCancion, nombreAlbum, direccion, genero FROM musica", null);

        String[] canciones = new String[cursor.getCount()];
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                canciones[i] = "Canción: " + cursor.getString(0) + "\nÁlbum: " + cursor.getString(1) +
                        "\nDirección: " + cursor.getString(2) + "\nGénero: " + cursor.getString(3);
                i++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, canciones);
        lstListaCanciones.setAdapter(adapter);
    }

    // Método para agregar una canción
    public void onClickAgregar(View view) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombreCancion", edtNombreCancion.getText().toString());
        values.put("nombreAlbum", edtNombreAlbum.getText().toString());
        values.put("direccion", edtDireccion.getText().toString());
        values.put("genero", spSpinnerGenero.getSelectedItem().toString());

        long result = db.insert("musica", null, values);
        db.close();

        if (result == -1) {
            Toast.makeText(this, "Error al agregar la canción", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Canción agregada", Toast.LENGTH_LONG).show();
            limpiarCampos();
            cargarListaCanciones();
        }
    }

    // Método para modificar una canción
    public void onClickModificar(View view) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombreCancion", edtNombreCancion.getText().toString());
        values.put("nombreAlbum", edtNombreAlbum.getText().toString());
        values.put("direccion", edtDireccion.getText().toString());
        values.put("genero", spSpinnerGenero.getSelectedItem().toString());

        String nombreCancion = edtNombreCancion.getText().toString();
        int result = db.update("musica", values, "nombreCancion=?", new String[]{nombreCancion});
        db.close();

        if (result > 0) {
            Toast.makeText(this, "Canción modificada", Toast.LENGTH_LONG).show();
            limpiarCampos();
            cargarListaCanciones();
        } else {
            Toast.makeText(this, "No se encontró la canción para modificar", Toast.LENGTH_LONG).show();
        }
    }

    // Método para eliminar una canción
    public void onClickEliminar(View view) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();
        String nombreCancion = edtNombreCancion.getText().toString();
        int result = db.delete("musica", "nombreCancion=?", new String[]{nombreCancion});
        db.close();

        if (result > 0) {
            Toast.makeText(this, "Canción eliminada", Toast.LENGTH_LONG).show();
            limpiarCampos();
            cargarListaCanciones();
        } else {
            Toast.makeText(this, "No se encontró la canción para eliminar", Toast.LENGTH_LONG).show();
        }
    }

    // Método para limpiar los campos de entrada
    private void limpiarCampos() {
        edtNombreCancion.setText("");
        edtNombreAlbum.setText("");
        edtDireccion.setText("");
    }
}


