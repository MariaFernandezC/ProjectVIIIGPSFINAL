package com.projectviiigps;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistroHijoRequest extends StringRequest {

    private static final  String ruta ="http://appgpsmovil.000webhostapp.com/webservices/registrohijo.php";
    private Map<String, String> parametros;

    public RegistroHijoRequest(int idpadre,String nombreh, String apellidoh,
                               int edadh,String direccionh, String tiposangre, String enfermedad,
                               String alergia, Response.Listener<String> listener) {
        super(Request.Method.POST, ruta,listener,null);
        parametros =new HashMap<>();
        parametros.put("idpadre",idpadre+"");
        parametros.put("nombreh",nombreh+"");
        parametros.put("apellidoh",apellidoh+"");
        parametros.put("edadh",edadh+"");
        parametros.put("direccionh",direccionh+"");
        parametros.put("tiposangre",tiposangre+"");
        parametros.put("enfermedad",enfermedad+"");
        parametros.put("alergia",alergia+"");
    }
    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}
