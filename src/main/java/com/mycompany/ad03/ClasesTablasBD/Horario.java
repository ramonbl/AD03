package com.mycompany.ad03.ClasesTablasBD;

public class Horario {
    private final int idHorario;
    private final int idEmpleado;
    private final int idTienda;
    private final int numHorasSemana;

    public Horario(int idHorario, int idEmpleado, int idTienda, int horasSemanales) {
        this.idHorario = idHorario;
        this.idEmpleado = idEmpleado;
        this.idTienda = idTienda;
        this.numHorasSemana = horasSemanales;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public int getIdTienda() {
        return idTienda;
    }

    public int getHorasSemanales() {
        return numHorasSemana;
    }
}
