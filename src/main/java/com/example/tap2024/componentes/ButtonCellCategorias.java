package com.example.tap2024.componentes;

import com.example.tap2024.modelos.CategoriasDao;
import com.example.tap2024.vistas.CategoriaForm;
import javafx.scene.control.*;

import java.util.Optional;

public class ButtonCellCategorias extends TableCell<CategoriasDao,String> {
    Button btnCelda;
    int opc;
    CategoriasDao objEmp;

    public ButtonCellCategorias(int opc) {
        this.opc = opc;
        String txtButton=( opc == 1 ) ? "Editar" : "Eliminar";
        btnCelda = new Button(txtButton);
        btnCelda.setOnAction(event -> AccionBoton(opc));
    }

    private void AccionBoton(int opc){
        TableView<CategoriasDao> tbvCategorias = ButtonCellCategorias.this.getTableView();
        objEmp = tbvCategorias.getItems().get(ButtonCellCategorias.this.getIndex());
        if (opc == 1){
            new CategoriaForm(tbvCategorias,objEmp);
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText("Confimacion de Acción");
            alert.setContentText("¿Deseas borrar el empleado: "+objEmp.getIdCategoria());
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                objEmp.ELIMINAR();
                tbvCategorias.setItems(objEmp.CONSULTAR());
                tbvCategorias.refresh();
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
