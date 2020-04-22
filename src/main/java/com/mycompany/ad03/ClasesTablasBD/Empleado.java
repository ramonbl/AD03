package com.mycompany.ad03.ClasesTablasBD;

public class Empleado {
    private final int idEmpleado;
    private final String nombre;
    private final String apellidos;

    public Empleado(int idEmpleado, String nombre, String apellidos) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    @Override
    public String toString(){
        return(String.format("%-30s  %-40s",nombre,apellidos));          
    }       
    
}