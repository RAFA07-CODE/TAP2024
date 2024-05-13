package com.example.tap2024.modelos;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class CategoriasDao {
    private int idCategoria;
    private String nombre;

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void INSERTAR() {
        String query = "insert into categoria(nombre) values ('" + nombre + "')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ACTUALIZAR() {
        String query = "UPDATE categoria set nombre='" + nombre + "' WHERE id_categoria = " + idCategoria;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR() {
        String query = "DELETE FROM categoria WHERE id_categoria = " + idCategoria;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<CategoriasDao> CONSULTAR() {
        ObservableList<CategoriasDao> listaCat = FXCollections.observableArrayList();
        String query = "SELECT * FROM categoria";

        try {
            CategoriasDao objEmp;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                objEmp = new CategoriasDao();
                objEmp.idCategoria = res.getInt("id_categoria");
                objEmp.nombre = res.getString("nombre");
                listaCat.add(objEmp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return listaCat;
    }

}
