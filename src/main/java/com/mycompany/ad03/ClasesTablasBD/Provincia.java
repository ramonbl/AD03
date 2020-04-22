package com.mycompany.ad03.ClasesTablasBD;

public class Provincia{

    private final int id;
    private final String nome;

    public Provincia(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }    

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

}    
