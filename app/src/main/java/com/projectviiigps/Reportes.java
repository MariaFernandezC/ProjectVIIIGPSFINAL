package com.projectviiigps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Reportes extends FragmentActivity implements OnMapReadyCallback {
    ListView listaResultado;
    private GoogleMap mMap;
    Button btnGuardar, btnCargar;
    LatLng position = null;
    private ListView lv;
    PolylineOptions opcionesPoliLinea = null;
    ArrayList<String> lista = new ArrayList<>();
    ArrayList<String> lista1 = new ArrayList<>();
    ArrayList<String> lista2 = new ArrayList<>();
    double[] nums1 = new double[4];
    double[] nums2 = new double[4];
    private EditText edtnombre;
    LatLng center = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);
        edtnombre = (EditText) findViewById(R.id.btnSatelite);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    public JSONArray ja = null;

    public void EnviarRecibirDatos(View view) {
        String consulta = "https://appgpsmovil.000webhostapp.com/webservices/consultarReserva.php?nombrenino=" + edtnombre.getText() + "";

        Toast.makeText(getApplicationContext(), "" + consulta, Toast.LENGTH_SHORT).show();

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
        if (edtnombre != null) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            CameraUpdate camUpd1 = CameraUpdateFactory.newLatLngZoom(new LatLng(-1.0125, -79.4698), 5);
            mMap.moveCamera(camUpd1);
            LatLng punto = new LatLng(-1.0125, -79.4698);
            mMap.addMarker(new MarkerOptions().position(punto).title("Universidad Técnica Estatal de Quevedo"));
            //mostrarLineas();
            //muestraRectangulo();
            //mostrarLineasDefecto2();
            mostrarlineasMysql4();
        }

    }


    String[] caso = null;
    String[] caso2;
    List<LatLng> pointsList = new ArrayList<>();
    ArrayList<Double> lis = new ArrayList<>();
    ArrayList<Double> lis2 = new ArrayList<>();

    public void CargarListView(JSONArray ja) {

        int z = 0;
        for (int i = 0; i < ja.length(); i += 2) {

            try {

                lista.add(ja.getString(i) + " " + ja.getString(i + 1));
                lista1.add(ja.getString(i));
                lista2.add(ja.getString(i + 1));
                lis.add(ja.getDouble(i));
                lis2.add(ja.getDouble(i + 1));

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
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lis.get(f), lis2.get(f)))
                    .title("Hello world"));
        }
        rec.width(8);

        //Definimos el color de la Polilíneas
        rec.color(Color.RED);
        mMap.addPolyline(rec);

    }

    private void mostrarlineasMysql2() {
        for (int i = 0; i < lis.size(); i++) {
            double val = Double.parseDouble(lista1.get(i));
            double val2 = Double.parseDouble(lista2.get(i));
            nums1[i] = val;
            nums2[i] = val2;
        }

        PolylineOptions rec = new PolylineOptions();
        for (int f = 0; f < lis.size(); f++) {
            rec.add(new LatLng(lis.get(f), lis2.get(f)));
            LatLng punto = new LatLng(lis.get(f), lis2.get(f));

            mMap.addMarker(new MarkerOptions().position(punto).title("Universidad Técnica Estatal de Quevedo"));

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
    }

    public void btnSatelite(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        CameraUpdate camUpd1 = CameraUpdateFactory.newLatLngZoom(new LatLng(-1.0125, -79.4698), 5);
        mMap.moveCamera(camUpd1);
        LatLng punto = new LatLng(-1.0125, -79.4698);
        mMap.addMarker(new MarkerOptions().position(punto).title("Universidad Técnica Estatal de Quevedo"));
        //mostrarLineas();
        muestraRectangulo();
        mostrarLineasDefecto2();
        mostrarlineasMysql4();

        //Definimos el grosor de las Polilíneas

    }
}