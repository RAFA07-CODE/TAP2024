package com.example.tap2024.vistas;

import com.example.tap2024.componentes.ButtonCellOrden;
import com.example.tap2024.modelos.OrdenDao;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class OrdenTaqueria extends Stage {
    private Panel pnlPrincipal;
    private BorderPane bnpPrincipal;
    private ToolBar tlbMenu;
    private Scene escena;
    private TableView<OrdenDao> tbvOrden;
    private Button btnAgregarEmplado;

    public OrdenTaqueria(){
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
        btnAgregarEmplado.setOnAction(event -> new OrdenForm(tbvOrden, null));
        btnAgregarEmplado.setPrefSize(30,30);
        btnAgregarEmplado.setGraphic(
                imvEmp);
        tlbMenu = new ToolBar(btnAgregarEmplado);
        CrearTable();
        bnpPrincipal = new BorderPane();
        bnpPrincipal.setTop(tlbMenu);
        bnpPrincipal.setCenter(tbvOrden);
        pnlPrincipal = new Panel("Ordenes");
        pnlPrincipal.getStyleClass().add("panel-info");
        pnlPrincipal.setBody(bnpPrincipal);
        escena = new Scene(pnlPrincipal,700,500);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    private void CrearTable(){
        OrdenDao objEmp = new OrdenDao();
        tbvOrden = new TableView<OrdenDao>();
        TableColumn<OrdenDao,String> tbcIdOrden = new TableColumn<>("ID Orden");
        tbcIdOrden.setCellValueFactory(new PropertyValueFactory<>("idOrden"));
        TableColumn<OrdenDao,String> tbcEmp = new TableColumn<>("Empleado");
        tbcEmp.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        TableColumn<OrdenDao,Float> tbcFecha = new TableColumn<>("Fecha");
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        TableColumn<OrdenDao,String> tbcObv = new TableColumn<>("Observaciones");
        tbcObv.setCellValueFactory(new PropertyValueFactory<>("observaciones"));

        TableColumn<OrdenDao,String> tbcEditar= new TableColumn<OrdenDao,String>("Editar");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<OrdenDao, String>, TableCell<OrdenDao, String>>() {
                    @Override
                    public TableCell<OrdenDao, String> call(TableColumn<OrdenDao, String> OrdenDaoStringTableColumn) {
                        return new ButtonCellOrden(1);
                    }
                }
        );
        TableColumn<OrdenDao,String> tbcEliminar= new TableColumn<OrdenDao,String>("Eliminar");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<OrdenDao, String>, TableCell<OrdenDao, String>>() {
                    @Override
                    public TableCell<OrdenDao, String> call(TableColumn<OrdenDao, String> OrdenDaoStringTableColumn) {
                        return new ButtonCellOrden(2);
                    }
                }
        );

        tbvOrden.getColumns().addAll(tbcIdOrden,tbcEmp,tbcFecha,tbcObv,tbcEditar,tbcEliminar);
        tbvOrden.setItems(objEmp.CONSULTAR());
    }
}
