package com.mycompany.ad03.parser;

public class Noticia {

  private String titulo;

  public Noticia() {
    this.titulo = new String("");
  }

  public Noticia(String titulo) {
    this.titulo = titulo;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

}
