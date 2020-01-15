package com.projectviiigps.Others;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static final String STRING_PREFERENCES = "appgps.Sesion";
    public static final String PREFERENCE_USUARIO_LOGIN_NOMBRE = "usuario.name";
    public static final String PREFERENCE_USUARIO_LOGIN_APELLIDO = "usuario.apellido";
    public static final String PREFERENCE_USUARIO_LOGIN_ID = "usuario.id";
    public static final String PREFERENCE_USUARIO_LOGIN_CONTRASENIA = "usuario.contrasenia";
    public static final String PREFERENCE_USUARIO_LOGIN_TELEFONO = "usuario.telefono";
    public static final String PREFERENCE_USUARIO_LOGIN_DIRECCION = "usuario.direccion";
    public static final String PREFERENCE_USUARIO_LOGIN_CORREO = "usuario.correo";


    public static void savePreferenceStringNombre(Context c, String b, String key){
        SharedPreferences preferences = c.getSharedPreferences(PREFERENCE_USUARIO_LOGIN_NOMBRE,c.MODE_PRIVATE);
        preferences.edit().putString(key,b).apply();
    }
    public static String obtenerPreferenceStringNombre(Context c,String key){
        SharedPreferences preferences = c.getSharedPreferences(PREFERENCE_USUARIO_LOGIN_NOMBRE,c.MODE_PRIVATE);
        return preferences.getString(key,"");//Si es que nunca se ha guardado nada en esta key pues retornara una cadena vacia
    }

    public static void savePreferenceStringApellido(Context c, String b, String key){
        SharedPreferences preferences = c.getSharedPreferences(PREFERENCE_USUARIO_LOGIN_APELLIDO,c.MODE_PRIVATE);
        preferences.edit().putString(key,b).apply();
    }
    public static String obtenerPreferenceStringApellido(Context c,String key){
        SharedPreferences preferences = c.getSharedPreferences(PREFERENCE_USUARIO_LOGIN_APELLIDO,c.MODE_PRIVATE);
        return preferences.getString(key,"");//Si es que nunca se ha guardado nada en esta key pues retornara una cadena vacia
    }

    public static void savePreferenceStringContrasenia(Context c, String b, String key){
        SharedPreferences preferences = c.getSharedPreferences(PREFERENCE_USUARIO_LOGIN_CONTRASENIA,c.MODE_PRIVATE);
        preferences.edit().putString(key,b).apply();
    }
    public static String obtenerPreferenceStringContrasenia(Context c,String key){
        SharedPreferences preferences = c.getSharedPreferences(PREFERENCE_USUARIO_LOGIN_CONTRASENIA,c.MODE_PRIVATE);
        return preferences.getString(key,"");//Si es que nunca se ha guardado nada en esta key pues retornara una cadena vacia
    }

    public static void savePreferenceStringId(Context c, String b, String key){
        SharedPreferences preferences = c.getSharedPreferences(PREFERENCE_USUARIO_LOGIN_ID,c.MODE_PRIVATE);
        preferences.edit().putString(key,b).apply();
    }

    public static String obtenerPreferenceStringId(Context c,String key){
        SharedPreferences preferences = c.getSharedPreferences(PREFERENCE_USUARIO_LOGIN_ID,c.MODE_PRIVATE);
        return preferences.getString(key,"");//Si es que nunca se ha guardado nada en esta key pues retornara una cadena vacia
    }
    public static void savePreferenceStringTelefono(Context c, String b, String key){
        SharedPreferences preferences = c.getSharedPreferences(PREFERENCE_USUARIO_LOGIN_TELEFONO,c.MODE_PRIVATE);
        preferences.edit().putString(key,b).apply();
    }
    public static String obtenerPreferenceStringTelefono(Context c,String key){
        SharedPreferences preferences = c.getSharedPreferences(PREFERENCE_USUARIO_LOGIN_TELEFONO,c.MODE_PRIVATE);
        return preferences.getString(key,"");//Si es que nunca se ha guardado nada en esta key pues retornara una cadena vacia
    }
    public static void savePreferenceStringCorreo(Context c, String b, String key){
        SharedPreferences preferences = c.getSharedPreferences(PREFERENCE_USUARIO_LOGIN_CORREO,c.MODE_PRIVATE);
        preferences.edit().putString(key,b).apply();
    }
    public static String obtenerPreferenceStringCorreo(Context c,String key){
        SharedPreferences preferences = c.getSharedPreferences(PREFERENCE_USUARIO_LOGIN_CORREO,c.MODE_PRIVATE);
        return preferences.getString(key,"");//Si es que nunca se ha guardado nada en esta key pues retornara una cadena vacia
    }
    public static void savePreferenceStringDireccion(Context c, String b, String key){
        SharedPreferences preferences = c.getSharedPreferences(PREFERENCE_USUARIO_LOGIN_DIRECCION,c.MODE_PRIVATE);
        preferences.edit().putString(key,b).apply();
    }
    public static String obtenerPreferenceStringDireccion(Context c,String key){
        SharedPreferences preferences = c.getSharedPreferences(PREFERENCE_USUARIO_LOGIN_DIRECCION,c.MODE_PRIVATE);
        return preferences.getString(key,"");//Si es que nunca se ha guardado nada en esta key pues retornara una cadena vacia
    }


}
