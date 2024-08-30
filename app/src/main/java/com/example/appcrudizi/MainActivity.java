package com.example.appcrudizi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et_codigo, et_descripcion, et_precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_codigo = (EditText) findViewById(R.id.txt_codigo);
        et_descripcion = (EditText) findViewById(R.id.txt_descripcion);
        et_precio = (EditText) findViewById(R.id.txt_precio);
    }

    public void Registrar(View view) {
        Administrador admin = new Administrador(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = null;
        try {
            BaseDeDatos = admin.getWritableDatabase();
            String codigo = et_codigo.getText().toString();
            String descripcion = et_descripcion.getText().toString();
            String precio = et_precio.getText().toString();
            if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()) {
                ContentValues registro = new ContentValues();
                registro.put("codigo", codigo);
                registro.put("descripcion", descripcion);
                registro.put("precio", precio);
                BaseDeDatos.insert("articulos", null, registro);
                et_codigo.setText("");
                et_descripcion.setText("");
                et_precio.setText("");
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
            }
        } finally {
            if (BaseDeDatos != null) {
                BaseDeDatos.close();
            }
        }
    }

    public void Buscar(View view) {
        Administrador admin = new Administrador(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = null;
        try {
            BaseDeDatos = admin.getWritableDatabase();
            String codigo = et_codigo.getText().toString();
            if (!codigo.isEmpty()) {
                Cursor fila = BaseDeDatos.rawQuery(
                        "SELECT descripcion, precio FROM articulos WHERE codigo = ?",
                        new String[]{codigo});
                if (fila.moveToFirst()) {
                    et_descripcion.setText(fila.getString(0));
                    et_precio.setText(fila.getString(1));
                } else {
                    Toast.makeText(this, "No existe el artículo", Toast.LENGTH_SHORT).show();
                }
                fila.close();
            } else {
                Toast.makeText(this, "Debes introducir el código del artículo", Toast.LENGTH_SHORT).show();
            }
        } finally {
            if (BaseDeDatos != null) {
                BaseDeDatos.close();
            }
        }
    }

    public void Eliminar(View view) {
        Administrador admin = new Administrador(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = null;
        try {
            BaseDeDatos = admin.getWritableDatabase();
            String codigo = et_codigo.getText().toString();
            if (!codigo.isEmpty()) {
                int cantidad = BaseDeDatos.delete("articulos", "codigo = ?", new String[]{codigo});
                et_codigo.setText("");
                et_descripcion.setText("");
                et_precio.setText("");
                if (cantidad == 1) {
                    Toast.makeText(this, "Artículo eliminado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "El artículo no existe", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Debes introducir el código del artículo", Toast.LENGTH_SHORT).show();
            }
        } finally {
            if (BaseDeDatos != null) {
                BaseDeDatos.close();
            }
        }
    }

    public void Modificar(View view) {
        Administrador admin = new Administrador(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = null;
        try {
            BaseDeDatos = admin.getWritableDatabase();
            String codigo = et_codigo.getText().toString();
            String descripcion = et_descripcion.getText().toString();
            String precio = et_precio.getText().toString();
            if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()) {
                ContentValues valores = new ContentValues();
                valores.put("descripcion", descripcion);
                valores.put("precio", precio);
                int cantidad = BaseDeDatos.update("articulos", valores, "codigo = ?", new String[]{codigo});
                if (cantidad > 0) {
                    Toast.makeText(this, "Artículo modificado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "El artículo no existe", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
            }
        } finally {
            if (BaseDeDatos != null) {
                BaseDeDatos.close();
            }
        }
    }

    public void Limpiar(View view) {
        et_codigo.setText("");
        et_descripcion.setText("");
        et_precio.setText("");
    }

    public void IrAListadoActivity(View view) {
        Intent intent = new Intent(this, ListadoActivity.class);
        startActivity(intent);
    }
}