package com.example.tap2024.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class OrdenDetalleDao {
    private int id_orden;
    private int id_mesa;
    private int id_producto;
    private int cantidad;
    private int id_detalle;
    private int total;


    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public int getId_mesa() {
        return id_mesa;
    }

    public void setId_mesa(int id_mesa) {
        this.id_mesa = id_mesa;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void INSERTAR() {
        String query = "insert into orden_detalle(id_mesa, id_producto, cantidad,total)values ('" + id_mesa + "'," + id_producto + ",'" + cantidad + "','" + total+ "')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ACTUALIZAR() {
        String query = "UPDATE orden_detalle set id_orden='" + id_orden + "'," + "id_mesa='" + id_mesa + "',id_producto=" + id_producto + "," + "cantidad='" + cantidad + "' WHERE id_orden = " + id_orden;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR() {
        String query = "DELETE FROM orden_detalle WHERE id_orden=" + id_orden;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<OrdenDetalleDao> CONSULTAR() {
        ObservableList<OrdenDetalleDao> listaOrdD = FXCollections.observableArrayList();
        String query = "SELECT * FROM orden_detalle";

        try {
            OrdenDetalleDao objOrdD;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                objOrdD = new OrdenDetalleDao();
                objOrdD.id_mesa = res.getInt("id_mesa");
                objOrdD.id_producto = res.getInt("id_producto");
                objOrdD.cantidad = res.getInt("cantidad");
                objOrdD.total = res.getInt("total");
                listaOrdD.add(objOrdD);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return listaOrdD;
    }

}