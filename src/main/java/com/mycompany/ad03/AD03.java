package com.mycompany.ad03;

import com.mycompany.ad03.ClasesTablasBD.Pais;
import com.mycompany.ad03.utils.Menu;
import com.mycompany.ad03.AccesoBD.BD;
import com.mycompany.ad03.utils.AccesoArchivos;
import com.google.gson.Gson;
import com.mycompany.ad03.ClasesTablasBD.Cliente;
import com.mycompany.ad03.ClasesTablasBD.Producto;
import com.mycompany.ad03.ClasesTablasBD.Stock;
import com.mycompany.ad03.ClasesTablasBD.Tienda;
import com.mycompany.ad03.parser.Titulares;
import java.sql.Connection;

public class AD03 {

  public static void main(String[] args) {

    final String NOME_BD = "novaBaseDeDatos.db";
    BD.dbCreate(NOME_BD);
    Connection con = BD.dbConnect(NOME_BD);
    BD.dbTablesCreate(con);

    //Cargar Provincias
    Gson gson = new Gson();

    AccesoArchivos fa = new AccesoArchivos();
    Pais pais = gson.fromJson(fa.fileReader("provincias.json"), Pais.class);

    BD.dbTableProvinciasInsert(con, pais);

    Franquicia franquicia = new Franquicia(con);

    int opcion;
    do {
      Menu.getMenu();
      opcion = Menu.getMenuOpcion();
      switch (opcion) {

        case 1: //AÑADIR  TIENDA
          Tienda tienda = Menu.getFormTiendaCreate(franquicia.con);
          franquicia.addTienda(tienda);
          break;

        case 2: //MOSTRAR TIENDAS 
          franquicia.getTiendas();
          break;

        case 3: //ELIMINAR TIENDA 
          int idTienda = Menu.getFormTiendaDelete(franquicia);
          if (idTienda != 0) {
            franquicia.delTienda(idTienda);
          }
          break;

        case 4: //AÑADIR  PRODUCTO
          Producto producto = Menu.getFormProductoCreate();
          franquicia.addProducto(producto);
          break;

        case 5: //MOSTRAR PRODUCTOS (FRANQUICIA)
          franquicia.getProductos();
          break;

        case 6: //MOSTRAR PRODUCTOS DE TIENDA
          idTienda = Menu.getFormProductosTiendaShow(franquicia);
          if (idTienda != 0) {
            franquicia.getProductos(idTienda);
          }
          break;

        case 7: //AÑADIR PRODUCTO EN TIENDA (STOCK)
          Stock stock = Menu.getFormInsertarStock(franquicia);
          if (stock != null) {
            franquicia.addStock(stock);
          }
          break;

        case 8: //ACTUALIZAR STOCK PRODUCTO EN TIENDA
          stock = Menu.getFormStockUpdate(franquicia);
          if (stock != null) {
            franquicia.updateStock(stock);
          }
          break;

        case 9: //MOSTRAR STOCK PRODUCTO EN UNA TIENDA
          stock = Menu.getFormProductoTiendaDelete(franquicia);
          if (stock != null) {
            franquicia.getStock(stock);
          }
          break;

        case 10: //ELIMINAR PRODUCTO EN TIENDA
          stock = Menu.getFormProductoTiendaDelete(franquicia);
          if (stock != null) {
            franquicia.delProductoTienda(stock.getIdProducto(), stock.getIdTienda());
          }
          break;

        case 11: //ELIMINAR PRODUCTOS FRANQUICIA
          int idProducto = Menu.getFormProductoDelete(franquicia);
          if (idProducto != 0) {
            franquicia.delProducto(idProducto);
          }
          break;

        case 12: //AÑADIR CLIENTE
          Cliente cliente = Menu.getFormClienteCreate();
          franquicia.addCliente(cliente);
          break;

        case 13: //MOSTRAR CLIENTES FRANQUICIA
          franquicia.getClientes();
          break;

        case 14: //ELIMINAR CLIENTE FRANQUICIA
          int idCliente = Menu.getFormClienteDelete(franquicia);
          if (idCliente != 0) {
            franquicia.delCliente(idCliente);
          }
          break;

        case 15: //LEER TITULARES DE EL PAÍS
          opcion = Menu.getFormTitularesShow();
          new Titulares().mostrarTitulares(opcion);
          break;

        case 16: //SALIR PROGRAMA
          BD.dbDisconnect(franquicia.con);

          break;

        default:
          System.out.println("Escribe una opción correcta");
      }
    } while (opcion != 16);

  }

}
