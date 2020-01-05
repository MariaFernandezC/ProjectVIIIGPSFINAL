package com.projectviiigps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistroHijo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_hijo);
        final EditText nombre              = findViewById(R.id.RegistroNombresH);
        final EditText apellido           = findViewById(R.id.RegistroApellidosH);
        final EditText edad               = findViewById(R.id.RegistroEdadH);
        final EditText direccion          = findViewById(R.id.RegistroDireccionH);
        final EditText tiposangreh           = findViewById(R.id.RegistroTipoSangreH);
        final EditText enfermedadh           = findViewById(R.id.RegistroEnfermedadH);
        final EditText alergiah             = findViewById(R.id.RegistroAlergiaH);
        Button btnRegistro                  = findViewById(R.id.btnRegistroH);


        btnRegistro.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String nombreh       = nombre.getText().toString();
                String apellidoh       = apellido.getText().toString();
                int    edadh         =  Integer.parseInt( edad.getText().toString());
                String direccionh    = direccion.getText().toString();
                String tiposangre    = tiposangreh.getText().toString();;
                String enfermedad    = enfermedadh.getText().toString();;
                String alergia    = alergiah.getText().toString();;



                Response.Listener <String> respuesta=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonRespuesta =new JSONObject(response);
                            boolean ok = jsonRespuesta.getBoolean("success");

                            if ( ok){
                               Intent i = new Intent(RegistroHijo.this, Bienvenido.class);
                               RegistroHijo.this.startActivity(i);
                               RegistroHijo.this.finish();


                            }else {
                                AlertDialog.Builder alerta =new AlertDialog.Builder( RegistroHijo.this);
                                alerta.setMessage("Fallo en el Registro")
                                        .setNegativeButton( "Reintertar", null)
                                        .create()
                                        .show();
                            }
                        }catch (JSONException e){
                            e.getMessage();
                        }
                    }
                };
                RegistroHijoRequest r = new RegistroHijoRequest(nombreh,apellidoh,edadh,direccionh,tiposangre, enfermedad, alergia, respuesta);
                RequestQueue cola = Volley.newRequestQueue( RegistroHijo.this);
                cola.add(r);
            }

        });

    }

}
