package com.example.tap2024.vistas;

import com.example.tap2024.modelos.OrdenDetalleDao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class OrdenDetalleForm extends Stage {

    private TableView<OrdenDetalleDao> tbvEmpleados;
    private OrdenDetalleDao objEmp;
    String[] arrPromts = {"ID Orden","ID Mesa","ID Producto","Cantidad","Total"};
    private Scene escena;
    private TextField[] artxtCampos = new TextField[5];
    private Button btnGuardar;
    private VBox vbxPrincipal;
    public OrdenDetalleForm(TableView<OrdenDetalleDao> tbvEmp, OrdenDetalleDao objEmp) {
        tbvEmpleados = tbvEmp;
        this.objEmp =(objEmp == null) ? new OrdenDetalleDao() : objEmp;
        CrearUI();
        this.setTitle("Insertar Orden Detalle");
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
        artxtCampos[0].setText(objEmp.getId_orden()+"");
        artxtCampos[1].setText(objEmp.getId_mesa()+"");
        artxtCampos[2].setText(objEmp.getId_producto()+"");
        artxtCampos[3].setText(objEmp.getCantidad()+"");
        artxtCampos[4].setText(objEmp.getTotal()+"");
    }

    private void GuardarEmpleado() {
        objEmp.setId_orden(Integer.parseInt(artxtCampos[0].getText()));
        objEmp.setId_mesa(Integer.parseInt(artxtCampos[1].getText()));
        objEmp.setId_producto(Integer.parseInt(artxtCampos[2].getText()));
        objEmp.setCantidad(Integer.parseInt(artxtCampos[3].getText()));
        objEmp.setTotal(Integer.parseInt(artxtCampos[4].getText()));
        if(objEmp.getId_detalle()>0)
            objEmp.ACTUALIZAR();
        else
            objEmp.INSERTAR();
        tbvEmpleados.setItems(objEmp.CONSULTAR());
        tbvEmpleados.refresh();

        artxtCampos[0].clear();
        artxtCampos[1].clear();
        artxtCampos[2].clear();
        artxtCampos[3].clear();
        artxtCampos[4].clear();
    }

}

