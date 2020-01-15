package com.projectviiigps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.projectviiigps.Others.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import static java.security.AccessController.getContext;

public class RegistroHijo extends AppCompatActivity {

    private EditText idpadre1;
    private String id_padre;

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

        final EditText codigo              = findViewById(R.id.RegistroCodigop);

        id_padre = Preferences.obtenerPreferenceStringId(this,
                Preferences.PREFERENCE_USUARIO_LOGIN_ID);
        codigo.setText(id_padre);


       // Toast.makeText(this, "Id_padre: " + id_padre, Toast.LENGTH_LONG).show();


        btnRegistro.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent i =getIntent();
                /*final int id = i.getExtras().getInt("idpadre");
                idpadre1.setText(Integer.toString(id));*/
                String nombreh       = nombre.getText().toString();
                String apellidoh       = apellido.getText().toString();
                int    edadh         =  Integer.parseInt( edad.getText().toString());
                String direccionh    = direccion.getText().toString();
                String tiposangre    = tiposangreh.getText().toString();
                String enfermedad    = enfermedadh.getText().toString();
                String alergia    = alergiah.getText().toString();



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
                RegistroHijoRequest r = new RegistroHijoRequest( Integer.parseInt(id_padre),
                        nombreh,apellidoh,edadh,direccionh,tiposangre, enfermedad, alergia, respuesta);
                RequestQueue cola = Volley.newRequestQueue( RegistroHijo.this);
                cola.add(r);
            }

        });

    }

}
