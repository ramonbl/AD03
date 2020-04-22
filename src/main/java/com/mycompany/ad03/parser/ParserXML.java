package com.mycompany.ad03.parser;

import org.xml.sax.Attributes;              // SAX
import org.xml.sax.SAXException;            // SAX
import org.xml.sax.helpers.DefaultHandler;  // SAX
import java.util.ArrayList;                 

public class ParserXML extends DefaultHandler {

  private ArrayList<Noticia> alNoticias;
  private Noticia noticiaAux;
  private String cadena;

  public ParserXML() {
    super();
  }

  @Override
  public void startDocument() throws SAXException {
  } //antes parsear

  @Override
  public void endDocument() throws SAXException {
  } //tras parsear

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

    if (localName == "channel") {
      this.alNoticias = new ArrayList<Noticia>();
    } else if (localName == "item") {
      this.noticiaAux = new Noticia();
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {

    if (localName == "title") {
      if (this.noticiaAux != null) {
        this.noticiaAux.setTitulo(cadena);
      }
    } else if (localName == "item") {
      this.alNoticias.add(this.noticiaAux);
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    this.cadena = new String(ch, start, length);
  }

  public ArrayList<Noticia> getAlNoticias() {
    return alNoticias;
  }

}
