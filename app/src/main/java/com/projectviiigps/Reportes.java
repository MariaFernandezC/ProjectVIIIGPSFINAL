package com.projectviiigps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Reportes extends AppCompatActivity {
    private EditText etLocalizacion, etFechaLocalizacion,edtnombre;
    private Button btnCargar;
    private ListView listaResultado;
    private AsyncHttpClient cliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        etLocalizacion = (EditText)findViewById(R.id.etLocalizacion);
        etFechaLocalizacion = (EditText)findViewById(R.id.etFechaLocalizacion);
        btnCargar = (Button)findViewById(R.id.btnLoad);
        listaResultado = (ListView)findViewById(R.id.lvLista);
        cliente = new AsyncHttpClient();
        // obtenerlocalizacion();
        botoncargar();
    }

    private void botoncargar(){
        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etLocalizacion.getText().toString().isEmpty()|| etFechaLocalizacion.getText().toString().isEmpty()){
                    Toast.makeText(Reportes.this, "Hay campos vacios", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    obtenerlocalizacion();
                }
            }
        });
    }



    private void obtenerlocalizacion(){
        String url="http://appgpsmovil.000webhostapp.com/webservices/obtenerlocalizacion.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    listarlocalizacion(new String (responseBody));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    private void listarlocalizacion(String respuesta){
        ArrayList<Localizacion_clase> lista = new ArrayList<Localizacion_clase>();
        try {
            JSONArray jsonArreglo =new JSONArray(respuesta);
            for (int i=0; i<jsonArreglo.length();i++){
                Localizacion_clase L= new Localizacion_clase();
                L.setId(jsonArreglo.getJSONObject(i).getInt("idlocalizacion"));
                L.setNombrenino(jsonArreglo.getJSONObject(i).getString("nombrenino"));
                L.setLatitud(jsonArreglo.getJSONObject(i).getString("latitud"));
                L.setLongitud(jsonArreglo.getJSONObject(i).getString("longitud"));
                L.setFecha(jsonArreglo.getJSONObject(i).getString("fecha"));
                L.setHora(jsonArreglo.getJSONObject(i).getString("hora"));
                lista.add(L);

            }
            ArrayAdapter<Localizacion_clase> localizar =new ArrayAdapter(this, android.R.layout.simple_list_item_1,lista);
            listaResultado.setAdapter(localizar);
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }


}
