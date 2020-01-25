package com.projectviiigps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Reportes extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {
    ListView listaResultado;
    private GoogleMap mMap;
    Button btnGuardar, btnCargar;
    LatLng position = null;
    private ListView lv;
    PolylineOptions opcionesPoliLinea = null;
    ArrayList<String> lista = new ArrayList<>();
    ArrayList<String> lista1 = new ArrayList<>();
    ArrayList<String> lista2 = new ArrayList<>();
    private EditText edtnombre;
    List<LatLng> points = new ArrayList<LatLng>();
    LatLng center = null;
    private static final String CERO = "0";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Widgets
    EditText etFecha;
    ImageButton ibObtenerFecha;
    DatePicker picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);
        edtnombre = (EditText) findViewById(R.id.et_mostrar_fecha_picker);
        etFecha = (EditText) findViewById(R.id.et_mostrar_fecha_picker);
        //Widget ImageButton del cual usaremos el evento clic para obtener la fecha
        ibObtenerFecha = (ImageButton) findViewById(R.id.ib_obtener_fecha);
        //Evento setOnClickListener - clic
        ibObtenerFecha.setOnClickListener(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }
    private void obtenerFecha() {
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10) ? CERO + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10) ? CERO + String.valueOf(mesActual) : String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado

                etFecha.setText(year +"-"+ mesFormateado +"-"+ diaFormateado);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        }, anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }
    public JSONArray ja = null;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }
    public void EnviarRecibirDatos(View view) {
        // String consulta = "https://localizadorgps.000webhostapp.com/consultarReserva.php";
        String consulta = "https://appgpsmovil.000webhostapp.com/webservices/consultarlocalizacion.php?nombrenino=" + etFecha.getText() + "";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, consulta, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");
                if (response.length() > 0) {
                    try {
                        ja = new JSONArray(response);
                        Log.i("sizejson", "" + ja.length());
                        CargarListView(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void llamada(View view) {

        try {
            EnviarRecibirDatos(view);
            btnSatelite(view);
            if (lis == null || lis.size() == 0) {
                Toast.makeText(getApplicationContext(), "Intente nuevamente", Toast.LENGTH_SHORT).show();
            } else {
                // el arraylist tiene valor
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Intente nuevamente", Toast.LENGTH_SHORT).show();
        }


    }


    String[] caso = null;
    String[] caso2;

    ArrayList<Double> lis = new ArrayList<>();
    ArrayList<Double> lis2 = new ArrayList<>();
    ArrayList<String> lis3 = new ArrayList<>();
    LatLng punto = null;

    public void CargarListView(JSONArray ja) {

        int z = 0;
        for (int i = 0; i < ja.length(); i += 3) {

            try {

                lista.add(ja.getString(i) + " " + ja.getString(i + 1) + " " + ja.getString(i + 2));
                lista1.add(ja.getString(i));
                lista2.add(ja.getString(i + 1));
                lis.add(ja.getDouble(i));
                lis2.add(ja.getDouble(i + 1));
                lis3.add(ja.getString(i + 2));
                if (center == null) {
                    center = new LatLng(ja.getDouble(i), ja.getDouble(i + 1));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }


    private void muestraRectangulo() {

        PolylineOptions lineas = new PolylineOptions()
                .add(new LatLng(45.0, -12.0))
                .add(new LatLng(45.0, 5.0))
                .add(new LatLng(34.5, 5.0))
                .add(new LatLng(34.5, -12.0))
                .add(new LatLng(45.0, -12.0));


        lineas.width(8);
        lineas.color(Color.RED);
        mMap.addPolyline(lineas);

    }

    private void mostrarlineasMysql4() {

        PolylineOptions rec = new PolylineOptions();
        for (int f = 0; f < lis.size(); f++) {
            rec.add(new LatLng(lis.get(f), lis2.get(f)));
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lis.get(f), lis2.get(f)))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .title(lis3.get(f)));
            marker.showInfoWindow();
        }
        rec.width(8);

        //Definimos el color de la Polilíneas
        rec.color(Color.CYAN);
        mMap.addPolyline(rec);

    }

    private void mostrarlineasMysql2() {
        for (int i = 0; i < lis.size(); i++) {
            double val = Double.parseDouble(lista1.get(i));
            double val2 = Double.parseDouble(lista2.get(i));

        }

        PolylineOptions rec = new PolylineOptions();
        for (int f = 0; f < lis.size(); f++) {
            rec.add(new LatLng(lis.get(f), lis2.get(f)));
            LatLng punto = new LatLng(lis.get(f), lis2.get(f));


        }
        rec.width(2);
        //Definimos el color de la Polilíneas
        rec.color(Color.RED);
        mMap.addPolyline(rec);

    }

    double val = 0.0;
    double val2 = 0.0;

    private void mostrarlineasMysql() {


        double val = Double.parseDouble(lista1.get(0));
        double val2 = Double.parseDouble(lista1.get(1));
        double val3 = Double.parseDouble(lista2.get(0));
        double val4 = Double.parseDouble(lista2.get(1));
        double val5 = Double.parseDouble(lista1.get(2));
        double val6 = Double.parseDouble(lista1.get(3));
        double val7 = Double.parseDouble(lista2.get(2));
        double val8 = Double.parseDouble(lista2.get(3));
        PolylineOptions rec = new PolylineOptions();
        rec.add(new LatLng(val, val3), new LatLng(val2, val4),
                new LatLng(val5, val7), new LatLng(val6, val8));
        rec.width(8);
        //Definimos el color de la Polilíneas
        rec.color(Color.RED);
        mMap.addPolyline(rec);


    }


    private void mostrarLineasDefecto() {

        PolylineOptions rec = new PolylineOptions();
        rec.add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0));
        rec.width(2);
        //Definimos el color de la Polilíneas
        rec.color(Color.RED);
        mMap.addPolyline(rec);

    }

    private void mostrarLineasDefecto2() {
        //Dibujo con Lineas
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0))
                .width(5)
                .color(Color.RED));


    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    public void btnSatelite(View view) {

        mMap.clear();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        CameraUpdate camUpd1 = CameraUpdateFactory.newLatLngZoom(new LatLng(center.latitude, center.longitude), 15);
        mMap.moveCamera(camUpd1);

        //mostrarLineas();
        //muestraRectangulo();
        //mostrarLineasDefecto2();
        mostrarlineasMysql4();


        //Definimos el grosor de las Polilíneas


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_obtener_fecha:
                obtenerFecha();
                break;
        }
    }
}



//LatLng sydney = new LatLng(-34, 151);
// mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
// CameraUpdate camUpd1 = (CameraUpdateFactory.newLatLngZoom(center, 13));
//mMap.moveCamera(camUpd1);


