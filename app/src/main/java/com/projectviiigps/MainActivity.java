package com.projectviiigps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.projectviiigps.Others.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout expandableView;
    Button arrowBtn;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String user = Preferences.obtenerPreferenceStringNombre(this, Preferences.PREFERENCE_USUARIO_LOGIN_NOMBRE);
        String correo = Preferences.obtenerPreferenceStringId(this,Preferences.PREFERENCE_USUARIO_LOGIN_ID);
        String pass = Preferences.obtenerPreferenceStringContrasenia(this,Preferences.PREFERENCE_USUARIO_LOGIN_CONTRASENIA);

        if(!user.equals("")&& !pass.equals("")){
            Intent bienvenido = new Intent( MainActivity.this, Bienvenido.class);
            startActivity(bienvenido);
            finish();
        }

        TextView registro= findViewById(R.id.RegistroLogin);
        Button btnlogin = findViewById(R.id.btnLogin);

        final EditText usuarioT = findViewById(R.id.edtUsuario);
        final EditText claveT = findViewById(R.id.edtPassword);

        expandableView = findViewById(R.id.expandableView);
        arrowBtn = findViewById(R.id.arrowBtn);
        cardView = findViewById(R.id.cardView);



        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registro = new Intent (MainActivity.this, Registro.class);
                MainActivity.this.startActivity(registro);
                finish();
            }
        });


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                final String usuario = usuarioT.getText().toString();
                final String clave = claveT.getText().toString();
                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonRespuesta = new JSONObject(response);
                            boolean ok =jsonRespuesta.getBoolean("success");


                            if ( ok==true ){

                                //int idpadre1 = jsonRespuesta.getInt("idpadre");//esto
                                String nombre = jsonRespuesta.getString("nombre");
                                String apellido = jsonRespuesta.getString("apellido");
                                String direccion = jsonRespuesta.getString("direccion");
                                int telefono = jsonRespuesta.getInt("telefono");
                                String correo = jsonRespuesta.getString("correo");

                                Preferences.savePreferenceStringId(MainActivity.this,jsonRespuesta.getString("idpadre"),
                                        Preferences.PREFERENCE_USUARIO_LOGIN_ID);
                                Preferences.savePreferenceStringNombre(MainActivity.this,
                                        (jsonRespuesta.getString("nombre")+" "+ jsonRespuesta.getString("apellido")),
                                        Preferences.PREFERENCE_USUARIO_LOGIN_NOMBRE);
                                Preferences.savePreferenceStringTelefono(MainActivity.this,jsonRespuesta.getString("telefono"),
                                        Preferences.PREFERENCE_USUARIO_LOGIN_TELEFONO);
                                Preferences.savePreferenceStringCorreo(MainActivity.this,jsonRespuesta.getString("correo"),
                                        Preferences.PREFERENCE_USUARIO_LOGIN_CORREO);
                                Preferences.savePreferenceStringDireccion(MainActivity.this,jsonRespuesta.getString("direccion"),
                                        Preferences.PREFERENCE_USUARIO_LOGIN_DIRECCION);
                                Preferences.savePreferenceStringContrasenia(MainActivity.this,
                                        jsonRespuesta.getString("clave"),
                                        Preferences.PREFERENCE_USUARIO_LOGIN_CONTRASENIA);

                                //int edad =jsonRespuesta.getInt("edad");
                                Intent bienvenido = new Intent( MainActivity.this, Bienvenido.class);
                                bienvenido.putExtra("nombre",nombre);
                                bienvenido.putExtra("apellido",apellido);
                                bienvenido.putExtra("direccion",direccion);
                                bienvenido.putExtra("telefono",telefono);
                                bienvenido.putExtra("correo",correo);
                              //  bienvenido.putExtra("idpadre",idpadre1);
                                startActivity(bienvenido);
                                //MainActivity.this.finish();

                            }
                            else {
                                AlertDialog.Builder alerta =new AlertDialog.Builder( MainActivity.this);
                                alerta.setMessage("Fallo en el login")
                                        .setNegativeButton( "Reintentar", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e){
                            e.getMessage();
                            AlertDialog.Builder alerta =new AlertDialog.Builder( MainActivity.this);
                            alerta.setMessage(e.getMessage())
                                    .setNegativeButton( "Reintentar", null)
                                    .create()
                                    .show();
                        }
                    }



                };

                LoginRequest r = new LoginRequest(usuario,clave, respuesta);
                RequestQueue cola = Volley.newRequestQueue( MainActivity.this);
                cola.add(r);
            }

        });





    }
}
