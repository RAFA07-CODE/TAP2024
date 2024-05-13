package com.example.tap2024.vistas;

import com.example.tap2024.modelos.CategoriasDao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CategoriaForm extends Stage {

    private TableView<CategoriasDao> tbvEmpleados;
    private CategoriasDao objEmp;
    String[] arrPromts = {"nombre"};
    private Scene escena;
    private TextField[] artxtCampos = new TextField[1];
    private Button btnGuardar;
    private VBox vbxPrincipal;
    public CategoriaForm(TableView<CategoriasDao> tbvEmp, CategoriasDao objEmp) {
        tbvEmpleados = tbvEmp;
        this.objEmp =(objEmp == null) ? new CategoriasDao() : objEmp;
        CrearUI();
        this.setTitle("Insertar Usuario");
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
        if (artxtCampos.length > 0) {
            artxtCampos[0].setText(objEmp.getNombre());
        }
    }

    private void GuardarEmpleado() {
        objEmp.setNombre(artxtCampos[0].getText());

        if (objEmp.getIdCategoria() > 0)
            objEmp.ACTUALIZAR();
        else
            objEmp.INSERTAR();
        tbvEmpleados.setItems(objEmp.CONSULTAR());
        tbvEmpleados.refresh();

        artxtCampos[0].clear();
    }

}


