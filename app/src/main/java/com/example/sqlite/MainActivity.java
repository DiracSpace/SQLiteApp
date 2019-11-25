package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // instancia de clase
    DatabaseHelper db;

    // variables de botones
    Button m, i, a, e, dbb;

    // variables para texto
    EditText uid, nom, ap,ed, prom;

    // variables para datos de usuario
    String id, nombres, apellidos, edad, promedio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // conectar con botones de interfaz
        m = findViewById(R.id.mostrar);
        i = findViewById(R.id.insertar);
        a = findViewById(R.id.actualizar);
        e = findViewById(R.id.eliminar);
        dbb = findViewById(R.id.click);

        // conectar cajas de texto con interfaz
        uid = findViewById(R.id.id);
        nom = findViewById(R.id.nombres);
        ap = findViewById(R.id.apellidos);
        ed = findViewById(R.id.edad);
        prom = findViewById(R.id.promedio);

        // deteccion de clicks en botones
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonM();
            }
        });

        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonI();
            }
        });

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonA();
            }
        });

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonE();
            }
        });

        dbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonDBB();
            }
        });
    }

    public void botonM() {
        // mostrar todos los dato
        try {
            Cursor res = db.obtenerTodo();
            if (res.getCount() == 0) {
                Toast.makeText(this,"No hay informacion",Toast.LENGTH_LONG).show();
            } else {
                StringBuilder buffer = new StringBuilder();
                // guardo los datos en un array
                while (res.moveToNext()) {
                    buffer.append("ID: ").append(res.getString(0)).append("\n");
                    buffer.append("Nombres: ").append(res.getString(1)).append("\n");
                    buffer.append("Apellidos: ").append(res.getString(2)).append("\n");
                    buffer.append("Edad: ").append(res.getString(3)).append("\n");
                    buffer.append("Promedio: ").append(res.getString(4)).append("\n\n");
                }
                // mostrar los datos
                mostrarTodo("Datos", buffer.toString());
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error at: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void botonI() {
        // insertar datos
        try {
            id = uid.getText().toString();
            nombres = nom.getText().toString();
            apellidos = ap.getText().toString();
            edad = ed.getText().toString();
            promedio = prom.getText().toString();

            if ((id.matches("")) || (nombres.matches("")) || (apellidos.matches("")) || (edad.matches("")) || (promedio.matches(""))) {
                Toast.makeText(this,"Ingresa datos",Toast.LENGTH_LONG).show();
            } else {
                boolean fueInsertada = db.insertarDatos(id,nombres,apellidos,edad,promedio);
                if (fueInsertada) {
                    Toast.makeText(this,"Se insertaron los datos",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this,"No se insertaron los datos",Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this,"Genera la base de datos primero",Toast.LENGTH_LONG).show();
        }
    }

    public void botonA() {
        // actualizar datos
        try {
            id = uid.getText().toString();
            nombres = nom.getText().toString();
            apellidos = ap.getText().toString();
            edad = ed.getText().toString();
            promedio = prom.getText().toString();

            if ((id.matches("")) || (nombres.matches("")) || (apellidos.matches("")) || (edad.matches("")) || (promedio.matches(""))) {
                Toast.makeText(this,"Ingresa datos",Toast.LENGTH_LONG).show();
            } else {
                boolean fueActualizado = db.actualizarDato(id,nombres,apellidos,edad,promedio);
                if (fueActualizado) {
                    Toast.makeText(this,"Se actualizaron los datos",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this,"No se actualizaron los datos",Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this,"Error en: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void botonE() {
        // eliminar datos
        try {
            id = uid.getText().toString();

            if ((id.matches("")) || (nombres.matches("")) || (apellidos.matches("")) || (edad.matches("")) || (promedio.matches(""))) {
                Toast.makeText(this,"Ingresa datos",Toast.LENGTH_LONG).show();
            } else {
                Integer filasEliminadas = db.eliminarDato(id);
                if (filasEliminadas > 0) {
                    Toast.makeText(this,"Se eliminaron " + filasEliminadas + " filas",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this,"No se eliminaron filas",Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this,"Error en: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void botonDBB() {
        // crear bd
        try {
            db = new DatabaseHelper(this);
            Toast.makeText(this,"Se hizo la bd y tabla",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void mostrarTodo(String titulo, String mensaje) {
        AlertDialog.Builder db = new AlertDialog.Builder(this);
        db.setCancelable(true);
        db.setTitle(titulo);
        db.setMessage(mensaje);
        db.show();
    }
}
