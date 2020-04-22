package com.mycompany.ad03.parser;

import java.io.IOException;
import java.util.ArrayList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class Titulares {

  public void mostrarTitulares(int opcion) {

    //Procesar XML
    XMLReader procesadorXML = null;
    try {

      System.out.println("------------------------------------------------------------------------------");
      //Creamos un parseador de texto y le añadimos la clase que va a parsear el texto
      procesadorXML = XMLReaderFactory.createXMLReader();
      ParserXML persoasXML = new ParserXML();
      procesadorXML.setContentHandler(persoasXML);

      //Indicamos el archivo XML                        
      InputSource archivoXML;
      if (opcion == 1) {
        archivoXML = new InputSource("portada.xml");
      } else {
        archivoXML = new InputSource("http://ep00.epimg.net/rss/elpais/portada.xml");
      }
      procesadorXML.parse(archivoXML);

      //Imprimimos los datos leidos del XML
      ArrayList<Noticia> noticias = persoasXML.getAlNoticias();
      for (int i = 0; i < noticias.size(); i++) {
        Noticia noticiaAux = noticias.get(i);
        System.out.println("Titulo " + (i + 1) + ": " + noticiaAux.getTitulo());
      }

    } catch (SAXException | IOException e) {
      System.out.println("Error al leer el archivo XML !!!");
    }
  }
}
