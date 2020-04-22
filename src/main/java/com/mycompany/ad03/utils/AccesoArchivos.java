package com.mycompany.ad03.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AccesoArchivos {

  public String fileReader(String nombreFichero) {
    String entrada = "";
    File archivo = null;
    FileReader fr = null;
    BufferedReader br = null;

    try {
      archivo = new File(nombreFichero);
      fr = new FileReader(archivo);
      br = new BufferedReader(fr);

      String linea;
      while ((linea = br.readLine()) != null) {
        entrada += linea;
      }
    } catch (IOException e) {
    } finally {
      try {
        if (null != fr) {
          fr.close();
        }
      } catch (IOException e2) {
      }
    }
    return entrada;
  }

}
