package com.example.tap2024.componentes;

import com.example.tap2024.modelos.OrdenDetalleDao;
import com.example.tap2024.vistas.OrdenDetalleForm;
import javafx.scene.control.*;

import java.util.Optional;

public class ButtonCellOrdenDetalle extends TableCell<OrdenDetalleDao,String> {
    Button btnCelda;
    int opc;
    OrdenDetalleDao objEmp;

    public ButtonCellOrdenDetalle(int opc) {
        this.opc = opc;
        String txtButton=( opc == 1 ) ? "Editar" : "Eliminar";
        btnCelda = new Button(txtButton);
        btnCelda.setOnAction(event -> AccionBoton(opc));
    }

    private void AccionBoton(int opc){
        TableView<OrdenDetalleDao> tbvEmpleados = ButtonCellOrdenDetalle.this.getTableView();
        objEmp = tbvEmpleados.getItems().get(ButtonCellOrdenDetalle.this.getIndex());
        if (opc == 1){
            new OrdenDetalleForm(tbvEmpleados,objEmp);
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText("Confimacion de Acción");
            alert.setContentText("¿Deseas borrar la orden: "+objEmp.getId_orden());
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                objEmp.ELIMINAR();
                tbvEmpleados.setItems(objEmp.CONSULTAR());
                tbvEmpleados.refresh();
            }
        }
    }

    @Override
    protected void updateItem(String s, boolean empty) {
        super.updateItem(s, empty);
        if (!empty)
            this.setGraphic(btnCelda);
    }
}
