package com.mycompany.ad03.ClasesTablasBD;

import com.mycompany.ad03.AccesoBD.BD;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Pais {
    
    private List<Provincia> provinciasList;

    public Pais(){      
        provinciasList=new ArrayList();
    }

    public List<Provincia> getProvincias(){
        return this.provinciasList;
    }

    public boolean provinciaExists(Connection con,int idProvincia){                 
        return BD.dbRegisterProvinciaExists(con, idProvincia);     
    }    
   
    public void ProvinciasString(Connection con){    
        System.out.println("\nPROVINCIAS");      
        System.out.println("---------------");             
        List<Provincia> provincia=BD.dbListProvinciasCreate(con);
        Provincia p;
        for (int i=1; i<=provincia.size(); i++){
            p=provincia.get(i-1);
            System.out.printf("%-5s %-30s",p.getId(),p.getNome());
            if (i%3==0 )System.out.println();
        }
        System.out.println("----------------");
    }

}
