package com.mycompany.ad03.ClasesTablasBD;

public class Producto {
    
    private final int idProducto;
    private final String nombre;
    private final String descripcion;
    private final float precio;

    public Producto(int idProducto, String nombre, String descripcion, float precio){
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }    

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getPrecio() {
        return precio;
    }
    
    @Override
    public String toString(){       
        return(String.format("%-30s %-40s %10.2f €",nombre,descripcion,precio));        
    }    
    
}
