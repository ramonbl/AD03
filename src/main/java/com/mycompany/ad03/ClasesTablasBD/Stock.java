package com.mycompany.ad03.ClasesTablasBD;

public class Stock {
    
    private final int idStock;
    private final int cantidad;
    private final int idTienda;
    private final int idProducto;  

    public Stock(int idStock, int cantidad, int idTienda, int idProducto) {
        this.idStock = idStock;
        this.cantidad = cantidad;
        this.idTienda = idTienda;
        this.idProducto = idProducto;
    }

    public int getIdStock() {
        return idStock;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getIdTienda() {
        return idTienda;
    }

    public int getIdProducto() {
        return idProducto;
    }

}

