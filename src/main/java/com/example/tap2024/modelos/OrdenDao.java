package com.example.tap2024.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class OrdenDao {
    private int idOrden;
    private int idEmpleado;
    private String fecha;
    private String observaciones;

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void INSERTAR() {
        String query = "insert into orden(idEmpleado,observaciones) values ('" + idEmpleado + "','" + observaciones + "')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ACTUALIZAR() {
        String query = "UPDATE orden set idEmpleado='" + idEmpleado + "'," + "observaciones='" + observaciones +  "' WHERE idOrden = " + idOrden;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR() {
        String query = "DELETE FROM orden WHERE idOrden=" + idOrden;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<OrdenDao> CONSULTAR() {
        ObservableList<OrdenDao> listaOrd = FXCollections.observableArrayList();
        String query = "SELECT * FROM orden";

        try {
            OrdenDao objEmp;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                objEmp = new OrdenDao();
                objEmp.idOrden = res.getInt("idOrden");
                objEmp.idEmpleado = res.getInt("idEmpleado");
                objEmp.fecha = res.getString("fecha");
                objEmp.observaciones = res.getString("observaciones");
                listaOrd.add(objEmp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return listaOrd;
    }

}