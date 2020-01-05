package com.projectviiigps;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class RegistroRequest extends StringRequest {

    private static final  String ruta ="http://appgpsmovil.000webhostapp.com/webservices/registro.php";
    private Map<String, String> parametros;

    public RegistroRequest(String nombre, String apellido,int cedula,  int edad,String direccion, int telefono, String correo, String usuario, String clave, Response.Listener<String> listener){
        super(Request.Method.POST, ruta,listener,null);
        parametros =new HashMap<>();
        parametros.put("nombre",nombre+"");
        parametros.put("apellido",apellido+"");
        parametros.put("cedula",cedula+"");
        parametros.put("edad",edad+"");
        parametros.put("direccion",direccion+"");
        parametros.put("telefono",telefono+"");
        parametros.put("correo",correo+"");
        parametros.put("usuario",usuario+"");
        parametros.put("clave",clave+"");
    }

    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}


