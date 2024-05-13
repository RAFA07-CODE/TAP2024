package com.example.tap2024.vistas;

import com.example.tap2024.componentes.ButtonCell;
import com.example.tap2024.modelos.EmpleadosDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class MesasTaqueria extends Stage {
    private Panel pnlPrincipal;
    private BorderPane bnpPrincipal;
    private ToolBar tlbMenu;
    private Scene escena;
    private TableView<EmpleadosDAO> tbvEmpleados;
    private Button btnAgregarEmplado;

    public MesasTaqueria(){
        CrearUI();
        this.setTitle("Taqueria Los de Suaperro :) ");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        ImageView imvEmp = new ImageView(getClass().getResource("/images/mas.png").toString());
        btnAgregarEmplado = new Button();
        imvEmp.setFitHeight(50);
        imvEmp.setFitWidth(50);
        btnAgregarEmplado = new Button();
        btnAgregarEmplado.setOnAction(event -> new EmpleadosForm(tbvEmpleados, null));
        btnAgregarEmplado.setPrefSize(30,30);
        btnAgregarEmplado.setGraphic(
                imvEmp);
        tlbMenu = new ToolBar(btnAgregarEmplado);
        CrearTable();
        bnpPrincipal = new BorderPane();
        bnpPrincipal.setTop(tlbMenu);
        bnpPrincipal.setCenter(tbvEmpleados);
        pnlPrincipal = new Panel("Mesas");
        pnlPrincipal.getStyleClass().add("panel-info");
        pnlPrincipal.setBody(bnpPrincipal);
        escena = new Scene(pnlPrincipal,700,500);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    private void CrearTable(){
        EmpleadosDAO objEmp = new EmpleadosDAO();
        tbvEmpleados = new TableView<EmpleadosDAO>();
        TableColumn<EmpleadosDAO,String> tbcNombreEmp = new TableColumn<>("Empleado");
        tbcNombreEmp.setCellValueFactory(new PropertyValueFactory<>("nomEmpleado"));
        TableColumn<EmpleadosDAO,String> tbcRfcEmp = new TableColumn<>("RFC");
        tbcRfcEmp.setCellValueFactory(new PropertyValueFactory<>("rfcEmpleado"));
        TableColumn<EmpleadosDAO,Float> tbcSueldoEmp = new TableColumn<>("Salario");
        tbcSueldoEmp.setCellValueFactory(new PropertyValueFactory<>("salario"));
        TableColumn<EmpleadosDAO,String> tbcTelEmp = new TableColumn<>("Telefono");
        tbcTelEmp.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        TableColumn<EmpleadosDAO,String> tbcDirEmp = new TableColumn<>("Dirección");
        tbcDirEmp.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        TableColumn<EmpleadosDAO,String> tbcEditar= new TableColumn<EmpleadosDAO,String>("Editar");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<EmpleadosDAO, String>, TableCell<EmpleadosDAO, String>>() {
                    @Override
                    public TableCell<EmpleadosDAO, String> call(TableColumn<EmpleadosDAO, String> empleadosDAOStringTableColumn) {
                        return new ButtonCell(1);
                    }
                }
        );
        TableColumn<EmpleadosDAO,String> tbcEliminar= new TableColumn<EmpleadosDAO,String>("Eliminar");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<EmpleadosDAO, String>, TableCell<EmpleadosDAO, String>>() {
                    @Override
                    public TableCell<EmpleadosDAO, String> call(TableColumn<EmpleadosDAO, String> empleadosDAOStringTableColumn) {
                        return new ButtonCell(2);
                    }
                }
        );

        tbvEmpleados.getColumns().addAll(tbcNombreEmp,tbcRfcEmp,tbcSueldoEmp,tbcTelEmp,tbcDirEmp,tbcEditar,tbcEliminar);
        tbvEmpleados.setItems(objEmp.CONSULTAR());
    }
}
