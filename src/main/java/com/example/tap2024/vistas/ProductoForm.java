package com.example.tap2024.vistas;

import com.example.tap2024.modelos.ProductosDao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class ProductoForm extends Stage {

    private TableView<ProductosDao> tbvProducto;
    private ProductosDao objEmp;
    String[] arrPromts = {"Nombre Producto","Id Categoria","Precio","Ruta"};
    private Scene escena;
    private TextField[] artxtCampos = new TextField[4];
    private Button btnGuardar;
    private VBox vbxPrincipal;
    public ProductoForm(TableView<ProductosDao> tbvEmp, ProductosDao objEmp) {
        tbvProducto = tbvEmp;
        this.objEmp =(objEmp == null) ? new ProductosDao() : objEmp;
        CrearUI();
        this.setTitle("Insertar Producto");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        vbxPrincipal = new VBox();
        vbxPrincipal.setPadding(new Insets(10));
        vbxPrincipal.setSpacing(10);
        vbxPrincipal.setAlignment(Pos.CENTER);
        for(int i = 0;i<artxtCampos.length;i++){
            artxtCampos[i] = new TextField();
            artxtCampos[i].setPromptText(arrPromts[i]);
            vbxPrincipal.getChildren().add(artxtCampos[i]);
        }
        LlenarForm();
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event ->GuardarEmpleado());
        vbxPrincipal.getChildren().add(btnGuardar);
        escena = new Scene(vbxPrincipal,350,250);
    }

    private void LlenarForm() {
        artxtCampos[0].setText(objEmp.getNombre());
        artxtCampos[1].setText(objEmp.getId_categoria()+"");
        artxtCampos[2].setText(objEmp.getPrecio()+"");
        artxtCampos[3].setText(objEmp.getRuta());
    }

    private void GuardarEmpleado() {
        objEmp.setNombre(artxtCampos[0].getText());
        objEmp.setId_categoria(Integer.parseInt(artxtCampos[1].getText()));
        objEmp.setPrecio(Integer.parseInt(artxtCampos[2].getText()));
        objEmp.setRuta(artxtCampos[3].getText());
        if(objEmp.getId_producto()>0)
            objEmp.ACTUALIZAR();
        else
            objEmp.INSERTAR();
        tbvProducto.setItems(objEmp.CONSULTAR());
        tbvProducto.refresh();

        artxtCampos[0].clear();
        artxtCampos[1].clear();
        artxtCampos[2].clear();
        artxtCampos[3].clear();
    }

}

