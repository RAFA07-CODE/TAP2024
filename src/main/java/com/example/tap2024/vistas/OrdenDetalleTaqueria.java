package com.example.tap2024.vistas;

import com.example.tap2024.componentes.ButtonCellOrdenDetalle;
import com.example.tap2024.modelos.OrdenDetalleDao;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class OrdenDetalleTaqueria extends Stage {
    private Panel pnlPrincipal;
    private BorderPane bnpPrincipal;
    private ToolBar tlbMenu;
    private Scene escena;
    private TableView<OrdenDetalleDao> tbvEmpleados;
    private Button btnAgregarEmplado;

    public OrdenDetalleTaqueria(){
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
        btnAgregarEmplado.setOnAction(event -> new OrdenDetalleForm(tbvEmpleados, null));
        btnAgregarEmplado.setPrefSize(30,30);
        btnAgregarEmplado.setGraphic(
                imvEmp);
        tlbMenu = new ToolBar(btnAgregarEmplado);
        CrearTable();
        bnpPrincipal = new BorderPane();
        bnpPrincipal.setTop(tlbMenu);
        bnpPrincipal.setCenter(tbvEmpleados);
        pnlPrincipal = new Panel("Orden Detalle");
        pnlPrincipal.getStyleClass().add("panel-info");
        pnlPrincipal.setBody(bnpPrincipal);
        escena = new Scene(pnlPrincipal,700,500);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    private void CrearTable(){
        OrdenDetalleDao objEmp = new OrdenDetalleDao();
        tbvEmpleados = new TableView<OrdenDetalleDao>();
        TableColumn<OrdenDetalleDao,String> tbcIdOrden = new TableColumn<>("ID Orden");
        tbcIdOrden.setCellValueFactory(new PropertyValueFactory<>("id_orden"));
        TableColumn<OrdenDetalleDao,String> tbcMesa = new TableColumn<>("ID Mesa");
        tbcMesa.setCellValueFactory(new PropertyValueFactory<>("id_mesa"));
        TableColumn<OrdenDetalleDao,Float> tbcProducto = new TableColumn<>("ID Producto");
        tbcProducto.setCellValueFactory(new PropertyValueFactory<>("id_producto"));
        TableColumn<OrdenDetalleDao,String> tbcCant = new TableColumn<>("Cantidad");
        tbcCant.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        TableColumn<OrdenDetalleDao,String> tbcTotal = new TableColumn<>("Total");
        tbcCant.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<OrdenDetalleDao,String> tbcEditar= new TableColumn<OrdenDetalleDao,String>("Editar");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<OrdenDetalleDao, String>, TableCell<OrdenDetalleDao, String>>() {
                    @Override
                    public TableCell<OrdenDetalleDao, String> call(TableColumn<OrdenDetalleDao, String> empleadosDAOStringTableColumn) {
                        return new ButtonCellOrdenDetalle(1);
                    }
                }
        );
        TableColumn<OrdenDetalleDao,String> tbcEliminar= new TableColumn<OrdenDetalleDao,String>("Eliminar");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<OrdenDetalleDao, String>, TableCell<OrdenDetalleDao, String>>() {
                    @Override
                    public TableCell<OrdenDetalleDao, String> call(TableColumn<OrdenDetalleDao, String> empleadosDAOStringTableColumn) {
                        return new ButtonCellOrdenDetalle(2);
                    }
                }
        );

        tbvEmpleados.getColumns().addAll(tbcIdOrden,tbcMesa,tbcProducto,tbcCant,tbcEditar,tbcEliminar);
        tbvEmpleados.setItems(objEmp.CONSULTAR());
    }
}
