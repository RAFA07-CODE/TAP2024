package com.example.tap2024.vistas;

import com.example.tap2024.componentes.ButtonCell;
import com.example.tap2024.componentes.ButtonCellProductos;
import com.example.tap2024.modelos.ProductosDao;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class ProductosTaqueria extends Stage {
    private Panel pnlPrincipal;
    private BorderPane bnpPrincipal;
    private ToolBar tlbMenu;
    private Scene escena;
    private TableView<ProductosDao> tbvProducto;
    private Button btnAgregarEmplado;

    public ProductosTaqueria(){
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
        btnAgregarEmplado.setOnAction(event -> new ProductoForm(tbvProducto, null));
        btnAgregarEmplado.setPrefSize(30,30);
        btnAgregarEmplado.setGraphic(
                imvEmp);
        tlbMenu = new ToolBar(btnAgregarEmplado);
        CrearTable();
        bnpPrincipal = new BorderPane();
        bnpPrincipal.setTop(tlbMenu);
        bnpPrincipal.setCenter(tbvProducto);
        pnlPrincipal = new Panel("Productos");
        pnlPrincipal.getStyleClass().add("panel-info");
        pnlPrincipal.setBody(bnpPrincipal);
        escena = new Scene(pnlPrincipal,700,500);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    private void CrearTable(){
        ProductosDao objEmp = new ProductosDao();
        tbvProducto = new TableView<ProductosDao>();
        TableColumn<ProductosDao,String> tbcIdProducto = new TableColumn<>("Id Prodcuto");
        tbcIdProducto.setCellValueFactory(new PropertyValueFactory<>("id_producto"));
        TableColumn<ProductosDao,String> tbcNombre = new TableColumn<>("Nombre");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn<ProductosDao,Float> tbcCategoria = new TableColumn<>("Categoria");
        tbcCategoria.setCellValueFactory(new PropertyValueFactory<>("id_categoria"));
        TableColumn<ProductosDao,String> tbcPrecio = new TableColumn<>("Precio");
        tbcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        TableColumn<ProductosDao,String> tbcRuta = new TableColumn<>("Ruta");
        tbcRuta.setCellValueFactory(new PropertyValueFactory<>("ruta"));

        TableColumn<ProductosDao,String> tbcEditar= new TableColumn<ProductosDao,String>("Editar");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<ProductosDao, String>, TableCell<ProductosDao, String>>() {
                    @Override
                    public TableCell<ProductosDao, String> call(TableColumn<ProductosDao, String> ProductosDaoStringTableColumn) {
                        return new ButtonCellProductos(1);
                    }
                }
        );
        TableColumn<ProductosDao,String> tbcEliminar= new TableColumn<ProductosDao,String>("Eliminar");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<ProductosDao, String>, TableCell<ProductosDao, String>>() {
                    @Override
                    public TableCell<ProductosDao, String> call(TableColumn<ProductosDao, String> ProductosDaoStringTableColumn) {
                        return new ButtonCellProductos(2);
                    }
                }
        );

        tbvProducto.getColumns().addAll(tbcIdProducto,tbcNombre,tbcCategoria,tbcPrecio,tbcRuta,tbcEditar,tbcEliminar);
        tbvProducto.setItems(objEmp.CONSULTAR());
    }
}
