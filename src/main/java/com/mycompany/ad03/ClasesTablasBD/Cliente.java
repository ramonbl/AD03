package com.mycompany.ad03.ClasesTablasBD;

public class Cliente {
    final private int idCliente;
    final private String nombre;
    final private String apellidos;
    final private String email;

    public Cliente(int idCliente, String nombre, String apellidos, String email){
        this.idCliente=idCliente;
        this.nombre=nombre;
        this.apellidos=apellidos;        
        this.email=email;        
    }  

    public int getIdCliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }   

    public String toString(){
        return(String.format("%-30s  %-40s  %-30s",nombre,apellidos,email));  
    }    
    
}
