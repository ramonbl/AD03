package com.mycompany.ad03.utils;

import com.mycompany.ad03.ClasesTablasBD.Cliente;
import com.mycompany.ad03.Franquicia;
import com.mycompany.ad03.ClasesTablasBD.Pais;
import com.mycompany.ad03.ClasesTablasBD.Producto;
import com.mycompany.ad03.ClasesTablasBD.Provincia;
import com.mycompany.ad03.ClasesTablasBD.Stock;
import com.mycompany.ad03.ClasesTablasBD.Tienda;
import com.mycompany.ad03.AccesoBD.BD;
import java.sql.Connection;
import java.util.Scanner;

public class Menu {

  public static void getMenu() {
    System.out.println(
            "-==========================================================" + "\n"
            + "MENU" + "\n"
            + " 1. Añadir tienda" + "\n"
            + " 2. Mostrar tiendas" + "\n"
            + " 3. Elimina tienda" + "\n"
            + " 4. Añadir producto" + "\n"
            + " 5. Mostrar los productos de la franquicia" + "\n"
            + " 6. Mostrar productos de tienda" + "\n"
            + " 7. Añadir producto en tienda" + "\n"
            + " 8. Actualizar stock de producto en tienda" + "\n"
            + " 9. Mostrar stock producto en tienda" + "\n"
            + "10. Elimina producto en tienda" + "\n"
            + "11. Elimina producto en franquicia" + "\n"
            + "12. Añadir clientes a franqucia" + "\n"
            + "13. Mostrar clientes de franquicia" + "\n"
            + "14. Elimina un cliente" + "\n"
            + "15. Leer los titulares del periódico El País" + "\n"
            + "16. Salir del programa"
            + "-------------------------------------------------------" + "\n");
  }

  public static int getMenuOpcion() { //------
    int opc;

    Scanner in = new Scanner(System.in);

    try {
      System.out.print("Seleccione una opción: ");
      opc = in.nextInt();
    } catch (Exception e) {
      opc = -1;
    }
    System.out.println("=====================================================");
    return opc;
  }

  public static Tienda getFormTiendaCreate(Connection con) { //-----
    String nombre, ciudad;
    int idProvincia = 0;

    System.out.println("\nCREAR TIENDA" + "\n"
            + "--------------------------------------------------------------");
    nombre = DataTypes.getString("Nombre: ");
    ciudad = DataTypes.getString("Ciudad: ");
    idProvincia = getProvincia(con, "Provincia: \n");

    Provincia provincia = BD.dbObjProvinciaCreate(con, idProvincia);
    Tienda tienda = new Tienda(0, nombre, ciudad, provincia);
    return tienda;
  }

  public static int getFormTiendaDelete(Franquicia franquicia) { //----
    int idTienda;

    // Se piden los datos por pantalla
    System.out.println("\nELIMINAR TIENDA" + "\n"
            + "--------------------------------------------------------------");

    if (franquicia.getTotalTiendas() == 0) {
      System.out.println("No hay tienda para eliminar");
      return 0;
    }

    idTienda = getTienda(franquicia, "Tienda: \n");

    return idTienda;
  }

  public static Producto getFormProductoCreate() { //----------
    String nombre, descripcion;
    float precio;

    System.out.println("\nCREAR PRODUCTO" + "\n"
            + "--------------------------------------------------------------");
    nombre = DataTypes.getString("Nombre: ");
    descripcion = DataTypes.getString("Descripción: ");
    precio = DataTypes.getFloat("Precio: ");

    Producto producto = new Producto(0, nombre, descripcion, precio);
    return producto;
  }

  public static int getFormProductosTiendaShow(Franquicia franquicia) { //-----
    int idTienda;

    System.out.println("\nMOSTRAR PRODUCTOS DE TIENDA" + "\n"
            + "--------------------------------------------------------------");
    idTienda = getTienda(franquicia, "Tienda: \n");

    if (franquicia.getTotalProductos(idTienda) == 0) {
      System.out.println("Sin existencias");
      return 0;
    }

    return idTienda;
  }

  public static Stock getFormInsertarStock(Franquicia franquicia) { //-----
    int cantidad, idTienda, idProducto;

    System.out.println("AÑADIR PRODUCTO A TIENDA" + "\n"
            + "--------------------------------------------------------------");
    idTienda = getTienda(franquicia, "Tienda: \n");
    idProducto = getProducto(franquicia, "Producto: \n");

    if (franquicia.existeStock(idProducto, idTienda)) {
      System.out.println("Producto ya existe");
      return null;
    }

    cantidad = DataTypes.getInt("Cantidad: ");

    Stock stock = new Stock(0, cantidad, idTienda, idProducto);
    return stock;
  }

  public static Stock getFormStockUpdate(Franquicia franquicia) { //---------
    int idTienda, idProducto, cantidad;

    System.out.println("ACTUALIZAR STOCK DE TIENDA" + "\n"
            + "--------------------------------------------------------------");
    idTienda = getTienda(franquicia, "Tienda: \n");

    if (franquicia.getTotalProductos(idTienda) == 0) {
      System.out.println("Sin productos");
      return null;
    }

    idProducto = getProductoTienda(franquicia, idTienda, "Producto: \n");
    cantidad = DataTypes.getInt("Cantidad: ");

    Stock stock = new Stock(0, cantidad, idTienda, idProducto);
    return stock;
  }

  public static Stock getFormProductoTiendaDelete(Franquicia franquicia) { //----
    int idTienda, idProducto;

    System.out.println("ELIMINAR STOCK DE TIENDA" + "\n"
            + "--------------------------------------------------------------");

    if (franquicia.getTotalTiendas() == 0) {
      System.out.println("No hay tiendas.");
      return null;
    }

    idTienda = getTienda(franquicia, "Tienda: \n");

    if (franquicia.getTotalProductos(idTienda) == 0) {
      System.out.println("No hay productos en la tienda");
      return null;
    }

    idProducto = getProductoTienda(franquicia, idTienda, "Producto: \n");

    Stock stock = new Stock(0, 0, idTienda, idProducto);
    return stock;
  }

  
    public static int getFormProductoDelete(Franquicia franquicia) { //------
    int idProducto;

    System.out.println("ELIMINAR PRODUCTO" + "\n"
            + "--------------------------------------------------------------");

    if (franquicia.getTotalProductos() == 0) {
      System.out.println("Sin productos");
      return 0;
    }

    idProducto = getProducto(franquicia, "Producto: \n");

    return idProducto;
  }
    
   public static Cliente getFormClienteCreate() { //----------
    String nombre, apellidos, email;

    System.out.println("\nCREAR CLIENTE" + "\n"
            + "--------------------------------------------------------------");
    nombre = DataTypes.getString("Nombre: ");
    apellidos = DataTypes.getString("Apellidos: ");
    email = DataTypes.getEmail("Email: ");

    Cliente cliente = new Cliente(0, nombre, apellidos, email);
    return cliente;
  }


     public static int getFormClienteDelete(Franquicia franquicia) { //---
    int idCliente;

    System.out.println("ELIMINAR CLIENTE" + "\n"
            + "--------------------------------------------------------------");

    if (franquicia.getTotalClientes() == 0) {
      System.out.println("Sin clientes");
      return 0;
    }

    idCliente = getCliente(franquicia, "Cliente: \n");

    return idCliente;
  }
   
     public static int getFormTitularesShow() { //--------------
    int opc;

    System.out.println("RSS" + "\n"
            + " 1. Leer desde archivo" + "\n"
            + " 2. Leer desde la web" + "\n"
            + "--------------------------------------------------------------");
    do {
      opc = DataTypes.getInt("Selecciona una opcion: ");
      if (opc != 1 && opc != 2) {
        System.out.print("Error. ");
      }
    } while (opc != 1 && opc != 2);

    return opc;
  } 
    
    
  

  public static int getProvincia(Connection con, String mensaje) {
    int idProvincia;
    System.out.print(mensaje);
    Pais pais = new Pais();
    do {
      pais.ProvinciasString(con);
      idProvincia = Menu.getMenuOpcion();
      if (!pais.provinciaExists(con, idProvincia)) {
        System.out.println("Selecciona una provincia correcta");
      } else {
        break;
      }
    } while (!pais.provinciaExists(con, idProvincia));
    return idProvincia;
  }

  public static int getTienda(Franquicia franquicia, String mensaje) {
    int idTienda;
    System.out.print(mensaje);
    do {
      franquicia.getTiendas();
      idTienda = Menu.getMenuOpcion();
      if (!franquicia.existeTienda(idTienda)) {
        System.out.println("No existe");
      } else {
        break;
      }
    } while (!franquicia.existeTienda(idTienda));
    return idTienda;
  }

  public static int getCliente(Franquicia franquicia, String mensaje) {
    int idCliente;
    System.out.print(mensaje);
    do {
      franquicia.getClientes();
      idCliente = Menu.getMenuOpcion();
      if (!franquicia.existeCliente(idCliente)) {
        System.out.println("Opción incorrecta !!!");
      } else {
        break;
      }
    } while (!franquicia.existeTienda(idCliente));
    return idCliente;
  }

  public static int getEmpleado(Franquicia franquicia, String mensaje) {
    int idEmpleado;
    System.out.print(mensaje);
    do {
      franquicia.getEmpleados();
      idEmpleado = Menu.getMenuOpcion();
      if (!franquicia.existeEmpleado(idEmpleado)) {
        System.out.println("Opción incorrecta !!!");
      } else {
        break;
      }
    } while (!franquicia.existeEmpleado(idEmpleado));
    return idEmpleado;
  }

  public static int getProducto(Franquicia franquicia, String mensaje) {
    int idProducto;
    System.out.print(mensaje);
    do {
      franquicia.getProductos();
      idProducto = Menu.getMenuOpcion();
      if (!franquicia.existeProducto(idProducto)) {
        System.out.println("No existe");
      } else {
        break;
      }
    } while (!franquicia.existeProducto(idProducto));
    return idProducto;
  }

  public static int getProductoTienda(Franquicia franquicia, int idTienda, String mensaje) {
    int idProducto;
    System.out.print(mensaje);
    do {
      franquicia.getProductos(idTienda);
      idProducto = Menu.getMenuOpcion();
      if (!franquicia.existeProducto(idProducto)) {
        System.out.println("no existe");
      } else {
        break;
      }
    } while (!franquicia.existeProducto(idProducto));
    return idProducto;
  }

}
