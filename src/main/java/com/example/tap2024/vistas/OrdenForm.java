package com.example.tap2024.vistas;

import com.example.tap2024.modelos.OrdenDao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrdenForm extends Stage {

    private TableView<OrdenDao> tbvOrden;
    private OrdenDao objEmp;
    String[] arrPromts = {"idEmpleado","observaciones"};
    private Scene escena;
    private TextField[] artxtCampos = new TextField[2];
    private Button btnGuardar;
    private VBox vbxPrincipal;
    public OrdenForm(TableView<OrdenDao> tbvEmp, OrdenDao objEmp) {
        tbvOrden = tbvEmp;
        this.objEmp =(objEmp == null) ? new OrdenDao() : objEmp;
        CrearUI();
        this.setTitle("Insertar Orden");
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
        artxtCampos[0].setText(objEmp.getIdEmpleado()+"");
        artxtCampos[1].setText(objEmp.getObservaciones());
    }

    private void GuardarEmpleado() {
        objEmp.setIdEmpleado(Integer.parseInt(artxtCampos[0].getText()));
        objEmp.setObservaciones(artxtCampos[1].getText());

        // Verificar si el ID de empleado es mayor que 0 para decidir entre inserción o actualización
        if(objEmp.getIdOrden() > 0) {
            objEmp.ACTUALIZAR();  // Actualizar registro existente
        } else {
            objEmp.INSERTAR();  // Insertar nuevo registro
        }

        // Actualizar la tabla después de la inserción o actualización
        tbvOrden.setItems(objEmp.CONSULTAR());
        tbvOrden.refresh();

        // Limpiar campos de texto después de guardar
        for (TextField textField : artxtCampos) {
            textField.clear();
        }
    }


}


