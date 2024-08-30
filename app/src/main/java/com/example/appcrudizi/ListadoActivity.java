package com.example.appcrudizi;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListadoActivity extends AppCompatActivity {
    private ListView listViewProductos;
    private EditText et_filtro;
    private ArrayList<String> listaProductos;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        listViewProductos = findViewById(R.id.listViewProductos);
        et_filtro = findViewById(R.id.et_filtro);

        listaProductos = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaProductos);
        listViewProductos.setAdapter(adapter);

        cargarProductos();
    }

    private void cargarProductos() {
        listaProductos.clear();
        Administrador admin = new Administrador(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        Cursor cursor = BaseDeDatos.rawQuery("SELECT * FROM articulos", null);

        if (cursor.moveToFirst()) {
            do {
                String producto = cursor.getString(0) + " - " +
                        cursor.getString(1) + " - $" +
                        cursor.getString(2);
                listaProductos.add(producto);
            } while (cursor.moveToNext());
        }

        cursor.close();
        BaseDeDatos.close();
        adapter.notifyDataSetChanged();
    }

    public void Filtrar(View view) {
        String filtro = et_filtro.getText().toString().toLowerCase();
        ArrayList<String> listaFiltrada = new ArrayList<>();

        for (String producto : listaProductos) {
            if (producto.toLowerCase().contains(filtro)) {
                listaFiltrada.add(producto);
            }
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaFiltrada);
        listViewProductos.setAdapter(adapter);
    }

    public void Regresar(View view) {
        finish();
    }
}