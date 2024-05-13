package com.example.tap2024.vistas;

import com.example.tap2024.modelos.Conexion;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Login extends Stage {

    private MenuBar mnbPrincipal;
    private Menu menParcial1, menSalir;
    private MenuItem mitCategoria, mitSalir, mitproductos, mitMesa, mitEmpleado, mitOrden, mitTaqueria , mitOrdenDetalle;
    private BorderPane bdpPanel;

    public Login() {
        setTitle("Login");
        setResizable(false); // No permitir cambiar el tamaño de la ventana

        // Crear un GridPane para organizar los elementos
        GridPane grid = new GridPane();
        grid.getStyleClass().add("grid-pane"); // Agregar la clase de estilo CSS
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        Text sceneTitle = new Text("Inicio de Sesión");
        sceneTitle.getStyleClass().add("text-title"); // Agregar la clase de estilo CSS
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label usernameLabel = new Label("Usuario:");
        usernameLabel.getStyleClass().add("label"); // Agregar la clase de estilo CSS
        grid.add(usernameLabel, 0, 1);
        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("text-field"); // Agregar la clase de estilo CSS
        grid.add(usernameField, 1, 1);

        Label passwordLabel = new Label("Contraseña:");
        passwordLabel.getStyleClass().add("label"); // Agregar la clase de estilo CSS
        grid.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("password-field"); // Agregar la clase de estilo CSS
        grid.add(passwordField, 1, 2);

        Button loginButton = new Button("Iniciar Sesión");
        loginButton.getStyleClass().add("button"); // Agregar la clase de estilo CSS
        grid.add(loginButton, 1, 3);

        // Agregar mensaje de acción para mostrar errores de inicio de sesión
        Text actionTarget = new Text();
        actionTarget.getStyleClass().add("text-action"); // Agregar la clase de estilo CSS
        grid.add(actionTarget, 1, 4);

        // Resto del código...

        // Manejar el evento del botón de inicio de sesión
        Login loginStage = this; // Referencia al stage actual
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Aquí puedes implementar la lógica de autenticación
            if (username.equals("admin") && password.equals("1234")) {
                CrearMenu();
                bdpPanel = new BorderPane();
                bdpPanel.setTop(mnbPrincipal);
                Scene scene = new Scene(bdpPanel);
                scene.getStylesheets()
                        .add(getClass().getResource("/estilos/tacos.css").toString());
                loginStage.setScene(scene);
                loginStage.setTitle("Administración!");
                loginStage.show();
                loginStage.setMaximized(true);

                Conexion.crearConexion();
                // Aquí puedes abrir la siguiente ventana o realizar otras acciones
            } else {
                actionTarget.setText("Usuario o contraseña incorrectos.");
            }
        });

        // Crear la escena y establecerla en esta instancia de Stage
        Scene scene = new Scene(grid, 500, 300);
        scene.getStylesheets().add(getClass().getResource("/estilos/login.css").toExternalForm()); // Cargar archivo CSS
        setScene(scene);
    }

    private void CrearMenu() {

        mitproductos = new MenuItem("Productos");
        mitproductos.setOnAction(event -> new ProductosTaqueria());

        mitCategoria = new MenuItem("Categoria");
        mitCategoria.setOnAction(event -> new CategoriasTaqueria());

        mitMesa = new MenuItem("Mesa");
        mitMesa.setOnAction(event -> new MesasTaqueria());

        mitOrden = new MenuItem("Orden");
        mitOrden.setOnAction(event -> new OrdenTaqueria());

        mitOrdenDetalle = new MenuItem("Orden Detalle");
        mitOrdenDetalle.setOnAction(event -> new OrdenDetalleTaqueria());

        mitEmpleado = new MenuItem("Empleado Taqueria");
        mitEmpleado.setOnAction(event -> new EmpleadoTaqueria());

        mitTaqueria = new MenuItem("Taqueria");
        mitTaqueria.setOnAction(event -> new Taqueria());

        menParcial1 = new Menu("Primer Parcial");
        menParcial1.getItems().addAll(mitEmpleado, mitTaqueria,mitproductos,mitMesa,mitCategoria,mitOrden,mitOrdenDetalle);

        /* Menu salir */
        mitSalir = new MenuItem("Salir");
        menSalir = new Menu("Salir");
        menSalir.getItems().add(mitSalir);
        mitSalir.setOnAction(event -> System.exit(0));

        mnbPrincipal = new MenuBar();
        mnbPrincipal.getMenus().addAll(menParcial1, menSalir);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
