package com.projectviiigps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.projectviiigps.clases.MailJob;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecuperarContrasena extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena);

        Button btnRecuperarContrasena = findViewById(R.id.btnRecuperarContrasena);
        final EditText correo = findViewById(R.id.ingreseCorreo);


        btnRecuperarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {


                final String repcorreo = correo.getText().toString();
                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            if (response!= "")
                                {
                            JSONArray jsonRespuesta = new JSONArray(response);


                            if ( true ){

                                for (int i=0; i<jsonRespuesta.length();i++){
                                    JSONObject jsonObject = jsonRespuesta.getJSONObject(i);
                                    String correo = String.valueOf(jsonObject.getString("correo"));
                                    String clave = String.valueOf(jsonObject.getString("clave"));

                                    new MailJob("parapruebas650@gmail.com", "Pruebas650").execute(
                                            new MailJob.Mail("parapruebas650@gmail.com", correo, "Restablecer  contraseña", "Su contraseña:" +clave)
                                    );
                                    AlertDialog.Builder alerta =new AlertDialog.Builder( RecuperarContrasena.this);
                                    alerta.setMessage("enviado a su correo, reviselo!!")
                                            .setNegativeButton( "ok", null)
                                            .create()
                                            .show();
                                }
                            }
                            else {
                                AlertDialog.Builder alerta =new AlertDialog.Builder( RecuperarContrasena.this);
                                alerta.setMessage("Fallo en el login")
                                        .setNegativeButton( "Reintentar", null)
                                        .create()
                                        .show();
                            }
                                }
                            else {
                                AlertDialog.Builder alerta =new AlertDialog.Builder( RecuperarContrasena.this);
                                alerta.setMessage("Datos vacios,ingrese su correo valido en la cuenta")
                                        .setNegativeButton( "Reintentar", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e){
                            e.getMessage();
                            AlertDialog.Builder alerta =new AlertDialog.Builder( RecuperarContrasena.this);
                            alerta.setMessage(e.getMessage())
                                    .setNegativeButton( "Reintentar", null)
                                    .create()
                                    .show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }



                };

                ClaseRecuperarContrasena r = new ClaseRecuperarContrasena(repcorreo, respuesta);
                RequestQueue cola = Volley.newRequestQueue( RecuperarContrasena.this);
                cola.add(r);
            }

        });



    }



}


