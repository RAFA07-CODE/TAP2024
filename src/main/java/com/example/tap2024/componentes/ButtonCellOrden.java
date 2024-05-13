package com.example.tap2024.componentes;

import com.example.tap2024.modelos.CategoriasDao;
import com.example.tap2024.modelos.OrdenDao;
import com.example.tap2024.vistas.CategoriaForm;
import com.example.tap2024.vistas.OrdenForm;
import javafx.scene.control.*;

import java.util.Optional;

public class ButtonCellOrden extends TableCell<OrdenDao,String> {
    Button btnCelda;
    int opc;
    OrdenDao objEmp;

    public ButtonCellOrden(int opc) {
        this.opc = opc;
        String txtButton=( opc == 1 ) ? "Editar" : "Eliminar";
        btnCelda = new Button(txtButton);
        btnCelda.setOnAction(event -> AccionBoton(opc));
    }

    private void AccionBoton(int opc){
        TableView<OrdenDao> tbvOrden = ButtonCellOrden.this.getTableView();
        objEmp = tbvOrden.getItems().get(ButtonCellOrden.this.getIndex());
        if (opc == 1){
            new OrdenForm(tbvOrden,objEmp);
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText("Confimacion de Acción");
            alert.setContentText("¿Deseas borrar el empleado: "+objEmp.getIdOrden());
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                objEmp.ELIMINAR();
                tbvOrden.setItems(objEmp.CONSULTAR());
                tbvOrden.refresh();
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
