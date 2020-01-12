package com.projectviiigps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class localizacion extends AppCompatActivity implements OnMapReadyCallback {

    private EditText edtnombre, edtlatitud, edtlongitud;
    private Button btncargar2;
    private GoogleMap mMap;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacion);

        edtnombre = (EditText) findViewById(R.id.edtnombrenino);
        edtlatitud = (EditText) findViewById(R.id.edtlatitud);
        edtlongitud = (EditText) findViewById(R.id.edtlongitud);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btncargar2 = (Button) findViewById(R.id.btncargar2);
        btncargar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarnino("http://appgpsmovil.000webhostapp.com/webservices/datos.php?nombrenino="
                        + edtnombre.getText() + "");

            }
        });
    }

    private void buscarnino(String URL) {
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        edtnombre.setText(jsonObject.getString("nombrenino"));
                        edtlatitud.setText(jsonObject.getString("latitud"));
                        edtlongitud.setText(jsonObject.getString("longitud"));
                        Double latitud = Double.parseDouble(edtlatitud.getText().toString());
                        Double longitud = Double.parseDouble(edtlongitud.getText().toString());
                        LatLng latLng = new LatLng(latitud,longitud);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.addMarker(new MarkerOptions().position(latLng).title("Punto. Lat: "+ latitud +", Lon:" + longitud));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(34));
                    } catch (JSONException e) {
                        Toast.makeText(localizacion.this, "", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(localizacion.this, "Error de conexion", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        enableMyLocationIfPermitted();
    }

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

    private void showDefaultLocation() {
        Toast.makeText(this, "Permiso de ubicación no concedido, " +
                        "mostrando la ubicación por defecto",
                Toast.LENGTH_SHORT).show();
        LatLng redmond = new LatLng(-1.0128, -79.4693);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                return;
            }
        }
    }
}


