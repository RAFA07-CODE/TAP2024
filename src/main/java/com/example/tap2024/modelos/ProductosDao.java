package com.example.tap2024.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class ProductosDao {
    private int id_producto;
    private String nombre;
    private int id_categoria;
    private int precio; 
    private String ruta;

    public ProductosDao(int id_producto, String nombre, int precio ) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.precio = precio;
    }

    public ProductosDao() {
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void INSERTAR() {
        String query = "insert into productos(nombre,id_categoria,precio,ruta) values ('" + nombre + "','" + id_categoria + "'," + precio + ",'" + ruta + "')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ACTUALIZAR() {
        String query = "UPDATE productos set nombre='" + nombre + "'," + "id_categoria='" + id_categoria + "',precio=" + precio + "," + "ruta='" + ruta + "' WHERE id_producto = " + id_producto;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR() {
        String query = "DELETE FROM productos WHERE id_producto=" + id_producto;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ProductosDao> CONSULTAR() {
        ObservableList<ProductosDao> listaEmp = FXCollections.observableArrayList();
        String query = "SELECT * FROM productos";

        try {
            ProductosDao objEmp;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                objEmp = new ProductosDao();
                objEmp.id_producto = res.getInt("id_producto");
                objEmp.nombre = res.getString("nombre");
                objEmp.id_categoria = res.getInt("id_categoria");
                objEmp.precio = res.getInt("precio");
                objEmp.ruta = res.getString("ruta");
                listaEmp.add(objEmp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return listaEmp;
    }

}