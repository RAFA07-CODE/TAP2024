package com.example.tap2024.vistas;

import com.example.tap2024.componentes.ButtonCellCategorias;
import com.example.tap2024.modelos.CategoriasDao;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class CategoriasTaqueria extends Stage {
    private Panel pnlPrincipal;
    private BorderPane bnpPrincipal;
    private ToolBar tlbMenu;
    private Scene escena;
    private TableView<CategoriasDao> tbvCategorias;
    private Button btnAgregarCategoria;

    public CategoriasTaqueria(){
        CrearUI();
        this.setTitle("Taqueria Los de Suaperro :) ");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        ImageView imvEmp = new ImageView(getClass().getResource("/images/mas.png").toString());
        btnAgregarCategoria = new Button();
        imvEmp.setFitHeight(50);
        imvEmp.setFitWidth(50);
        btnAgregarCategoria = new Button();
        btnAgregarCategoria.setOnAction(event -> new CategoriaForm(tbvCategorias, null));
        btnAgregarCategoria.setPrefSize(30,30);
        btnAgregarCategoria.setGraphic(
                imvEmp);
        tlbMenu = new ToolBar(btnAgregarCategoria);
        CrearTable();
        bnpPrincipal = new BorderPane();
        bnpPrincipal.setTop(tlbMenu);
        bnpPrincipal.setCenter(tbvCategorias);
        pnlPrincipal = new Panel("Categorias");
        pnlPrincipal.getStyleClass().add("panel-info");
        pnlPrincipal.setBody(bnpPrincipal);
        escena = new Scene(pnlPrincipal,700,500);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    private void CrearTable(){
        CategoriasDao objCat = new CategoriasDao();
        tbvCategorias = new TableView<CategoriasDao>();
        TableColumn<CategoriasDao,String> tbcCategoria = new TableColumn<>("ID Categoria");
        tbcCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        TableColumn<CategoriasDao,String> tbcNombre = new TableColumn<>("Nombre");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<CategoriasDao,String> tbcEditar= new TableColumn<CategoriasDao,String>("Editar");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<CategoriasDao, String>, TableCell<CategoriasDao, String>>() {
                    @Override
                    public TableCell<CategoriasDao, String> call(TableColumn<CategoriasDao, String> CategoriasDaoStringTableColumn) {
                        return new ButtonCellCategorias(1);
                    }
                }
        );
        TableColumn<CategoriasDao,String> tbcEliminar= new TableColumn<CategoriasDao,String>("Eliminar");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<CategoriasDao, String>, TableCell<CategoriasDao, String>>() {
                    @Override
                    public TableCell<CategoriasDao, String> call(TableColumn<CategoriasDao, String> CategoriasDaoStringTableColumn) {
                        return new ButtonCellCategorias(2);
                    }
                }
        );

        tbvCategorias.getColumns().addAll(tbcCategoria,tbcNombre,tbcEditar,tbcEliminar);
        tbvCategorias.setItems(objCat.CONSULTAR());
    }
}
