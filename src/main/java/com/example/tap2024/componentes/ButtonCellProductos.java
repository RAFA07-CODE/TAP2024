package com.example.tap2024.componentes;

import com.example.tap2024.modelos.ProductosDao;
import com.example.tap2024.vistas.EmpleadosForm;
import com.example.tap2024.vistas.ProductoForm;
import javafx.scene.control.*;

import java.util.Optional;

public class ButtonCellProductos extends TableCell<ProductosDao,String> {
    Button btnCelda;
    int opc;
    ProductosDao objEmp;

    public ButtonCellProductos(int opc) {
        this.opc = opc;
        String txtButton=( opc == 1 ) ? "Editar" : "Eliminar";
        btnCelda = new Button(txtButton);
        btnCelda.setOnAction(event -> AccionBoton(opc));
    }

    private void AccionBoton(int opc){
        TableView<ProductosDao> tbvProductos = ButtonCellProductos.this.getTableView();
        objEmp = tbvProductos.getItems().get(ButtonCellProductos.this.getIndex());
        if (opc == 1){
            new ProductoForm(tbvProductos,objEmp);
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText("Confimacion de Acción");
            alert.setContentText("¿Deseas borrar el empleado: "+objEmp.getId_producto());
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                objEmp.ELIMINAR();
                tbvProductos.setItems(objEmp.CONSULTAR());
                tbvProductos.refresh();
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
