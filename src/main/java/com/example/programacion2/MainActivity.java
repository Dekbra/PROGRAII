package com.example.programacion2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    Button btn;
    FloatingActionButton fab;
    TextView tempVal;
    String accion = "nuevo";
    String id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fabRegresarLtsAmigos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActividad();
            }
        });
        btn = findViewById(R.id.btnGuardarAgendaAmigos);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    tempVal = findViewById(R.id.txtNombre);
                    String nombre = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtDireccion);
                    String direccion = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtTelefono);
                    String tel = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtEmail);
                    String email = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtDui);
                    String dui = tempVal.getText().toString();

                    DB db = new DB(getApplicationContext(), "",null, 1);
                    String[] datos = new String[]{id,nombre,direccion,tel,email,dui};
                    mostrarMsg(accion);
                    String respuesta = db.administrar_amigos(accion, datos);
                    if(respuesta.equals("ok")){
                        Toast.makeText(getApplicationContext(), "Amigo guardado con exito", Toast.LENGTH_LONG).show();
                        abrirActividad();
                    }else{
                        Toast.makeText(getApplicationContext(), "Error al intentar guardar el amigo: "+ respuesta, Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Error: "+ e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        mostrarDatosAmigos();
    }
    private void mostrarDatosAmigos(){
        try{
            Bundle parametros = getIntent().getExtras();//Recibir los parametros...
            accion = parametros.getString("accion");

            if(accion.equals("modificar")){
                String[] amigos = parametros.getStringArray("amigos");
                id = amigos[0];

                tempVal = findViewById(R.id.txtNombre);
                tempVal.setText(amigos[1]);

                tempVal = findViewById(R.id.txtDireccion);
                tempVal.setText(amigos[2]);

                tempVal = findViewById(R.id.txtTelefono);
                tempVal.setText(amigos[3]);

                tempVal = findViewById(R.id.txtEmail);
                tempVal.setText(amigos[4]);

                tempVal = findViewById(R.id.txtDui);
                tempVal.setText(amigos[5]);
            }
        }catch (Exception e){
            mostrarMsg("Error al mostrar datos: "+ e.getMessage());
        }
    }
    private void mostrarMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    private void abrirActividad(){
        Intent abrirActividad = new Intent(getApplicationContext(), listado_amigos.class);
        startActivity(abrirActividad);
    }
}