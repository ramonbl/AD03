package com.mycompany.ad03.ClasesTablasBD;

public class Tienda {
    
    private final int idTienda;
    private final String nombre;
    private final String ciudad;
    private final Provincia provincia;

    public Tienda(int idTienda, String nombre, String ciudad, Provincia provincia){
        this.idTienda=idTienda;
        this.nombre=nombre;
        this.ciudad=ciudad;   
        this.provincia=provincia;     
    }

    public int getIdTienda() {
        return idTienda;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    @Override
    public String toString(){       
        return(String.format("%-30s %-30s %-30s",nombre,ciudad,provincia.getNome()));        
    }
  
}
