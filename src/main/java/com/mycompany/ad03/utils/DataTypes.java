package com.mycompany.ad03.utils;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataTypes {
  
  
public static String getEmail(String mensaje) {
    Scanner entrada = new Scanner(System.in);
    Pattern pattern = Pattern
            .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    Matcher mather;
    String email;
    System.out.print(mensaje);
    while (true) {
      email = entrada.nextLine();
      mather = pattern.matcher(email);
      if (mather.find() == false) {
        System.out.println("Error, introduce el email:");
      } else {
        break;
      }
    }
    return email;
  }

  public static int getInt(String mensaje) {
    Scanner entrada = new Scanner(System.in);
    int res = 0;
    System.out.print(mensaje);
    while (entrada.hasNext()) {
      if (entrada.hasNextInt()) {
        res = entrada.nextInt();
        break;
      }
      System.out.println("Error, introduce un entero:");
      entrada.next();
    }
    return res;
  }
  
    public static String getString(String mensaje) {
    Scanner entrada = new Scanner(System.in);
    String res;
    System.out.print(mensaje);
    res = entrada.nextLine();
    return res;
  }


  public static float getFloat(String mensaje) {
    Scanner entrada = new Scanner(System.in);
    float res = 0;
    System.out.print(mensaje);
    while (entrada.hasNext()) {
      if (entrada.hasNextFloat()) {
        res = entrada.nextFloat();
        break;
      }
      System.out.println("Error, introduce un decimal:");
      entrada.next();
    }
    return res;
  }
}
