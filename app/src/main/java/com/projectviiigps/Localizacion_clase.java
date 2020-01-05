package com.projectviiigps;

public class Localizacion_clase {

    private int id;
    private String nombrenino;
    private String latitud;
    private String longitud;
    private String fecha;
    private String hora;

    public Localizacion_clase() {
        this.id = id;
        this.nombrenino = nombrenino;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getId(int idlocalizacion) {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombrenino() {
        return nombrenino;
    }

    public void setNombrenino(String nombrenino) {
        this.nombrenino = nombrenino;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Identificador:"+ id + '\n'+
                "Nombres:" + nombrenino + '\n'+
                "Latitud:"+ latitud + '\n'+
                "Longitud: " + longitud + '\n'+
                "Fecha: " + fecha + '\n'+
                "Hora: "+ hora;
    }
}
