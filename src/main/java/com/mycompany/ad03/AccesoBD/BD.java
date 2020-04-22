package com.mycompany.ad03.AccesoBD;

import com.mycompany.ad03.ClasesTablasBD.Cliente;
import com.mycompany.ad03.ClasesTablasBD.Empleado;
import com.mycompany.ad03.ClasesTablasBD.Pais;
import com.mycompany.ad03.ClasesTablasBD.Producto;
import com.mycompany.ad03.ClasesTablasBD.Provincia;
import com.mycompany.ad03.ClasesTablasBD.Stock;
import com.mycompany.ad03.ClasesTablasBD.Tienda;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BD {

  public static void dbCreate(String filename) {
    String databaseFile = "jdbc:sqlite:" + filename;
    try {
      Connection connection = DriverManager.getConnection(databaseFile);
      if (connection != null) {
        DatabaseMetaData databasemetadata = connection.getMetaData();
        System.out.println("DB_Create OK");
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public static Connection dbConnect(String filename) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
      System.out.println("DB_Connect OK");
      return connection;
    } catch (SQLException e) {
      System.out.println("DB_Connect FAIL");
      System.err.println(e.getMessage());
      return null;
    }
  }

  public static void dbTablesCreate(Connection con) {
    try {
      Statement stmt;
      String sql;
      sql = "CREATE TABLE IF NOT EXISTS provincias (\n"
              + "    idProvincia integer PRIMARY KEY,\n"
              + "    nombre text NOT NULL\n"
              + ");";
      stmt = con.createStatement();
      stmt.execute(sql);
      sql = "CREATE TABLE IF NOT EXISTS tiendas (\n"
              + "    idTienda integer PRIMARY KEY,\n"
              + "    nombre text NOT NULL,\n"
              + "    ciudad text NOT NULL,\n"
              + "    idProvincia integer NOT NULL\n"
              + ");";
      stmt = con.createStatement();
      stmt.execute(sql);
      sql = "CREATE TABLE IF NOT EXISTS productos (\n"
              + "    idProducto integer PRIMARY KEY,\n"
              + "    nombre text NOT NULL,\n"
              + "    descripcion text NOT NULL,\n"
              + "    precio real NOT NULL"
              + ");";
      stmt = con.createStatement();
      stmt.execute(sql);
      sql = "CREATE TABLE IF NOT EXISTS stock (\n"
              + "    idStock integer PRIMARY KEY,\n"
              + "    cantidad integer NOT NULL,\n"
              + "    idTienda integer NOT NULL,\n"
              + "    idProducto integer NOT NULL"
              + ");";
      stmt = con.createStatement();
      stmt.execute(sql);
      sql = "CREATE TABLE IF NOT EXISTS clientes (\n"
              + "    idCliente integer PRIMARY KEY,\n"
              + "    nombre text NOT NULL,\n"
              + "    apellidos text NOT NULL,\n"
              + "    email text NOT NULL"
              + ");";
      stmt = con.createStatement();
      stmt.execute(sql);
      sql = "CREATE TABLE IF NOT EXISTS empleados (\n"
              + "    idEmpleado integer PRIMARY KEY,\n"
              + "    nombre text NOT NULL,\n"
              + "    apellidos text NOT NULL"
              + ");";
      stmt = con.createStatement();
      stmt.execute(sql);
      sql = "CREATE TABLE IF NOT EXISTS horario (\n"
              + "    idHorario integer PRIMARY KEY,\n"
              + "    idEmpleado integer NOT NULL,\n"
              + "    idTienda integer NOT NULL,\n"
              + "    horasSemanales integer NOT NULL"
              + ");";
      stmt = con.createStatement();
      stmt.execute(sql);
      System.out.println("DB_Tables_Create OK");
    } catch (SQLException e) {
      System.out.println("DB_Tables_Create FAIL");
      System.out.println(e.getMessage());
    }
  }

  public static void dbDisconnect(Connection connection) {
    try {
      if (connection != null) {
        connection.close();
        System.out.println("DB_Disconnect OK");
      }
    } catch (SQLException e) {
      System.out.println("DB_Disconnect FAIL");
      System.out.println(e.getMessage());
    }
  }

  public static void dbTableProvinciasInsert(Connection con, Pais pais) {
    String sql;
    for (Provincia p : pais.getProvincias()) {
      try {
        sql = "INSERT OR REPLACE INTO provincias(idProvincia,nombre) VALUES(?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, p.getId());
        pstmt.setString(2, p.getNome());
        pstmt.executeUpdate();
        System.out.println("DB_Provincia_Insert OK");
      } catch (SQLException e) {
        System.out.println("DB_Provincia_Insert FAIL");
        System.out.println(e.getMessage());
      }
    }
  }

  public static void dbTableTiendasInsert(Connection con, Tienda tienda) {
    String sql;
    try {
      sql = "INSERT INTO tiendas(nombre,ciudad,idProvincia) VALUES(?,?,?)";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setString(1, tienda.getNombre());
      pstmt.setString(2, tienda.getCiudad());
      pstmt.setInt(3, tienda.getProvincia().getId());
      pstmt.executeUpdate();
      System.out.println("DB_Tienda_Insert OK");
    } catch (SQLException e) {
      System.out.println("DB_Tienda_Insert FAIL");
      System.out.println(e.getMessage());
    }
  }

  public static List<Tienda> dbListTiendasCreate(Connection con) {
    List<Tienda> tiendas = new ArrayList();
    Provincia provincia;
    Tienda tienda;
    try {
      Statement statement = con.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM tiendas");
      while (rs.next()) {
        provincia = BD.dbObjProvinciaCreate(con, rs.getInt("idProvincia"));
        tienda = new Tienda(rs.getInt("idTienda"), rs.getString("nombre"), rs.getString("ciudad"), provincia);
        tiendas.add(tienda);
      }
      System.out.println("DB_List_Tiendas_Create OK");
    } catch (SQLException e) {
      System.out.println("DB_List_Tiendas_Create FAIL");
      System.err.println(e.getMessage());
    }
    return tiendas;
  }

  public static void dbRegisterTiendaDelete(Connection con, int idTienda) {
    try {
      String sql = "DELETE FROM tiendas WHERE idTienda = ?";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, idTienda);
      pstmt.executeUpdate();
      System.out.println("DB_Tienda_Delete");

      sql = "DELETE FROM stock WHERE idTienda = ?";
      pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, idTienda);
      pstmt.executeUpdate();
      System.out.println("DB_Stock_Tienda_Delete");

      sql = "DELETE FROM horario WHERE idTienda = ?";
      pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, idTienda);
      pstmt.executeUpdate();
      System.out.println("Db_Horario_Tienda_Delete");

    } catch (SQLException e) {
      System.out.println("DB_Register_Tienda_Delete FAIL ");
      System.err.println(e.getMessage());
    }
  }

  public static void dbTableProductosInsert(Connection con, Producto producto) {
    String sql;
    try {
      sql = "INSERT INTO productos(nombre,descripcion,precio) VALUES(?,?,?)";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setString(1, producto.getNombre());
      pstmt.setString(2, producto.getDescripcion());
      pstmt.setFloat(3, producto.getPrecio());
      pstmt.executeUpdate();
      System.out.println("DB_Table_Productos_Insert OK");
    } catch (SQLException e) {
      System.out.println("DB_Table_Productos_Insert FAIL");
      System.out.println(e.getMessage());
    }
  }

  public static List<Producto> dbListProductosCreate(Connection con) {
    List<Producto> productos = new ArrayList();
    Producto producto;
    try {
      Statement statement = con.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM productos");
      while (rs.next()) {
        producto = new Producto(rs.getInt("idProducto"), rs.getString("nombre"), rs.getString("descripcion"), rs.getFloat("precio"));
        productos.add(producto);
      }
      System.out.println("DB_List_Productos_Create OK");
    } catch (SQLException e) {
      System.out.println("DB_List_Productos_Create FAIL");
      System.err.println(e.getMessage());
    }
    return productos;
  }

  public static List<Producto> dbListProductosTiendaCreate(Connection con, int idTienda) {
    List<Producto> productos = new ArrayList();
    Producto producto;
    try {
      String sql = "SELECT * FROM productos WHERE idProducto IN (SELECT idProducto FROM stock WHERE idTienda=?)";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, idTienda);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        producto = new Producto(rs.getInt("idProducto"), rs.getString("nombre"), rs.getString("descripcion"), rs.getFloat("precio"));
        productos.add(producto);
      }
      System.out.println("DB_List_Productos_Tienda_Create OK");
    } catch (SQLException e) {
      System.out.println("DB_List_Productos_Tienda_Create FAIL");
      System.err.println(e.getMessage());
    }
    return productos;
  }

  public static void dbTableStockInsert(Connection con, Stock stock) {
    String sql;
    try {
      sql = "INSERT INTO stock(cantidad,idTienda,idProducto) VALUES(?,?,?)";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, stock.getCantidad());
      pstmt.setInt(2, stock.getIdTienda());
      pstmt.setInt(3, stock.getIdProducto());
      pstmt.executeUpdate();
      System.out.println("DB_Table_Stock_Insert OK");
    } catch (SQLException e) {
      System.out.println("DB_Table_Stock_Insert FAIL");
      System.out.println(e.getMessage());
    }
  }

  public static void dbTableStockUpdate(Connection con, Stock stock) {
    String sql;
    try {
      sql = "UPDATE stock SET cantidad=? WHERE idTienda=? AND idProducto=?";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, stock.getCantidad());
      pstmt.setInt(2, stock.getIdTienda());
      pstmt.setInt(3, stock.getIdProducto());
      pstmt.executeUpdate();
      System.out.println("DB_Table_Stock_Update OK ");
    } catch (SQLException e) {
      System.out.println("DB_Table_Stock_Update FAIL");
      System.out.println(e.getMessage());
    }
  }

  public static void dbStockProductoTiendaShow(Connection con, Stock stock) {
    try {
      String sql = "SELECT cantidad,nombre \n"
              + "FROM stock,productos \n"
              + "WHERE stock.idTienda = ? AND \n"
              + "    stock.idProducto = ? AND \n"
              + "    stock.idProducto = productos.idProducto";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, stock.getIdTienda());
      pstmt.setInt(2, stock.getIdProducto());
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        System.out.println(rs.getString("nombre") + " tiene " + rs.getInt("cantidad") + "unidades de stock");
      }
    } catch (SQLException e) {
      System.out.println("DB_Stock_Productos_Tienda_Create FAIL");
      System.err.println(e.getMessage());
    }
  }

  public static void dbRegisterProductoTiendaDelete(Connection con, int idProducto, int idTienda) {
    try {
      String sql = "DELETE FROM stock WHERE idProducto = ? AND idTienda = ?";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, idProducto);
      pstmt.setInt(2, idTienda);
      pstmt.executeUpdate();
      System.out.println("DB_Stock_Tienda_Delete OK");
    } catch (SQLException e) {
      System.out.println("\"DB_Stock_Tienda_Delete FAIL");
      System.err.println(e.getMessage());
    }
  }

  public static void dbRegisterProductoDelete(Connection con, int idProducto) {
    try {
      String sql = "DELETE FROM productos WHERE idProducto = ?";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, idProducto);
      pstmt.executeUpdate();
      System.out.println("DB_Producto_Delete");

      sql = "DELETE FROM stock WHERE idProducto = ?";
      pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, idProducto);
      pstmt.executeUpdate();
      System.out.println("DB_Stock_Producto_Delete");

    } catch (SQLException e) {
      System.out.println("\"DB_Register_Producto_Delete FAIL \"");
      System.err.println(e.getMessage());
    }
  }

  public static void dbTableClientesInsert(Connection con, Cliente cliente) {
    String sql;
    try {
      sql = "INSERT INTO clientes(nombre,apellidos,email) VALUES(?,?,?)";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setString(1, cliente.getNombre());
      pstmt.setString(2, cliente.getApellidos());
      pstmt.setString(3, cliente.getEmail());
      pstmt.executeUpdate();
      System.out.println("DB_Table_Clientes_Insert OK");
    } catch (SQLException e) {
      System.out.println("DB_Table_Clientes_Insert FAIL");
      System.out.println(e.getMessage());
    }
  }

  public static List<Cliente> dbListClientesCreate(Connection con) {
    List<Cliente> clientes = new ArrayList();
    Cliente cliente;
    try {
      Statement statement = con.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM clientes");
      while (rs.next()) {
        cliente = new Cliente(rs.getInt("idCliente"), rs.getString("nombre"), rs.getString("apellidos"), rs.getString("email"));
        clientes.add(cliente);
      }
      System.out.println("DB_List_Clientes_Create OK");
    } catch (SQLException e) {
      System.out.println("DB_List_Clientes_Create FAIL");
      System.err.println(e.getMessage());
    }
    return clientes;
  }

  public static void dbRegisterClienteDelete(Connection con, int idCliente) {
    try {
      String sql = "DELETE FROM clientes WHERE idCliente = ?";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, idCliente);
      pstmt.executeUpdate();
      System.out.println("DB_Cliente_Delete OK");
    } catch (SQLException e) {
      System.out.println("DB_Cliente_Delete FAIL");
      System.err.println(e.getMessage());
    }
  }

  public static boolean dbStockProductoTiendaExists(Connection con, int idProducto, int idTienda) {
    boolean existe = false;
    try {
      String sql = "SELECT * FROM stock WHERE idProducto=? AND idTienda=?";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, idProducto);
      pstmt.setInt(2, idTienda);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        existe = true;
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return existe;
  }

  public static boolean dbTiendaExists(Connection con, int idTienda) {
    boolean existe = false;
    try {
      String sql = "SELECT * FROM tiendas WHERE idTienda=?";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, idTienda);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        existe = true;
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return existe;
  }

  public static boolean dbProductoExits(Connection con, int idProducto) {
    boolean existe = false;
    try {
      String sql = "SELECT * FROM productos WHERE idProducto=?";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, idProducto);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        existe = true;
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return existe;
  }

  public static boolean dbClienteExists(Connection con, int idCliente) {
    boolean existe = false;
    try {
      String sql = "SELECT * FROM clientes WHERE idCliente=?";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, idCliente);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        existe = true;
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return existe;
  }

  public static boolean dbEmpleadoExists(Connection con, int idEmpleado) {
    boolean existe = false;
    try {
      String sql = "SELECT * FROM empleados WHERE idEmpleado=?";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, idEmpleado);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        existe = true;
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return existe;
  }

  public static boolean dbEmpleadoTiendaExits(Connection con, int idTienda, int idEmpleado) {
    boolean existe = false;
    try {
      String sql = "SELECT * FROM horario WHERE idTienda=? AND idEmpleado=?";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, idTienda);
      pstmt.setInt(2, idEmpleado);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        existe = true;
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return existe;
  }

  public static List<Empleado> dbListEmpleadosTiendaCreate(Connection con, int idTienda) {
    List<Empleado> empleados = new ArrayList();
    Empleado empleado;
    try {
      String sql = "SELECT empleados.idEmpleado,nombre, apellidos \n"
              + "FROM horario, empleados \n"
              + "WHERE horario.idTienda=? AND \n"
              + "horario.idEmpleado=empleados.idEmpleado";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, idTienda);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        empleado = new Empleado(rs.getInt("idEmpleado"), rs.getString("nombre"), rs.getString("apellidos"));
        empleados.add(empleado);
      }
      System.out.println("DB_List_EmpleadosTienda_Create OK");
    } catch (SQLException e) {
      System.out.println("DB_List_EmpleadosTienda_Create FAIL");
      System.err.println(e.getMessage());
    }
    return empleados;
  }

  public static List<Empleado> dbListEmpleadosCreate(Connection con) {
    List<Empleado> empleados = new ArrayList();
    Empleado empleado;
    try {
      Statement statement = con.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM empleados");
      while (rs.next()) {
        empleado = new Empleado(rs.getInt("idEmpleado"), rs.getString("nombre"), rs.getString("apellidos"));
        empleados.add(empleado);
      }
      System.out.println("DB_List_Clientes_Create OK");
    } catch (SQLException e) {
      System.out.println("DB_List_Clientes_Create FAIL");
      System.err.println(e.getMessage());
    }
    return empleados;
  }

  public static Provincia dbObjProvinciaCreate(Connection con, int idProvincia) {
    Provincia provincia = null;
    try {
      String sql = "SELECT * FROM provincias WHERE idProvincia=?";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, idProvincia);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        provincia = new Provincia(rs.getInt("idProvincia"), rs.getString("nombre"));
      }

      System.out.println("DB_Obj_Provincia_Create OK");
    } catch (SQLException e) {
      System.out.println("DB_Obj_Provincia_Create FAIL");
      System.err.println(e.getMessage());
    }
    return provincia;
  }

  public static List<Provincia> dbListProvinciasCreate(Connection con) {
    List<Provincia> provincias = new ArrayList();
    Provincia provincia;
    try {
      Statement statement = con.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM provincias");
      while (rs.next()) {
        provincia = new Provincia(rs.getInt("idProvincia"), rs.getString("nombre"));
        provincias.add(provincia);
      }
      System.out.println("DB_List_Provincias_Create OK");
    } catch (SQLException e) {
      System.out.println("DB_List_Provincias_Create FAIL");
      System.err.println(e.getMessage());
    }
    return provincias;
  }

  public static boolean dbRegisterProvinciaExists(Connection con, int idProvincia) {
    boolean existe = false;
    try {
      String sql = "SELECT * FROM provincias WHERE idProvincia=?";
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, idProvincia);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        existe = true;
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return existe;
  }
  

}
