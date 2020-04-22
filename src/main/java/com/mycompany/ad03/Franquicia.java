package com.mycompany.ad03;

import com.mycompany.ad03.ClasesTablasBD.Cliente;
import com.mycompany.ad03.ClasesTablasBD.Empleado;
import com.mycompany.ad03.ClasesTablasBD.Producto;
import com.mycompany.ad03.ClasesTablasBD.Tienda;
import com.mycompany.ad03.ClasesTablasBD.Stock;
import com.mycompany.ad03.AccesoBD.BD;
import java.sql.Connection;
import java.util.List;

public class Franquicia {

  Connection con;

  public Franquicia(Connection con) {
    this.con = con;
  }

  public void addTienda(Tienda tienda) {           //----------
    BD.dbTableTiendasInsert(con, tienda);
  }

  public void getTiendas() {   //--------------------
    System.out.println("LISTADO DE TIENDAS");
    System.out.println("------------------------------------------------------------------------------");
    BD.dbListTiendasCreate(con).forEach((t) -> {
      System.out.printf("%-5s " + t + "\n", t.getIdTienda());
    });
  }

  public void delTienda(int idTienda) {   //--------------
    BD.dbRegisterTiendaDelete(con, idTienda);
  }

  public void addProducto(Producto producto) { //------------
    BD.dbTableProductosInsert(con, producto);
  }

  public void getProductos() {  //-------------------
    System.out.println("LISTADO DE PRODUCTOS");
    System.out.println("------------------------------------------------------------------------------");
    BD.dbListProductosCreate(con).forEach((p) -> {
      System.out.printf("%-5s " + p + "\n", p.getIdProducto());
    });
  }

  public void getProductos(int idTienda) {   //----------------
    System.out.println("LISTADO DE PRODUCTOS");
    System.out.println("------------------------------------------------------------------------------");
    BD.dbListProductosTiendaCreate(con, idTienda).forEach((p) -> {
      System.out.printf("%-5s " + p + "\n", p.getIdProducto());
    });
  }

  public void addStock(Stock stock) { //----------------
    BD.dbTableStockInsert(con, stock);
  }

  public void updateStock(Stock stock) { //--------------
    BD.dbTableStockUpdate(con, stock);
  }

  public void getStock(Stock stock) {   //---------------------
    System.out.println("STOCK");
    System.out.println("------------------------------------------------------------------------------");
    BD.dbStockProductoTiendaShow(con, stock);
  }

  public void delProductoTienda(int idProducto, int idTienda) { //-------
    BD.dbRegisterProductoTiendaDelete(con, idProducto, idTienda);
  }

  public void delProducto(int idProducto) { //-------
    BD.dbRegisterProductoDelete(con, idProducto);
  }

  public void addCliente(Cliente cliente) { //---------
    BD.dbTableClientesInsert(con, cliente);
  }

  public void getClientes() {   //------------
    System.out.println("------------------------------------------------------------------------------");
    System.out.println("LISTADO DE CLIENTES");
    System.out.println("------------------------------------------------------------------------------");
    BD.dbListClientesCreate(con).forEach((c) -> {
      System.out.printf("%-5s " + c + "\n", c.getIdCliente());
    });
  }

  public void delCliente(int idCliente) { //----------
    BD.dbRegisterClienteDelete(con, idCliente);
  }

  public boolean existeStock(int idProducto, int idTienda) {
    return BD.dbStockProductoTiendaExists(con, idProducto, idTienda);
  }

  public boolean existeTienda(int idTienda) {
    return BD.dbTiendaExists(con, idTienda);
  }

  public boolean existeProducto(int idProducto) {
    return BD.dbProductoExits(con, idProducto);
  }

  public boolean existeCliente(int idCliente) {
    return BD.dbClienteExists(con, idCliente);
  }

  public boolean existeEmpleado(int idEmpleado) {
    return BD.dbEmpleadoExists(con, idEmpleado);
  }

  public boolean existeEmpleadoTienda(int idTienda, int idEmpleado) {
    return BD.dbEmpleadoTiendaExits(con, idTienda, idEmpleado);
  }

  public int getTotalProductos(int idTienda) {
    List<Producto> productos = BD.dbListProductosTiendaCreate(con, idTienda);
    return productos.size();
  }

  public int getTotalEmpleados(int idTienda) {
    List<Empleado> empleados = BD.dbListEmpleadosTiendaCreate(con, idTienda);
    return empleados.size();
  }

  public int getTotalTiendas() {
    List<Tienda> tiendas = BD.dbListTiendasCreate(con);
    return tiendas.size();
  }

  public int getTotalProductos() {
    List<Producto> productos = BD.dbListProductosCreate(con);
    return productos.size();
  }

  public int getTotalEmpleados() {
    List<Empleado> empleados = BD.dbListEmpleadosCreate(con);
    return empleados.size();
  }

  public int getTotalClientes() {
    List<Cliente> clientes = BD.dbListClientesCreate(con);
    return clientes.size();
  }

  public void getEmpleados() {
    System.out.println("------------------------------------------------------------------------------");
    System.out.println("LISTADO DE EMPLEADOS");
    System.out.println("------------------------------------------------------------------------------");
    BD.dbListEmpleadosCreate(con).forEach((e) -> {
      System.out.printf("%-5s " + e + "\n", e.getIdEmpleado());
    });
  }

}
